package com.example.taskmanager

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.model.adapter.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fabCreate = findViewById<FloatingActionButton>(R.id.fab_create)
        val taskRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val titleEditText = findViewById<EditText>(R.id.et_title)
        val descEditText = findViewById<EditText>(R.id.et_desc)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskAdapter = TaskAdapter(viewModel.getAllTasks(),
            onItemClick = { task -> viewModel.markTaskCompleted(task) },
            onItemLongClick = { task -> showDeleteDialog(task) })

        taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        fabCreate.setOnClickListener {
            val taskTitle = titleEditText.text.toString()
            val taskDescription = descEditText.text.toString()
            if (taskTitle.isNotBlank()) {
                viewModel.addTask(taskTitle, taskDescription)
                taskAdapter.notifyDataSetChanged()
                titleEditText.text.clear()
            }
        }
    }

    private fun showDeleteDialog(task: Task) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.removeTask(task)
                taskAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.show()
    }
}