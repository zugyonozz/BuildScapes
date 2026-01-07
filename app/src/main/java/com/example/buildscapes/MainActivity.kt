package com.example.buildscapes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.discoverFragment,
                R.id.bookmarkFragment,
                R.id.uploadFragment,
                R.id.notificationFragment,
                R.id.profileFragment -> {
                    bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    bottomNav.visibility = View.GONE
                }
            }
        }

        bottomNav.setupWithNavController(navController)
        setupNotificationBadge(bottomNav)
    }

    private fun setupNotificationBadge(bottomNav: BottomNavigationView) {
        val badge = bottomNav.getOrCreateBadge(R.id.notificationFragment)

        badge.isVisible = true
        badge.number = 5
    }
}