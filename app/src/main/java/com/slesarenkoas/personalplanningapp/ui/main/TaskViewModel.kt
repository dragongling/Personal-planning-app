package com.slesarenkoas.personalplanningapp.ui.main

import androidx.lifecycle.*
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import com.slesarenkoas.personalplanningapp.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
	val currentTasks: LiveData<List<Task>> = repository.currentTasks.asLiveData()

	fun markTaskComplete(taskId: Int) = viewModelScope.launch {
		repository.markTaskComplete(taskId)
	}

	fun addTask(taskTitle: String) = viewModelScope.launch {
		repository.addTask(taskTitle)
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
