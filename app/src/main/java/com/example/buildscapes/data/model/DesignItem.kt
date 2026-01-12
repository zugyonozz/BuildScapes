package com.example.buildscapes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_designs")
data class DesignItem(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String
)