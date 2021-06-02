package com.slesarenkoas.personalplanningapp.ui.main

import android.content.Context
import androidx.lifecycle.*
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import com.slesarenkoas.personalplanningapp.model.Task
import com.slesarenkoas.personalplanningapp.ui.widget.TasksWidgetProvider
import kotlinx.coroutines.launch

class TaskViewModel(val repository: TaskRepository) : ViewModel() {
	val currentTasks: LiveData<List<Task>> = repository.currentTasks.asLiveData()

	fun addTask(taskTitle: String, color: Int, isCategory: Boolean, context: Context) =
		viewModelScope.launch {
			repository.addTask(taskTitle, color, isCategory)
			TasksWidgetProvider.sendRefreshBroadcast(context)
		}

	fun editTask(id: Int, taskTitle: String, color: Int, context: Context) = viewModelScope.launch {
		repository.editTask(id, taskTitle, color)
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
