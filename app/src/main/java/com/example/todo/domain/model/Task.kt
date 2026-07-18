package com.example.todo.domain.model

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: Category,
    val stage: Stage,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
