package com.example.buildscapes.data.model

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val isNew: Boolean = false
)