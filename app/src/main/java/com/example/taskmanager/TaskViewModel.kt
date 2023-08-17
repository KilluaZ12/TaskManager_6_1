package com.example.taskmanager

import androidx.lifecycle.ViewModel
import com.example.taskmanager.model.Task

class TaskViewModel : ViewModel() {

    val taskList = mutableListOf<Task>()

    fun addTask(title: String, description: String) {
        taskList.add(Task(title, description))
    }

    fun markTaskCompleted(task: Task) {
        task.isCompleted = true
    }

    fun removeTask(task: Task) {
        taskList.remove(task)
    }

    fun getAllTasks(): List<Task> {
        return taskList
    }
}