package com.slesarenkoas.personalplanningapp.ui.addtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.slesarenkoas.personalplanningapp.PersonalPlanningApplication
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModel
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
	private val taskViewModel: TaskViewModel by viewModels {
		TaskViewModelFactory((application as PersonalPlanningApplication).repository)
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
				taskViewModel.addTask(taskTitle, this)
				setResult(Activity.RESULT_OK, replyIntent)
			}
			finish()
		}

		taskTitle.addTextChangedListener {
			addTaskButton.text = getString(
				if (TextUtils.isEmpty(taskTitle.text))
					R.string.cancel
				else
					R.string.add_task
			)
		}
	}
}