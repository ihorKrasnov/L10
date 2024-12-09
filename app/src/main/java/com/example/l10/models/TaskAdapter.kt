package com.example.l10.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.l10.R

class TaskAdapter(
    private var taskList: List<Task>,
    private val onItemClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var displayedTaskList = taskList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = displayedTaskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return displayedTaskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        private val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)

        init {
            itemView.setOnClickListener {
                onItemClick(displayedTaskList[adapterPosition])
            }
        }

        fun bind(task: Task) {
            taskTitle.text = task.title
            taskDescription.text = task.description
        }
    }

    fun updateDisplayedTasks(newTaskList: List<Task>) {
        displayedTaskList = newTaskList
        notifyDataSetChanged()
    }

    fun updateTasks(newTaskList: List<Task>) {
        taskList = newTaskList
        displayedTaskList = taskList
        notifyDataSetChanged()
    }
}
