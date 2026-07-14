package com.example.todo.ui.taskscreen

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task

data class TaskState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val category: Category = Category.DEFAULT_CATEGORY,
    val stage: Stage = Stage.INCOMPLETE_STAGE,
    val saving: Boolean = false
)

fun Task.toTaskState() = TaskState(
    id = id,
    title = title,
    description = description,
    category = category,
    stage = stage,
    saving = false
)

fun TaskState.toTask() = Task(
    title = title,
    description = description,
    category = category,
    stage = stage
)