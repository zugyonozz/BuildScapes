package com.example.buildscapes

import android.app.Application
import android.util.Log
import com.example.buildscapes.data.local.AppDatabase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class BuildScapesApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                val options = FirebaseOptions.Builder()
                    .setApplicationId("1:744459753826:android:061e2975bcb5648f29b43b") // App ID
                    .setApiKey("AIzaSyBhtfGXzjGUzz2vqlNo3xLS90n1m-wNdaI") // API Key
                    .setProjectId("buildscapes-880f4") // Project ID
                    .setStorageBucket("buildscapes-880f4.firebasestorage.app") // Storage Bucket
                    .setGcmSenderId("744459753826") // Sender ID
                    .build()

                FirebaseApp.initializeApp(this, options)
                Log.d("BuildScapes", "✅ Firebase berhasil diinisialisasi secara manual!")
            } else {
                Log.d("BuildScapes", "ℹ️ Firebase sudah aktif.")
            }
        } catch (e: Exception) {
            Log.e("BuildScapes", "❌ Gagal inisialisasi Firebase: ${e.message}")
        }
    }
}