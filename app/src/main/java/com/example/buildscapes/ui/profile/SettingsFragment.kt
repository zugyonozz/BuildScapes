package com.example.buildscapes.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.R
import com.example.buildscapes.util.FirebaseManager
import com.example.buildscapes.util.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val firebaseManager = FirebaseManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val tvEmail = view.findViewById<TextView>(R.id.tvSettingsEmail)
        val tvUserName = view.findViewById<TextView>(R.id.tvSettingsUserName)

        // Load user profile
        user?.let {
            tvEmail.text = it.email

            lifecycleScope.launch {
                val result = firebaseManager.getUserProfile(it.uid)
                result.onSuccess { userProfile ->
                    tvUserName.text = userProfile.getFullName()
                }
            }
        }

        // Edit Profile
        view.findViewById<TextView>(R.id.menuEditProfile).setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_edit)
        }

        // Change Password
        view.findViewById<TextView>(R.id.menuChangePassword).setOnClickListener {
            showChangePasswordDialog()
        }

        // Notifications
        view.findViewById<TextView>(R.id.menuNotifications).setOnClickListener {
            Toast.makeText(requireContext(), "Notification Settings Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        // Security & Privacy
        view.findViewById<TextView>(R.id.menuSecurity).setOnClickListener {
            Toast.makeText(requireContext(), "Security Settings Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        // About
        view.findViewById<TextView>(R.id.menuAbout).setOnClickListener {
            showAboutDialog()
        }

        // Delete Account
        view.findViewById<TextView>(R.id.menuDeleteAccount).setOnClickListener {
            showDeleteAccountDialog()
        }

        // Logout
        view.findViewById<TextView>(R.id.menuLogout).setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showChangePasswordDialog() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(context, "Error: Not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Change Password")
            .setMessage("We'll send a password reset link to ${user.email}")
            .setPositiveButton("Send Link") { _, _ ->
                user.email?.let { email ->
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Password reset link sent to your email!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to send: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("About BuildScapes")
            .setMessage("BuildScapes v1.0\n\nA platform for architects and designers to share and discover inspiring architectural designs.\n\n© 2025 BuildScapes")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showDeleteAccountDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to permanently delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                confirmDeleteAccount()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(context, "Error: Not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Re-authenticate for sensitive operation
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Delete")
            .setMessage("Type 'DELETE' to confirm account deletion")
            .setPositiveButton("Confirm") { _, _ ->
                lifecycleScope.launch {
                    try {
                        // Delete user data from Firestore
                        // Delete user posts
                        // Delete profile images
                        // Finally delete auth account
                        user.delete().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val session = SessionManager(requireContext())
                                session.isLoggedIn = false
                                Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_settings_to_login)
                            } else {
                                Toast.makeText(context, "Failed to delete: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Log Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                val session = SessionManager(requireContext())
                session.isLoggedIn = false
                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_settings_to_login)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}