package com.example.buildscapes.data

import com.example.buildscapes.model.DesignItem
import kotlinx.coroutines.flow.Flow

class DesignRepository(private val designDao: DesignDao) {
    fun getAllSavedDesigns(): Flow<List<DesignItem>> = designDao.getAllSavedDesigns()
    suspend fun isBookmarked(id: Int): Boolean = designDao.isBookmarked(id)
    suspend fun insert(item: DesignItem) = designDao.insertDesign(item)
    suspend fun delete(item: DesignItem) = designDao.deleteDesign(item)
}