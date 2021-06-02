package com.slesarenkoas.personalplanningapp.ui.widget


import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.ui.addtask.AddTaskActivity


class TasksWidgetProvider : AppWidgetProvider() {
	companion object {
		const val MARK_COMPLETE_ACTION =
			"com.slesarenkoas.personalplanningapp.action.MARK_COMPLETE_ACTION"
		const val EXTRA_MARKED_TASK_ID =
			"com.slesarenkoas.personalplanningapp.ui.widget.taskposition"

		fun sendRefreshBroadcast(context: Context) {
			val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
			intent.component = ComponentName(context, TasksWidgetProvider::class.java)
			context.sendBroadcast(intent)
		}
	}

	override fun onUpdate(
		context: Context?,
		appWidgetManager: AppWidgetManager?,
		appWidgetIds: IntArray?
	) {
		for (appWidgetId in appWidgetIds!!) {
			val views = RemoteViews(
				context!!.packageName,
				R.layout.widget_tasks
			)
			val intent = Intent(context, TasksWidgetRemoteViewsService::class.java)
			views.setRemoteAdapter(R.id.tasksListView, intent)
			val addTaskIntent = Intent(context, AddTaskActivity::class.java)
			val addTaskPI = PendingIntent.getActivity(
				context,
				0,
				addTaskIntent,
				PendingIntent.FLAG_UPDATE_CURRENT
			)
			views.setOnClickPendingIntent(R.id.addTaskButton, addTaskPI)

			val markCompleteIntentTemplate = Intent(context, TasksWidgetProvider::class.java)
			// Set the action for the intent.
			// When the user touches a particular view
			markCompleteIntentTemplate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
			markCompleteIntentTemplate.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
			markCompleteIntentTemplate.action = MARK_COMPLETE_ACTION
			val markCompletePITemplate = PendingIntent.getActivity(
				context, appWidgetId,
				markCompleteIntentTemplate, PendingIntent.FLAG_UPDATE_CURRENT
			)
			views.setPendingIntentTemplate(R.id.tasksListView, markCompletePITemplate)

			appWidgetManager!!.updateAppWidget(appWidgetId, views)
		}
	}

	override fun onReceive(context: Context?, intent: Intent) {
		val action = intent.action
		if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
			val mgr = AppWidgetManager.getInstance(context)
			val cn = ComponentName(context!!, TasksWidgetProvider::class.java)
			mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.tasksListView)
		}
		if (action == MARK_COMPLETE_ACTION) {
			val taskId = intent.getIntExtra(EXTRA_MARKED_TASK_ID, -1)
			if (taskId != -1) {
				if (taskId in TasksWidgetRemoteViewsService.markedCompletedIds)
					TasksWidgetRemoteViewsService.markedCompletedIds.remove(taskId)
				else
					TasksWidgetRemoteViewsService.markedCompletedIds.add(taskId)
				val mgr = AppWidgetManager.getInstance(context)
				val cn = ComponentName(context!!, TasksWidgetProvider::class.java)
				mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.tasksListView)
			}
		}
		super.onReceive(context, intent)
	}
}