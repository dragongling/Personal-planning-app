package com.slesarenkoas.personalplanningapp

import android.graphics.Color
import java.text.DateFormat
import java.util.*

class Utils {
	companion object {
		val currentTime: Date
			get() = Calendar.getInstance().time

		fun getForegroundColor(
			backgroundColor: Int,
			// TODO: replace black & white for less eyestrain:
			darkForegroundColor: Int = Color.BLACK,
			lightForegroundColor: Int = Color.WHITE
		): Int {
			val red = Color.red(backgroundColor)
			val green = Color.green(backgroundColor)
			val blue = Color.blue(backgroundColor)
			// Approx from here: https://stackoverflow.com/a/3943023/6152666
			return if ((red * 0.299 + green * 0.587 + blue * 0.114) > 186)
				darkForegroundColor
			else
				lightForegroundColor
		}

		fun startOfDay(): Long {
			val cal = Calendar.getInstance()
			cal.timeInMillis = currentTime.time
			cal[Calendar.HOUR_OF_DAY] = 0 //set hours to zero
			cal[Calendar.MINUTE] = 0 // set minutes to zero
			cal[Calendar.SECOND] = 0 //set seconds to zero
			return cal.timeInMillis
		}

		fun formatDate(date: Date): String {
			return DateFormat.getDateTimeInstance(
				DateFormat.DEFAULT,
				DateFormat.SHORT,
				Locale.getDefault()
			).format(date)
			// The bottom way requires to be Oracle employee to get it work:
//			return Instant
//				.ofEpochMilli(date.getTime())
//				.atZone(ZoneId.systemDefault())
//				.toLocalDate()
//				.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG))
		}
	}
}