package com.slesarenkoas.personalplanningapp.db

import androidx.annotation.WorkerThread
import com.slesarenkoas.personalplanningapp.Utils
import com.slesarenkoas.personalplanningapp.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDAO: TaskDAO) {
	val currentTasks: Flow<List<Task>> = taskDAO.getCurrentTasks()

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun markTaskComplete(taskId: Int) {
		taskDAO.markComplete(taskId, Utils.currentTime)
	}

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun addTask(taskTitle: String) {
		taskDAO.addTask(taskTitle, Utils.currentTime)
	}
}
