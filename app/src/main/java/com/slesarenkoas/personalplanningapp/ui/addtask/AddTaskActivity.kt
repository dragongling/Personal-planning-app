package com.slesarenkoas.personalplanningapp.ui.addtask

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.material.chip.Chip
import com.slesarenkoas.personalplanningapp.PersonalPlanningApplication
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.Utils
import com.slesarenkoas.personalplanningapp.model.Task
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModel
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlin.random.Random


class AddTaskActivity : AppCompatActivity() {
	companion object {
		const val EXTRA_TASK_TO_EDIT = "taskToEdit"
	}

	private var taskToEdit: Task? = null
	private val taskViewModel: TaskViewModel by viewModels {
		TaskViewModelFactory((application as PersonalPlanningApplication).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		taskToEdit = intent.getSerializableExtra(EXTRA_TASK_TO_EDIT) as? Task
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_task)

		var color: Int
		if (taskToEdit != null) {
			taskTitle.setText(taskToEdit!!.title)
			categoryToggle.visibility = View.GONE
			color = taskToEdit!!.color
			title = getString(R.string.edit_task)
		} else {
			color =
				Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
			title = getString(R.string.add_task)
		}
		pickColorButton.background.setTint(color)
		pickColorButton.setTextColor(Utils.getForegroundColor(color))

		pickColorButton.setOnClickListener {
			ColorPickerDialogBuilder
				.with(this)
				.setTitle(getString(R.string.choose_color))
				.initialColor(color)
				.wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
				.density(12)
				.setPositiveButton(
					getString(android.R.string.ok)
				) { _, selectedColor, _ ->
					color = selectedColor
					pickColorButton.background.setTint(color)
					pickColorButton.setTextColor(Utils.getForegroundColor(color))
				}
				.setNegativeButton(android.R.string.cancel, null)
				.showAlphaSlider(false)
				.build()
				.show()
		}

		addTaskButton.setOnClickListener {
			val replyIntent = Intent()
			if (TextUtils.isEmpty(taskTitle.text)) {
				setResult(Activity.RESULT_CANCELED, replyIntent)
			} else {
				val taskTitle = taskTitle.text.toString()
				val isCategory = categoryToggle.isChecked
				if (taskToEdit == null)
					taskViewModel.addTask(taskTitle, color, isCategory, this)
				else
					taskViewModel.editTask(taskToEdit!!.id, taskTitle, color, this)
				setResult(Activity.RESULT_OK, replyIntent)
			}
			finish()
		}

		taskTitle.addTextChangedListener {
			addTaskButton.text = getString(
				when {
					TextUtils.isEmpty(taskTitle.text) -> R.string.cancel
					taskToEdit != null -> R.string.save_task
					else -> R.string.add_task
				}
			)
		}

		addCategoryButton.setOnClickListener {
			// setup the alert builder
			val builder = AlertDialog.Builder(this)
			builder.setTitle("Choose an animal")

			// add a list
			val animals = arrayOf("horse", "cow", "camel", "sheep", "goat")
			builder.setItems(animals) { dialog, which ->

			}

			// create and show the alert dialog
			val dialog = builder.create()
			dialog.show()
		}

		val chip = Chip(this)
		chip.isCloseIconVisible = true
		chip.text = "Test"
		categoriesContainer.addView(chip)

//		val countries = arrayOf(
//			"Belgium", "France", "Italy", "Germany", "Spain"
//		)
//
//		val adapter = ArrayAdapter(
//			this,
//			android.R.layout.simple_dropdown_item_1line,
//			countries
//		)
//		taskTitle.setAdapter(adapter)

	}
}