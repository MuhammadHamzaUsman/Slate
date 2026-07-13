package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Insert
    suspend fun addCategory(category: Category)

    @Delete
    suspend fun removeCategory(category: Category)
}