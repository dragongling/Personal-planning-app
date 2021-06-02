package com.slesarenkoas.personalplanningapp

import android.app.Application
import com.slesarenkoas.personalplanningapp.db.TaskDatabase
import com.slesarenkoas.personalplanningapp.db.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PersonalPlanningApplication: Application() {
	private val applicationScope = CoroutineScope(SupervisorJob())

	private val database by lazy { TaskDatabase.getDatabase(this) }
	val repository by lazy { TaskRepository(database.taskDAO()) }

}
