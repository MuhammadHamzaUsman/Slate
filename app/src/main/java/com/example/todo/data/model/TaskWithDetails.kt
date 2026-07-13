package com.example.todo.data.model

import androidx.room.Embedded
import androidx.room.Relation

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
)
