package com.example.taskmanager.model

data class Task(
    val title: String? = null,
    val description: String? = null,
    var isCompleted: Boolean = false
)