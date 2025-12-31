package com.example.buildscapes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.manager.BookmarkManager
import com.example.buildscapes.model.DesignItem

class DesignAdapter(private val items: List<DesignItem>) : RecyclerView.Adapter<DesignAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgDesign)
        val btnBookmark: ImageButton = view.findViewById(R.id.btnBookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.image)
        updateBookmarkIcon(holder.btnBookmark, item)
        holder.btnBookmark.setOnClickListener {
            BookmarkManager.toggleBookmark(item) // Simpan/Hapus dari manager
            updateBookmarkIcon(holder.btnBookmark, item) // Update ikon UI
        }
    }

    private fun updateBookmarkIcon(button: ImageButton, item: DesignItem) {
        if (BookmarkManager.isBookmarked(item)) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    override fun getItemCount() = items.size
}