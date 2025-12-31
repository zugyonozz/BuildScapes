package com.example.buildscapes.adapter

import com.example.buildscapes.model.DesignItem
import com.example.buildscapes.R
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class DesignAdapter(private val items: List<DesignItem>) : RecyclerView.Adapter<DesignAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgDesign)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        // Menggunakan Glide untuk load image dari URL
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount() = items.size
}
