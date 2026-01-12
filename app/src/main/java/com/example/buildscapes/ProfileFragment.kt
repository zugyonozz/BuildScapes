package com.example.buildscapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.buildscapes.adapter.DesignAdapter
import com.example.buildscapes.model.DesignItem
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val tvName = view.findViewById<TextView>(R.id.tv_profile_fragment_username)
        val tvEmail = view.findViewById<TextView>(R.id.tvBio) // Kita pake bio buat email sementara

        if (user != null) {
            tvName.text = user.displayName ?: "No Name"
            tvEmail.text = user.email
        } else {
            // Kalau gak ada user (aneh sih bisa masuk sini), balikin ke Login
            // findNavController().navigate(R.id.action_global_login)
        }

        val rvProfileDesigns = view.findViewById<RecyclerView>(R.id.rvProfileDesigns)
        val btnEdit = view.findViewById<Button>(R.id.btnEditProfile)
        val btnSettings = view.findViewById<android.widget.ImageView>(R.id.btnSettings)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvProfileDesigns.layoutManager = layoutManager

        val myDesigns = listOf(
            DesignItem(101, "My Studio", "https://images.unsplash.com/photo-1600607686527-6fb886090705?auto=format&fit=crop&w=400&q=80"),
            DesignItem(102, "Minimalist Kitchen", "https://images.unsplash.com/photo-1556912173-3db996ea0661?auto=format&fit=crop&w=400&q=80"),
            DesignItem(103, "Renovation Project", "https://images.unsplash.com/photo-1503387762-592deb58ef4e?auto=format&fit=crop&w=400&q=80"),
            DesignItem(104, "Draft #1", "https://images.unsplash.com/photo-1503387762-592deb58ef4e?auto=format&fit=crop&w=400&q=80")
        )

        rvProfileDesigns.adapter = DesignAdapter(myDesigns) { item ->
            val bundle = Bundle().apply {
                putInt("id", item.id)
                putString("title", item.title)
                putString("imageUrl", item.imageUrl)
            }

            androidx.navigation.Navigation.findNavController(view)
                .navigate(R.id.detailFragment, bundle)
        }

        btnEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Edit Profile Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        btnSettings.setOnClickListener {
            androidx.navigation.Navigation.findNavController(view).navigate(R.id.settingsFragment)
        }
    }
}