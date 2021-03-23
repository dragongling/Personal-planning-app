package com.slesarenkoas.personalplanningapp.data

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverters {
	@TypeConverter
	fun fromDate(date: Date?): Long? {
		return date?.time
	}

	@TypeConverter
	fun toDate(millisSinceEpoch: Long?): Date? {
		return millisSinceEpoch?.let {
			Date(it)
		}
	}
}