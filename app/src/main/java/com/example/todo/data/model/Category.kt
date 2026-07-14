package com.example.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val name: String,
    val color: Long
){
    companion object {
        const val DEFAULT_NAME = "Uncategorized"
        const val DEFAULT_COLOR = 0xFFFFFFFF

        val DEFAULT_CATEGORY = Category(DEFAULT_NAME, DEFAULT_COLOR)

        const val INITIALIZER_SQL =
        """
        INSERT INTO category
        VALUES ($DEFAULT_NAME, $DEFAULT_COLOR);
        """
    }
}