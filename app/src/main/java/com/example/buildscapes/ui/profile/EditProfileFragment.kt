package com.example.buildscapes.ui.profile

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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.buildscapes.R
import com.example.buildscapes.util.FirebaseManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private lateinit var ivProfile: ImageView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etBio: EditText
    private lateinit var btnSave: Button

    private var imageUri: Uri? = null
    private val firebaseManager = FirebaseManager()

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            Glide.with(this).load(it).circleCrop().into(ivProfile)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfile = view.findViewById(R.id.ivProfileImage)
        etFirstName = view.findViewById(R.id.etFirstName)
        etLastName = view.findViewById(R.id.etLastName)
        etPhone = view.findViewById(R.id.etPhone)
        etBio = view.findViewById(R.id.etBio)
        btnSave = view.findViewById(R.id.btnSave)

        view.findViewById<View>(R.id.layoutAvatar).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        loadCurrentProfile()

        btnSave.setOnClickListener {
            saveProfile()
        }
    }

    private fun loadCurrentProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        lifecycleScope.launch {
            val result = firebaseManager.getUserProfile(userId)
            result.onSuccess { user ->
                etFirstName.setText(user.firstName)
                etLastName.setText(user.lastName)
                etPhone.setText(user.phoneNumber)
                etBio.setText(user.bio)

                if (user.profileImageUrl.isNotEmpty()) {
                    Glide.with(this@EditProfileFragment)
                        .load(user.profileImageUrl)
                        .placeholder(R.drawable.ic_person_black)
                        .circleCrop()
                        .into(ivProfile)
                }
            }
        }
    }

    private fun saveProfile() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val bio = etBio.text.toString().trim()

        when {
            firstName.isEmpty() -> {
                Toast.makeText(context, "First name is required!", Toast.LENGTH_SHORT).show()
                return
            }
            lastName.isEmpty() -> {
                Toast.makeText(context, "Last name is required!", Toast.LENGTH_SHORT).show()
                return
            }
            phone.isEmpty() -> {
                Toast.makeText(context, "Phone number is required!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        btnSave.isEnabled = false
        btnSave.text = "Saving..."

        lifecycleScope.launch {
            try {
                val updates = mutableMapOf<String, Any>(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "phoneNumber" to phone,
                    "bio" to bio
                )

                // Upload new profile image if selected
                imageUri?.let { uri ->
                    val uploadResult = firebaseManager.uploadProfileImage(userId, uri)
                    uploadResult.onSuccess { url ->
                        updates["profileImageUrl"] = url
                    }
                }

                val result = firebaseManager.updateUserProfile(userId, updates)
                result.onSuccess {
                    Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }.onFailure { e ->
                    btnSave.isEnabled = true
                    btnSave.text = "SAVE CHANGES"
                    Toast.makeText(context, "Failed to update: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                btnSave.isEnabled = true
                btnSave.text = "SAVE CHANGES"
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}