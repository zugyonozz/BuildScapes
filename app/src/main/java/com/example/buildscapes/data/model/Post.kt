package com.example.buildscapes.data.model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val userName: String = "",
    val userProfileImage: String = "",
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)