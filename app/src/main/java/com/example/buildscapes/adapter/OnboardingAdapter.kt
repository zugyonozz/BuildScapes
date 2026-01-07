package com.example.buildscapes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buildscapes.R

class OnboardingAdapter(private val items: List<OnboardingItemAdapter>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivBg = view.findViewById<ImageView>(R.id.ivOnboardingBg)
        private val tvTitle = view.findViewById<TextView>(R.id.tvOnboardingTitle)
        private val tvDesc = view.findViewById<TextView>(R.id.tvOnboardingDesc)

        fun bind(item: OnboardingItemAdapter) {
            ivBg.setImageResource(item.imageRes)
            tvTitle.text = item.title
            tvDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
