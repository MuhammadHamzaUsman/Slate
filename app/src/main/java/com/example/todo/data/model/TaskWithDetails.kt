package com.example.todo.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todo.domain.model.Task

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
    fun toTask() = Task(task.id, task.title, task.description, category, stage)
}
