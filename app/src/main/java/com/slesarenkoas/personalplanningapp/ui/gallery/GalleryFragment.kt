package com.slesarenkoas.personalplanningapp.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.slesarenkoas.personalplanningapp.PersonalPlanningApplication
import com.slesarenkoas.personalplanningapp.R
import com.slesarenkoas.personalplanningapp.databinding.FragmentGalleryBinding
import com.slesarenkoas.personalplanningapp.ui.addtask.AddTaskActivity
import com.slesarenkoas.personalplanningapp.ui.main.TaskAdapter
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModel
import com.slesarenkoas.personalplanningapp.ui.main.TaskViewModelFactory

class GalleryFragment : Fragment() {

	private val taskViewModel: TaskViewModel by viewModels {
		TaskViewModelFactory((activity?.application as PersonalPlanningApplication).repository)
	}
	private var _binding: FragmentGalleryBinding? = null
	private lateinit var addOrEditTaskLauncher: ActivityResultLauncher<Intent>

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGalleryBinding.inflate(inflater, container, false)
		val root: View = binding.root

		val tasksRecyclerView = binding.tasksRecyclerView

		addOrEditTaskLauncher = registerForActivityResult(
			ActivityResultContracts.StartActivityForResult()
		) { result ->
			Toast.makeText(
				requireContext(),
				if (result.resultCode == Activity.RESULT_CANCELED)
					R.string.canceled
				else
					R.string.added,
				Toast.LENGTH_LONG
			).show()
		}

		tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
		val adapter = TaskAdapter(
			taskViewModel.viewModelScope,
			taskViewModel.repository,
			requireContext(),
			onTaskEdit = { task ->
				val intent = Intent(activity, AddTaskActivity::class.java)
				intent.putExtra(AddTaskActivity.EXTRA_TASK_TO_EDIT, task)
				addOrEditTaskLauncher.launch(intent)
			},
			onAddSubtask = { taskId ->
				val intent = Intent(activity, AddTaskActivity::class.java)
				intent.putExtra(AddTaskActivity.EXTRA_PARENT_TASK_ID, taskId)
				addOrEditTaskLauncher.launch(intent)
			}
		)
		tasksRecyclerView.adapter = adapter

		taskViewModel.todayTasks.observe(viewLifecycleOwner) { todayTasks ->
			todayTasks.let { adapter.submitList(it) }
		}

		return root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}