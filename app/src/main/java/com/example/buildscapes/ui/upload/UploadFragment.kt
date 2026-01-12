package com.example.buildscapes.ui.upload

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.data.model.Post
import com.example.buildscapes.util.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UploadFragment : Fragment() {

    private var selectedImageUri: Uri? = null
    private lateinit var imgPreview: ImageView
    private lateinit var btnUpload: Button
    private lateinit var etTitle: EditText

    private val firebaseManager = FirebaseManager()

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            Glide.with(this).load(uri).into(imgPreview)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgPreview = view.findViewById(R.id.imgPreview)
        btnUpload = view.findViewById(R.id.btnUpload)
        val cardImage = view.findViewById<View>(R.id.cardImagePreview)
        etTitle = view.findViewById(R.id.etTitle)

        cardImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnUpload.setOnClickListener {
            val title = etTitle.text.toString().trim()

            when {
                selectedImageUri == null -> {
                    Toast.makeText(requireContext(), "Please select an image first!", Toast.LENGTH_SHORT).show()
                }
                title.isEmpty() -> {
                    Toast.makeText(requireContext(), "Title cannot be empty!", Toast.LENGTH_SHORT).show()
                }
                title.length < 3 -> {
                    Toast.makeText(requireContext(), "Title must be at least 3 characters!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    uploadDesign(title)
                }
            }
        }
    }

    private fun uploadDesign(title: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val imageUri = selectedImageUri

        if (user == null) {
            Toast.makeText(context, "Error: Not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        if (imageUri == null) {
            Toast.makeText(context, "Error: No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        btnUpload.isEnabled = false
        btnUpload.text = "Uploading..."

        lifecycleScope.launch {
            try {
                // Get user profile first
                val userResult = firebaseManager.getUserProfile(user.uid)

                userResult.onSuccess { userProfile ->
                    lifecycleScope.launch {
                        // Upload image
                        val uploadResult = firebaseManager.uploadPostImage(user.uid, imageUri)

                        uploadResult.onSuccess { imageUrl ->
                            // Create post
                            val post = Post(
                                userId = user.uid,
                                title = title,
                                imageUrl = imageUrl,
                                userName = userProfile.getFullName(),
                                userProfileImage = userProfile.profileImageUrl,
                                likesCount = 0,
                                commentsCount = 0
                            )

                            val postResult = firebaseManager.createPost(post)

                            postResult.onSuccess {
                                Toast.makeText(context, "Design uploaded successfully! 🎉", Toast.LENGTH_LONG).show()

                                // Reset form
                                imgPreview.setImageResource(R.drawable.ic_upload)
                                etTitle.text.clear()
                                selectedImageUri = null

                                btnUpload.isEnabled = true
                                btnUpload.text = "Upload Design"
                            }.onFailure { e ->
                                btnUpload.isEnabled = true
                                btnUpload.text = "Upload Design"
                                Toast.makeText(context, "Failed to create post: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }.onFailure { e ->
                            btnUpload.isEnabled = true
                            btnUpload.text = "Upload Design"
                            Toast.makeText(context, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.onFailure { e ->
                    btnUpload.isEnabled = true
                    btnUpload.text = "Upload Design"
                    Toast.makeText(context, "Failed to get user profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                btnUpload.isEnabled = true
                btnUpload.text = "Upload Design"
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}