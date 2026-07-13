package com.example.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stage")
data class Stage(
    @PrimaryKey
    val name: String,
    val color: Long
){
    companion object {
        const val INCOMPLETE_NAME = "Incomplete"
        const val INCOMPLETE_COLOR = 0xFFF54242

        const val COMPLETED_NAME = "Completed"
        const val COMPLETED_COLOR = 0xFF42F578

        const val INITIALIZER_SQL =
        """
        INSERT INTO category
        VALUES ($INCOMPLETE_NAME, $INCOMPLETE_COLOR),
            ($COMPLETED_NAME, $COMPLETED_COLOR);
        """
    }
}