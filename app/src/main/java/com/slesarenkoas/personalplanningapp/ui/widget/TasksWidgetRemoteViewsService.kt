package com.slesarenkoas.personalplanningapp.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.db.TaskDAO
import com.slesarenkoas.personalplanningapp.db.TaskDatabase
import com.slesarenkoas.personalplanningapp.model.Task


class TasksWidgetRemoteViewsService : RemoteViewsService() {
	companion object {
		var markedCompletedIds = ArrayList<Int>()
	}

	override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
		return TasksRemoteViewsFactory(this.applicationContext)
	}

	class TasksRemoteViewsFactory(private val appContext: Context) : RemoteViewsFactory {
		private val dao: TaskDAO = TaskDatabase.getDatabase(appContext).taskDAO()
		private lateinit var tasks: List<Task>

		override fun onCreate() {
		}

		override fun onDataSetChanged() {
			tasks = dao.getCurrentTasksForWidget()
		}

		override fun onDestroy() {}

		override fun getCount(): Int {
			return tasks.size
		}

		override fun getViewAt(position: Int): RemoteViews? {
			if (tasks.isEmpty())
				return null
			val task: Task = tasks[position]
			val views = RemoteViews(appContext.packageName, R.layout.widget_item_row)
			views.setTextViewText(R.id.taskTitle, task.title)
			if (task.id in markedCompletedIds) {
				views.setInt(R.id.taskTitle, "setPaintFlags", Paint.STRIKE_THRU_TEXT_FLAG)
			}
			//views.setOnClickPendingIntent(R.id.markCompleteButton, getMarkCompletePI(task))

			// Make it possible to distinguish the individual on-click
			// action of a given item
			views.setOnClickFillInIntent(R.id.markCompleteButton, getMarkCompleteIntent(task))

			return views
		}

		private fun getMarkCompleteIntent(task: Task): Intent {
			val markCompleteIntent = Intent(appContext, TasksWidgetProvider::class.java)
			markCompleteIntent.action = TasksWidgetProvider.MARK_COMPLETE_ACTION
			markCompleteIntent.putExtra(TasksWidgetProvider.EXTRA_MARKED_TASK_ID, task.id)
			return markCompleteIntent
		}

		override fun getLoadingView(): RemoteViews? {
			return null
		}

		override fun getViewTypeCount(): Int {
			return 1
		}


		override fun getItemId(position: Int): Long {
			return position.toLong()
		}

		override fun hasStableIds(): Boolean {
			return false
		}
	}
}