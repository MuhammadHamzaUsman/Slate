package com.example.todo.ui.taskscreen

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import java.time.LocalDateTime
import java.time.ZoneId

data class TaskState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val category: Category = Category.DEFAULT_CATEGORY,
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault()),
    val stage: Stage = Stage.INCOMPLETE_STAGE,
    val saving: Boolean = false
)

fun Task.toTaskState(isSaving: Boolean) = TaskState(
    id = id,
    title = title,
    description = description,
    category = category,
    stage = stage,
    saving = isSaving
)

fun TaskState.toTask() = Task(
    id = id ?: 0,
    title = title,
    description = description,
    category = category,
    stage = stage,
    createdAt = createdAt,
    updatedAt = LocalDateTime.now(ZoneId.systemDefault())
)