package com.example.todo.domain.model

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: Category,
    val stage: Stage
)
