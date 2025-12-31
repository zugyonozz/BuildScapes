package com.example.buildscapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.buildscapes.model.DesignItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.buildscapes.viewmodel.DesignViewModel
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private val viewModel: DesignViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val designDao = (requireActivity().application as BuildScapesApp).database.designDao()

        val id = arguments?.getInt("id") ?: 0
        val title = arguments?.getString("title") ?: "No Title"
        val imageUrl = arguments?.getString("imageUrl") ?: ""

        val currentItem = DesignItem(id, title, imageUrl)

        val imgDetail = view.findViewById<ImageView>(R.id.imgDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val btnBookmark = view.findViewById<FloatingActionButton>(R.id.btnDetailBookmark)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarDetail)

        tvTitle.text = title
        Glide.with(this).load(imageUrl).into(imgDetail)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        lifecycleScope.launch {
            val isBookmarked = viewModel.isBookmarked(id)
            updateIcon(btnBookmark, isBookmarked)
        }

        btnBookmark.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.isBookmarked(id)) {
                    viewModel.delete(currentItem)
                    updateIcon(btnBookmark, false)
                    Toast.makeText(context, "Removed from Saved", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insert(currentItem)
                    updateIcon(btnBookmark, true)
                    Toast.makeText(context, "Saved to collection!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateIcon(btn: FloatingActionButton, isBookmarked: Boolean) {
        if (isBookmarked) {
            btn.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            btn.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }
}