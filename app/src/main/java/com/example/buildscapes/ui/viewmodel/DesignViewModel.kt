package com.example.buildscapes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildscapes.BuildScapesApp
import com.example.buildscapes.data.repository.DesignRepository
import com.example.buildscapes.data.model.DesignItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// Kita pake AndroidViewModel biar bisa akses Application context buat database
class DesignViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DesignRepository
    val allSavedDesigns: Flow<List<DesignItem>>

    init {
        val designDao = (application as BuildScapesApp).database.designDao()
        repository = DesignRepository(designDao)
        allSavedDesigns = repository.getAllSavedDesigns()
    }

    fun insert(item: DesignItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: DesignItem) = viewModelScope.launch {
        repository.delete(item)
    }

    suspend fun isBookmarked(id: Int): Boolean {
        return repository.isBookmarked(id)
    }
}