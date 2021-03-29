package com.slesarenkoas.personalplanningapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.slesarenkoas.personalplanningapp.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDAO {
	@Insert
	suspend fun insertAll(vararg tasks: Task)

	@Update
	suspend fun updateAll(vararg tasks: Task)

	@Query("SELECT * FROM task")
	fun getAll(): List<Task>

	@Query("SELECT * FROM task WHERE markCompletedTime is null")
	fun getCurrentTasks(): Flow<List<Task>>

	@Query("update task set markCompletedTime = :markCompleteTime where id = :taskId")
	suspend fun markComplete(taskId: Int, markCompleteTime: Date)

	@Query("insert into task (title, addTime) values (:taskTitle, :addTime)")
	suspend fun addTask(taskTitle: String, addTime: Date)
}