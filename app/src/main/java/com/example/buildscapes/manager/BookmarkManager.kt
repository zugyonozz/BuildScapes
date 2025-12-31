package com.example.buildscapes.manager

import com.example.buildscapes.model.DesignItem

object BookmarkManager {
    private val savedItems = mutableListOf<DesignItem>()

    fun toggleBookmark(item: DesignItem) {
        if (isBookmarked(item)) {
            savedItems.removeAll { it.id == item.id }
        } else {
            savedItems.add(item)
        }
    }

    fun isBookmarked(item: DesignItem): Boolean {
        return savedItems.any { it.id == item.id }
    }

    fun getBookmarks(): List<DesignItem> = savedItems
}