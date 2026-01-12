package com.example.buildscapes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.adapter.CategoryAdapter
import com.example.buildscapes.adapter.PostAdapter
import com.example.buildscapes.data.model.Category
import com.example.buildscapes.util.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private val firebaseManager = FirebaseManager()
    private lateinit var rvDesigns: RecyclerView
    private lateinit var tvGreeting: TextView
    private lateinit var tvUserName: TextView
    private lateinit var profileImage: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGreeting = view.findViewById(R.id.tvGreeting)
        tvUserName = view.findViewById(R.id.tvUserName)
        profileImage = view.findViewById(R.id.profileImage)
        rvDesigns = view.findViewById(R.id.rvDesigns)

        setupCategories(view)
        loadUserProfile()
        loadAllPosts()
    }

    private fun loadUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        lifecycleScope.launch {
            val result = firebaseManager.getUserProfile(userId)
            result.onSuccess { user ->
                tvUserName.text = user.getFullName()

                if (user.profileImageUrl.isNotEmpty()) {
                    Glide.with(this@DiscoverFragment)
                        .load(user.profileImageUrl)
                        .placeholder(R.drawable.ic_person_black)
                        .circleCrop()
                        .into(profileImage)
                }
            }
        }
    }

    private fun loadAllPosts() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvDesigns.layoutManager = layoutManager

        lifecycleScope.launch {
            val result = firebaseManager.getAllPosts()
            result.onSuccess { posts ->
                if (posts.isEmpty()) {
                    Toast.makeText(context, "No designs yet. Be the first to upload!", Toast.LENGTH_SHORT).show()
                } else {
                    rvDesigns.adapter = PostAdapter(posts) { post ->
                        val bundle = Bundle().apply {
                            putString("postId", post.postId)
                            putString("title", post.title)
                            putString("imageUrl", post.imageUrl)
                            putString("userId", post.userId)
                        }
                        findNavController().navigate(R.id.postDetailFragment, bundle)
                    }
                }
            }.onFailure { e ->
                Toast.makeText(context, "Failed to load designs: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCategories(view: View) {
        val rvCategories = view.findViewById<RecyclerView>(R.id.rvCategories)

        val categories = listOf(
            Category("All", true),
            Category("House"),
            Category("Interior"),
            Category("Material"),
            Category("Apartment"),
            Category("Minimalist")
        )

        rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = CategoryAdapter(categories) { selectedCategory ->
            Toast.makeText(requireContext(), "Filter: $selectedCategory", Toast.LENGTH_SHORT).show()
        }
    }
}