package com.slesarenkoas.personalplanningapp

import android.graphics.Color
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
	}
}