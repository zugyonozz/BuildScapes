package com.example.buildscapes

import android.app.Application
import com.example.buildscapes.data.AppDatabase

class BuildScapesApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}