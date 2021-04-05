package com.slesarenkoas.personalplanningapp.ui.widget


import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.ui.addtask.AddTaskActivity


class TasksWidgetProvider : AppWidgetProvider() {
	companion object {
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
		super.onReceive(context, intent)
	}

}