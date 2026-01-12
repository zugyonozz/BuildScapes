package com.example.buildscapes.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.adapter.PostAdapter
import com.example.buildscapes.data.model.User
import com.example.buildscapes.util.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var firebaseManager: FirebaseManager
    private lateinit var imgProfile: CircleImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvName: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvPostsCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView
    private lateinit var rvProfileDesigns: RecyclerView
    private lateinit var btnEditProfile: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseManager = FirebaseManager()

        imgProfile = view.findViewById(R.id.imgProfile)
        tvUsername = view.findViewById(R.id.tv_profile_fragment_username)
        tvName = view.findViewById(R.id.tv_profile_fragment_name)
        tvBio = view.findViewById(R.id.tvBio)
        tvPostsCount = view.findViewById(R.id.tvPostsCount)
        tvFollowersCount = view.findViewById(R.id.tvFollowersCount)
        tvFollowingCount = view.findViewById(R.id.tvFollowingCount)
        rvProfileDesigns = view.findViewById(R.id.rvProfileDesigns)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        val btnSettings = view.findViewById<ImageView>(R.id.btnSettings)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvProfileDesigns.layoutManager = layoutManager

        loadUserProfile()

        btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_edit)
        }

        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    private fun loadUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "Error: Not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            // Load user profile
            val userResult = firebaseManager.getUserProfile(userId)
            userResult.onSuccess { user ->
                updateUI(user)
                loadUserPosts(userId)
            }.onFailure { e ->
                Toast.makeText(context, "Failed to load profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: User) {
        tvUsername.text = user.getDisplayName()
        tvName.text = user.getFullName()
        tvBio.text = user.bio.ifEmpty { "No bio yet" }
        tvPostsCount.text = user.postsCount.toString()
        tvFollowersCount.text = formatCount(user.followersCount)
        tvFollowingCount.text = user.followingCount.toString()

        if (user.profileImageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(user.profileImageUrl)
                .placeholder(R.drawable.ic_person_black)
                .circleCrop()
                .into(imgProfile)
        }
    }

    private fun loadUserPosts(userId: String) {
        lifecycleScope.launch {
            val postsResult = firebaseManager.getUserPosts(userId)
            postsResult.onSuccess { posts ->
                rvProfileDesigns.adapter = PostAdapter(posts) { post ->
                    val bundle = Bundle().apply {
                        putString("postId", post.postId)
                        putString("title", post.title)
                        putString("imageUrl", post.imageUrl)
                        putString("userId", post.userId)
                    }
                    findNavController().navigate(R.id.postDetailFragment, bundle)
                }
            }.onFailure { e ->
                Toast.makeText(context, "Failed to load posts: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1000000 -> String.format("%.1fM", count / 1000000.0)
            count >= 1000 -> String.format("%.1fk", count / 1000.0)
            else -> count.toString()
        }
    }
}