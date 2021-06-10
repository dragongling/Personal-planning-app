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

	@Query("SELECT * FROM task WHERE markCompletedTime is null and isCategory == 0")
	fun getCurrentTasks(): Flow<List<Task>>

	@Query("SELECT * FROM task WHERE markCompletedTime is null and isCategory == 0 and startTime >= :dayStart and startTime < :dayStart + 86400000")
	fun getTodayTasks(dayStart: Long): Flow<List<Task>>

	@Query("SELECT * FROM task WHERE markCompletedTime is null")
	fun getCurrentTasksForWidget(): List<Task>

	@Query("update task set markCompletedTime = :markCompleteTime where id = :taskId")
	suspend fun markComplete(taskId: Int, markCompleteTime: Date)

	@Query("insert into task (title, color, addTime, isCategory, startTime) values (:taskTitle, :color, :addTime, :isCategory, :startTime)")
	suspend fun addTask(
		taskTitle: String,
		color: Int,
		addTime: Date,
		isCategory: Int,
		startTime: Date?
	)

//	@Query("insert into taskToTask (task1Id, task2Id, taskRelationId) values (:taskId, :categoryId, 1)")
//	suspend fun addCategory(taskId: Int, categoryId: Int)

	@Query("update task set title = :taskTitle, color = :color, startTime = :startTime where id = :taskId")
	suspend fun editTask(taskId: Int, taskTitle: String, color: Int, startTime: Date?)
}