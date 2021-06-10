package com.slesarenkoas.personalplanningapp.db

import androidx.annotation.WorkerThread
import com.slesarenkoas.personalplanningapp.Utils
import com.slesarenkoas.personalplanningapp.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

class TaskRepository(private val taskDAO: TaskDAO) {
	val currentTasks: Flow<List<Task>> = taskDAO.getCurrentTasks()
	val todayTasks: Flow<List<Task>> = taskDAO.getTodayTasks(Utils.startOfDay())

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun markTaskComplete(taskId: Int) {
		taskDAO.markComplete(taskId, Utils.currentTime)
	}

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun addTask(taskTitle: String, color: Int, startTime: Date?, isCategory: Boolean) {
		taskDAO.addTask(taskTitle, color, Utils.currentTime, if (isCategory) 1 else 0, startTime)
	}

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun editTask(id: Int, title: String, color: Int, startTime: Date?) {
		taskDAO.editTask(id, title, color, startTime)
	}
}
