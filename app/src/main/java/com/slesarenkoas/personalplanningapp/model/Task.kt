package com.slesarenkoas.personalplanningapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
	@PrimaryKey(autoGenerate = true)
	val id: Int,

	val title: String,

//        val parents: List<Task>?,
//        val children: List<Task>?,
//        val requires: List<Task>?,

	val addTime: Date,
	var markCompletedTime: Date?

//        val startTime: Timestamp,
//        val endTime: Timestamp,
//        val repeatPattern: String?, // TODO: format for pattern storage
//        val timePerDay: Timestamp, // TODO: Time period format
//
//        val location: String?, // TODO: Location format
//        val isHabit: Boolean,
//        val isMaintenance: Boolean,
//        val isCategory: Boolean
)

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
