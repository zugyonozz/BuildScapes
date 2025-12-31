package com.example.buildscapes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.buildscapes.R
import com.example.buildscapes.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (String) -> Unit // Callback biar MainActivity tau
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    // Simpan posisi mana yang lagi dipilih
    private var selectedPosition = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.cardCategory)
        val text: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.text.text = category.name

        // Logika Ganti Warna
        if (selectedPosition == position) {
            // Kalau dipilih: Background Hitam, Teks Putih
            holder.card.setCardBackgroundColor(Color.BLACK)
            holder.text.setTextColor(Color.WHITE)
        } else {
            // Kalau tidak: Background Putih, Teks Hitam
            holder.card.setCardBackgroundColor(Color.WHITE)
            holder.text.setTextColor(Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            // Update posisi yang dipilih
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            // Refresh tampilan biar warnanya berubah
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // Kasih tau MainActivity
            onCategoryClick(category.name)
        }
    }

    override fun getItemCount() = categories.size
}
