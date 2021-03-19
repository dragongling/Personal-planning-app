package com.slesarenkoas.personalplanningapp

import android.graphics.Paint
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.task_item_row.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        tasksRecyclerView.layoutManager = linearLayoutManager
        val tasks = ArrayList<Task>()
        for (i in 1..21) {
            tasks += Task("Task $i")
        }
        tasksRecyclerView.adapter = TaskRecyclerAdapter(tasks)
    }

    class TaskRecyclerAdapter(private val tasks: ArrayList<Task>) :
            RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>(){

        private val handler = Handler(Looper.getMainLooper())

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val inflatedView = parent.inflate(R.layout.task_item_row, false)
            return TaskViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val itemTask = tasks[position]
            holder.bindTask(itemTask, position)
        }

        override fun getItemCount() = tasks.size

        private fun crossOutItem(itemView: View, position: Int) {
            itemView.taskTitle.paintFlags = itemView.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            val duration: Long = 1000
            handler.postDelayed({
                tasks.removeAt(position)
                notifyDataSetChanged()
            }, duration)
        }

        private fun cancelCrossOutItem(itemView: View){
            itemView.taskTitle.paintFlags = itemView.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            handler.removeCallbacksAndMessages(null)
        }

        inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            fun bindTask(task: Task, position: Int){
                itemView.taskTitle.text = task.title
                itemView.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
                        crossOutItem(itemView, position)
                    else
                        cancelCrossOutItem(itemView)
                }
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
