package com.slesarenkoas.personalplanningapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.slesarenkoas.personalplanningapp.PersonalPlanningApplication
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.ui.addtask.AddTaskActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private lateinit var addOrEditTaskLauncher: ActivityResultLauncher<Intent>

	private val taskViewModel: TaskViewModel by viewModels {
		TaskViewModelFactory((application as PersonalPlanningApplication).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setTitle(R.string.current_tasks)

		addOrEditTaskLauncher = registerForActivityResult(
			ActivityResultContracts.StartActivityForResult()
		) { result ->
			Toast.makeText(
				applicationContext,
				if (result.resultCode == Activity.RESULT_CANCELED)
					R.string.canceled
				else
					R.string.added,
				Toast.LENGTH_LONG
			).show()
		}

		tasksRecyclerView.layoutManager = LinearLayoutManager(this)
		val adapter = TaskAdapter(
			taskViewModel.viewModelScope,
			taskViewModel.repository,
			this
		) { task ->
			val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
			intent.putExtra(AddTaskActivity.EXTRA_TASK_TO_EDIT, task)
			addOrEditTaskLauncher.launch(intent)
		}
		tasksRecyclerView.adapter = adapter

		taskViewModel.currentTasks.observe(this) { currentTasks ->
			currentTasks.let { adapter.submitList(it) }
		}

		addTaskButton.setOnClickListener {
			val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
			addOrEditTaskLauncher.launch(intent)
		}
	}
}
