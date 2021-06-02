package com.slesarenkoas.personalplanningapp.ui.main

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.Utils
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import com.slesarenkoas.personalplanningapp.model.Task
import com.slesarenkoas.personalplanningapp.ui.widget.TasksWidgetProvider
import kotlinx.android.synthetic.main.task_item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TaskAdapter(
	private val scope: CoroutineScope,
	private val repository: TaskRepository,
	private val context: Context,
	private val onTaskEdit: (task: Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskComparator()) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): TaskViewHolder {
		return TaskViewHolder.create(
			parent,
			onTaskMarkedComplete = {
				scope.launch {
					repository.markTaskComplete(it)
					TasksWidgetProvider.sendRefreshBroadcast(context)
				}
				notifyDataSetChanged()
			},
			onTaskEdit = onTaskEdit
		)
	}

	override fun onBindViewHolder(
		holder: TaskViewHolder,
		position: Int
	) {
		val current = getItem(position)
		holder.bind(current)
	}

	fun setOnItemEditListener(function: () -> Unit) {
		TODO("Not yet implemented")
	}

	class TaskViewHolder(
		itemView: View,
		val onTaskMarkedComplete: (taskId: Int) -> Unit,
		val onTaskEdit: (task: Task) -> Unit
	) : RecyclerView.ViewHolder(itemView) {
		private val handler = Handler(Looper.getMainLooper())
		private val markedComplete = false

		fun bind(task: Task) {
			itemView.taskTitle.text = task.title
			itemView.taskCard.background.setTint(task.color)

			val fgColor = Utils.getForegroundColor(task.color)
			itemView.taskTitle.setTextColor(fgColor)
			itemView.markCompleteButton.backgroundTintList = ColorStateList.valueOf(fgColor)
			itemView.editTaskButton.backgroundTintList = ColorStateList.valueOf(fgColor)
			itemView.markCompleteButton.setOnClickListener {
				if (!markedComplete) {
					markCompleted(itemView, task)
				} else {
					cancelMarkCompleted(itemView)
				}
			}

			itemView.editTaskButton.setOnClickListener {
				onTaskEdit(task)
			}
		}

		private fun markCompleted(itemView: View, task: Task) {
			itemView.taskTitle.paintFlags =
				itemView.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
			val delayBeforeMarked: Long = 1000
			handler.postDelayed({
				onTaskMarkedComplete(task.id)
			}, delayBeforeMarked)
		}

		private fun cancelMarkCompleted(itemView: View) {
			itemView.taskTitle.paintFlags =
				itemView.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
			handler.removeCallbacksAndMessages(null)
		}

		companion object {
			fun create(
				parent: ViewGroup,
				onTaskMarkedComplete: (taskId: Int) -> Unit,
				onTaskEdit: (task: Task) -> Unit
			): TaskViewHolder {
				val view: View = LayoutInflater.from(parent.context)
					.inflate(R.layout.task_item_row, parent, false)
				return TaskViewHolder(view, onTaskMarkedComplete, onTaskEdit)
			}
		}
	}

	class TaskComparator : DiffUtil.ItemCallback<Task>() {
		override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
			return oldItem === newItem
		}

		override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
			return oldItem.title == newItem.title
		}
	}
}