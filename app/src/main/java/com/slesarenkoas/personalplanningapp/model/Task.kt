package com.slesarenkoas.personalplanningapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Task(
	@PrimaryKey(autoGenerate = true)
	val id: Int,

	val title: String,

//	@Relation(
//		parentColumn = "task1Id",
//		entityColumn = "task2Id",
//		associateBy = Junction(TaskToTask::class)
//	)
//	val categories: List<Task>?,
//        val children: List<Task>?,
//        val requires: List<Task>?,

	val addTime: Date,
	var markCompletedTime: Date?,
	var startTime: Date?,
	val color: Int,

//        val startTime: Timestamp,
//        val endTime: Timestamp,
//        val repeatPattern: String?, // TODO: format for pattern storage
//        val timePerDay: Timestamp, // TODO: Time period format
//
//        val location: String?, // TODO: Location format
//        val isHabit: Boolean,
//        val isMaintenance: Boolean,
	val isCategory: Boolean
) : Serializable

//@Entity(primaryKeys = ["task1Id", "task2Id", "taskRelationId"])
//data class TaskToTask(
//	val task1Id: Int,
//	val task2Id: Int,
//	val taskRelationId: Int
//)
//
//@Entity
//data class TaskRelation(
//	@PrimaryKey val id: Int,
//	val name: String
//)
//val sleep: Task = Task(
//        0,
//        "Sleep",
//        null,
//        null,
//        null,
//        "now",
//        "",
//        "",
//        "every day",
//        "8 hours",
//        "Home",
//        true,
//        true
//)
