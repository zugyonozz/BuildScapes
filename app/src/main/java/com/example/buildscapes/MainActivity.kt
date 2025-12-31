package com.example.buildscapes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.buildscapes.adapter.CategoryAdapter
import com.example.buildscapes.adapter.DesignAdapter
import com.example.buildscapes.model.Category
import com.example.buildscapes.model.DesignItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()
        setupDesignGrid()
    }

    private fun setupCategories() {
        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        val categories = listOf(
            Category("All", true),
            Category("House"),
            Category("Design Interior"),
            Category("Material"),
            Category("Apartment"),
            Category("Minimalist")
        )

        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = CategoryAdapter(categories) { selectedCategory ->
            Toast.makeText(this, "Memilih: $selectedCategory", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDesignGrid() {
        val rvDesigns = findViewById<RecyclerView>(R.id.rvDesigns)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvDesigns.layoutManager = layoutManager
        val mockData = listOf(
            DesignItem(1, "Modern House", "https://images.unsplash.com/photo-1600596542815-27bfefd5c3c8?auto=format&fit=crop&w=400&q=80"),
            DesignItem(2, "Luxury Villa", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=400&q=80"),
            DesignItem(3, "Cozy Interior", "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&w=400&q=80"),
            DesignItem(4, "Spiral Stairs", "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?auto=format&fit=crop&w=400&q=80"),
            DesignItem(5, "Green Garden", "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?auto=format&fit=crop&w=400&q=80"),
            DesignItem(6, "Swimming Pool", "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=400&q=80")
        )

        rvDesigns.adapter = DesignAdapter(mockData)
    }
}