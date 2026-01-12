package com.example.buildscapes.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.data.model.User
import com.example.buildscapes.util.FirebaseManager
import com.example.buildscapes.util.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var ivProfile: ImageView
    private lateinit var btnContinue: Button
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhone: EditText

    private var imageUri: Uri? = null
    private val firebaseManager = FirebaseManager()

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            Glide.with(this).load(it).circleCrop().into(ivProfile)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfile = view.findViewById(R.id.ivProfileImage)
        btnContinue = view.findViewById(R.id.btnContinue)
        etFirstName = view.findViewById(R.id.etFirstName)
        etLastName = view.findViewById(R.id.etLastName)
        etPhone = view.findViewById(R.id.etPhone)

        view.findViewById<View>(R.id.layoutAvatar).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnContinue.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            when {
                firstName.isEmpty() -> {
                    Toast.makeText(context, "First name is required!", Toast.LENGTH_SHORT).show()
                }
                lastName.isEmpty() -> {
                    Toast.makeText(context, "Last name is required!", Toast.LENGTH_SHORT).show()
                }
                phone.isEmpty() -> {
                    Toast.makeText(context, "Phone number is required!", Toast.LENGTH_SHORT).show()
                }
                phone.length < 10 -> {
                    Toast.makeText(context, "Phone number must be at least 10 digits!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    saveUserProfile(firstName, lastName, phone)
                }
            }
        }
    }

    private fun saveUserProfile(firstName: String, lastName: String, phone: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Error: No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        btnContinue.isEnabled = false
        btnContinue.text = "Saving Profile..."

        lifecycleScope.launch {
            try {
                var profileImageUrl = ""

                // Upload profile image if selected
                imageUri?.let { uri ->
                    val uploadResult = firebaseManager.uploadProfileImage(currentUser.uid, uri)
                    uploadResult.onSuccess { url ->
                        profileImageUrl = url
                    }.onFailure {
                        Toast.makeText(context, "Failed to upload image, continuing without it", Toast.LENGTH_SHORT).show()
                    }
                }

                // Create user object
                val user = User(
                    uid = currentUser.uid,
                    email = currentUser.email ?: "",
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phone,
                    profileImageUrl = profileImageUrl,
                    bio = "",
                    postsCount = 0,
                    followersCount = 0,
                    followingCount = 0
                )

                // Save to Firestore
                val saveResult = firebaseManager.saveUserProfile(user)

                saveResult.onSuccess {
                    // Update session
                    val session = SessionManager(requireContext())
                    session.isLoggedIn = true

                    Toast.makeText(context, "Profile Saved Successfully! 🎉", Toast.LENGTH_SHORT).show()

                    // Navigate to home
                    findNavController().navigate(R.id.action_setup_to_home)
                }.onFailure { e ->
                    btnContinue.isEnabled = true
                    btnContinue.text = "CONTINUE"
                    Toast.makeText(context, "Failed to save profile: ${e.message}", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                btnContinue.isEnabled = true
                btnContinue.text = "CONTINUE"
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}