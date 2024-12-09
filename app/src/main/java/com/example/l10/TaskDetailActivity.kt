package com.example.l10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.l10.models.Task
class TaskDetailActivity : AppCompatActivity() {

    private lateinit var task: Task
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        task = intent.getParcelableExtra("task")!!
        taskList = intent.getParcelableArrayListExtra("taskList")!!

        val titleTextView: TextView = findViewById(R.id.taskTitle)
        val descriptionTextView: TextView = findViewById(R.id.taskDescription)
        titleTextView.text = task.title
        descriptionTextView.text = task.description

        val markAsCompletedButton: Button = findViewById(R.id.markAsCompletedButton)
        val deleteButton: Button = findViewById(R.id.deleteButton)

        deleteButton.setOnClickListener {
            taskList.remove(task)
            updateTaskList(deletedTask = task)
            finish()
        }

        markAsCompletedButton.setOnClickListener {
            task.isCompleted = true
            updateTaskList()
            finish()
        }
    }

    private fun updateTaskList(deletedTask: Task? = null) {
        val intent = Intent()
        if (deletedTask != null) {
            intent.putExtra("deleted_task", deletedTask)
        } else {
            intent.putParcelableArrayListExtra("taskList", ArrayList(taskList))
        }
        setResult(RESULT_OK, intent)
    }
}

