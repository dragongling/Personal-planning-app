package com.slesarenkoas.personalplanningapp.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import com.slesarenkoas.personalplanningapp.model.Task

class GalleryViewModel(val repository: TaskRepository) : ViewModel() {
	val todayTasks: LiveData<List<Task>> = repository.todayTasks.asLiveData()
}