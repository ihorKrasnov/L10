package com.example.l10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.l10.models.Task

class AddTaskActivity : AppCompatActivity() {

    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var addTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskTitle = findViewById(R.id.taskTitle)
        taskDescription = findViewById(R.id.taskDescription)
        addTaskButton = findViewById(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            val title = taskTitle.text.toString()
            val description = taskDescription.text.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val task = Task(title, description)

            val intent = Intent()
            intent.putExtra("task", task)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
