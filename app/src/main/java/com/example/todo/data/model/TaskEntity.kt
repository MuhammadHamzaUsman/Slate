package com.example.todo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.todo.model.Task

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

    @ColumnInfo(defaultValue = Category.DEFAULT_NAME)
    val category: String,

    @ColumnInfo(defaultValue = Stage.INCOMPLETE_NAME)
    val stage: String
){
    companion object {
        @JvmStatic
        fun createFromTask(task: Task) = TaskEntity(
            title = task.title,
            description = task.description,
            category = task.category.name,
            stage = task.stage.name
        )
    }
}