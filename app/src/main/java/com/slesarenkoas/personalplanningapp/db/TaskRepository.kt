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
	suspend fun addTask(taskTitle: String, color: Int, isCategory: Boolean) {
		taskDAO.addTask(taskTitle, color, Utils.currentTime, if (isCategory) 1 else 0)
	}

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun editTask(id: Int, title: String, color: Int) {
		taskDAO.editTask(id, title, color)
	}
}
