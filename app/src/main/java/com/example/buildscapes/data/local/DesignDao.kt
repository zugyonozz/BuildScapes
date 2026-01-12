package com.example.buildscapes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.buildscapes.data.model.DesignItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DesignDao {
    @Query("SELECT * FROM saved_designs")
    fun getAllSavedDesigns(): Flow<List<DesignItem>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_designs WHERE id = :id)")
    suspend fun isBookmarked(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesign(item: DesignItem)

    @Delete
    suspend fun deleteDesign(item: DesignItem)
}