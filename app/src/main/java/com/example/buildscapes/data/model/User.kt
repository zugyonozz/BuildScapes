package com.example.buildscapes.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val postsCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getFullName(): String = "$firstName $lastName"
    fun getDisplayName(): String = firstName.ifEmpty { email.substringBefore("@") }
}