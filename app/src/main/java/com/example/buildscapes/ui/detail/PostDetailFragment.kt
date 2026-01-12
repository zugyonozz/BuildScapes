package com.example.buildscapes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PostDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId = arguments?.getString("postId") ?: ""
        val title = arguments?.getString("title") ?: "No Title"
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        val userId = arguments?.getString("userId") ?: ""

        val imgDetail = view.findViewById<ImageView>(R.id.imgDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val btnBookmark = view.findViewById<FloatingActionButton>(R.id.btnDetailBookmark)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarDetail)

        tvTitle.text = title
        Glide.with(this).load(imageUrl).into(imgDetail)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        btnBookmark.setOnClickListener {
            Toast.makeText(context, "Bookmark feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}