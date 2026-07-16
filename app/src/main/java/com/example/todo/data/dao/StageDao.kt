package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.data.model.Stage
import kotlinx.coroutines.flow.Flow

@Dao
interface StageDao {
    @Query("SELECT * FROM stage")
    fun getStages(): Flow<List<Stage>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun addStage(stage: Stage)

    @Delete
    suspend fun removeStage(stage: Stage)
}