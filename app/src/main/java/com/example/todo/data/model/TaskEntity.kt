package com.example.todo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.todo.domain.model.Task

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["name"],
            childColumns = ["category"],
            onDelete = ForeignKey.SET_DEFAULT
        ),

        ForeignKey(
            entity = Stage::class,
            parentColumns = ["name"],
            childColumns = ["stage"],
            onDelete = ForeignKey.SET_DEFAULT
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,

    @ColumnInfo(
        index = true,
        defaultValue = Category.DEFAULT_NAME
    )
    val category: String,

    @ColumnInfo(
        index = true,
        defaultValue = Stage.INCOMPLETE_NAME
    )
    val stage: String
)

fun Task.toTaskEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    description = description,
    category = category.name,
    stage = stage.name
)