package com.slesarenkoas.personalplanningapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private val addTaskRequestCode: Int = 1

	private val taskViewModel: TaskViewModel by viewModels {
		TaskViewModelFactory((application as PersonalPlanningApplication).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		tasksRecyclerView.layoutManager = LinearLayoutManager(this)
		val adapter = TaskAdapter(taskViewModel)
		tasksRecyclerView.adapter = adapter

		taskViewModel.currentTasks.observe(this) { currentTasks ->
			currentTasks.let { adapter.submitList(it) }
		}

		addTaskButton.setOnClickListener {
			val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
			startActivityForResult(intent, addTaskRequestCode)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == addTaskRequestCode && resultCode == Activity.RESULT_OK) {
			data?.getStringExtra(AddTaskActivity.EXTRA_ADD_TASK)?.let { taskTitle ->
				taskViewModel.addTask(taskTitle)
			}
		} else {
			Toast.makeText(
				applicationContext,
				R.string.empty_task_title_not_saved,
				Toast.LENGTH_LONG
			).show()
		}
	}
}
