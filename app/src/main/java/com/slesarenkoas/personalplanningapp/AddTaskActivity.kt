package com.slesarenkoas.personalplanningapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
	companion object {
		const val EXTRA_ADD_TASK: String = "com.slesarenkoas.personalplanningapp.ADD_TASK"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_task)

		addTaskButton.setOnClickListener {
			val replyIntent = Intent()
			if (TextUtils.isEmpty(taskTitle.text)) {
				setResult(Activity.RESULT_CANCELED, replyIntent)
			} else {
				val taskTitle = taskTitle.text.toString()
				replyIntent.putExtra(EXTRA_ADD_TASK, taskTitle)
				setResult(Activity.RESULT_OK, replyIntent)
			}
			finish()
		}
	}
}