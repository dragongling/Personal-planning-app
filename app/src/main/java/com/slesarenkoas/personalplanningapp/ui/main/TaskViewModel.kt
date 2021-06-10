package com.slesarenkoas.personalplanningapp.ui.main

import android.content.Context
import androidx.lifecycle.*
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import com.slesarenkoas.personalplanningapp.model.Task
import com.slesarenkoas.personalplanningapp.ui.widget.TasksWidgetProvider
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(val repository: TaskRepository) : ViewModel() {
	val currentTasks: LiveData<List<Task>> = repository.currentTasks.asLiveData()
	val todayTasks: LiveData<List<Task>> = repository.todayTasks.asLiveData()

	fun addTask(
		taskTitle: String,
		color: Int,
		isCategory: Boolean,
		startTime: Date?,
		context: Context
	) =
		viewModelScope.launch {
			repository.addTask(taskTitle, color, startTime, isCategory)
			TasksWidgetProvider.sendRefreshBroadcast(context)
		}

	fun updateTask(id: Int, taskTitle: String, color: Int, startTime: Date?, context: Context) =
		viewModelScope.launch {
			repository.editTask(id, taskTitle, color, startTime)
			TasksWidgetProvider.sendRefreshBroadcast(context)
		}
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory{
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
			@Suppress("UNCHECKED_CAST")
			return TaskViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
