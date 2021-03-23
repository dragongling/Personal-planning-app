package com.slesarenkoas.personalplanningapp

import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slesarenkoas.personalplanningapp.data.Task
import kotlinx.android.synthetic.main.task_item_row.view.*

class TaskAdapter(
	private val taskViewModel: TaskViewModel
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskComparator()) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): TaskViewHolder {
		return TaskViewHolder.create(parent) {
			taskViewModel.markTaskComplete(it)
			notifyDataSetChanged()
		}
	}

	override fun onBindViewHolder(
		holder: TaskViewHolder,
		position: Int
	) {
		val current = getItem(position)
		holder.bind(current)
	}

	class TaskViewHolder(
		itemView: View,
		val onTaskMarkedComplete: (taskId: Int) -> Unit
	) : RecyclerView.ViewHolder(itemView) {
		private val handler = Handler(Looper.getMainLooper())

		fun bind(task: Task) {
			itemView.taskTitle.text = task.title
			itemView.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
				if (isChecked)
					markCompleted(itemView, task)
				else
					cancelMarkCompleted(itemView)
			}
		}

		private fun markCompleted(itemView: View, task: Task) {
			itemView.taskTitle.paintFlags = itemView.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
			val delayBeforeMarked: Long = 1000
			handler.postDelayed({
				onTaskMarkedComplete(task.id)
			}, delayBeforeMarked)
		}

		private fun cancelMarkCompleted(itemView: View){
			itemView.taskTitle.paintFlags = itemView.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
			handler.removeCallbacksAndMessages(null)
		}

		companion object {
			fun create(
				parent: ViewGroup,
				onTaskMarkedComplete: (taskId: Int) -> Unit
			): TaskViewHolder {
				val view: View = LayoutInflater.from(parent.context)
					.inflate(R.layout.task_item_row, parent, false)
				return TaskViewHolder(view, onTaskMarkedComplete)
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