package com.slesarenkoas.personalplanningapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.slesarenkoas.personalplanningapp.databinding.ActivityNavigationBinding
import com.slesarenkoas.personalplanningapp.ui.addtask.AddTaskActivity

class NavigationActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityNavigationBinding
	private lateinit var addOrEditTaskLauncher: ActivityResultLauncher<Intent>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityNavigationBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.appBarNavigation.toolbar)

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

		binding.appBarNavigation.fab.setOnClickListener { view ->
			val intent = Intent(this, AddTaskActivity::class.java)
			addOrEditTaskLauncher.launch(intent)
		}
		val drawerLayout: DrawerLayout = binding.drawerLayout
		val navView: NavigationView = binding.navView
		val navController = findNavController(R.id.nav_host_fragment_content_navigation)
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.nav_unfinished_tasks, R.id.nav_today_tasks, R.id.nav_slideshow
			), drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.navigation, menu)
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_navigation)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}
}