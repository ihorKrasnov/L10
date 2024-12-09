package com.example.l10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l10.models.Task
import com.example.l10.models.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val tasks = mutableListOf<Task>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasks.add(Task("Завдання 1", "Опис завдання 1", isCompleted = false))
        tasks.add(Task("Завдання 2", "Опис завдання 2", isCompleted = true))

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskAdapter = TaskAdapter(tasks) { task ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("task", task)
            intent.putParcelableArrayListExtra("taskList", ArrayList(tasks))
            startActivityForResult(intent, TASK_DETAIL_REQUEST)
        }
        recyclerView.adapter = taskAdapter

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_all_tasks -> {
                taskAdapter.updateTasks(tasks)
                showSnackbar("Показано всі завдання")
                true
            }
            R.id.nav_completed_tasks -> {
                val completedTasks = tasks.filter { it.isCompleted }
                taskAdapter.updateDisplayedTasks(completedTasks)
                showSnackbar("Показано завершені завдання")
                true
            }
            R.id.nav_incomplete_tasks -> {
                val incompleteTasks = tasks.filter { !it.isCompleted }
                taskAdapter.updateDisplayedTasks(incompleteTasks)
                showSnackbar("Показано незавершені завдання")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackbar(message: String) {
        val view: View = findViewById(R.id.recyclerView)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_TASK_REQUEST) {
                val task = data?.getParcelableExtra<Task>("task")
                task?.let {
                    tasks.add(it)
                    taskAdapter.updateTasks(tasks)
                }
            } else if (requestCode == TASK_DETAIL_REQUEST) {
                val updatedTasks = data?.getParcelableArrayListExtra<Task>("taskList")
                updatedTasks?.let {
                    tasks.clear()
                    tasks.addAll(it)
                    taskAdapter.updateTasks(tasks)
                }

                val deletedTask = data?.getParcelableExtra<Task>("deleted_task")
                deletedTask?.let {
                    tasks.remove(it)
                    taskAdapter.updateTasks(tasks)
                }
            }
        }
    }


    companion object {
        const val ADD_TASK_REQUEST = 1
        const val TASK_DETAIL_REQUEST = 2
    }
}
