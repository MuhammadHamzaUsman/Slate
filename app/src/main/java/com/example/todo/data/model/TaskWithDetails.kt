package com.example.todo.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todo.domain.model.Task
import com.example.todo.data.util.toDate

data class TaskWithDetails(
    @Embedded
    val task: TaskEntity,

    @Relation(
        parentColumn = "category",
        entityColumn = "name"
    )
    val category: Category,
    @Relation(
        parentColumn = "stage",
        entityColumn = "name"
    )
    val stage: Stage
){
    fun toTask() = Task(
        id = task.id,
        title = task.title,
        description = task.description,
        category = category,
        stage = stage,
        createdAt = task.createdAt.toDate(),
        updatedAt = task.updatedAt.toDate()
    )
}
