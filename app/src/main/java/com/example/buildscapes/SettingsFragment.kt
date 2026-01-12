package com.example.buildscapes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.util.SessionManager
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val tvEmail = view.findViewById<TextView>(R.id.tvSettingsEmail)

        if (user != null) {
            tvEmail.text = user.email // Tampilkan Email Asli
        } else {
            tvEmail.text = "Guest Mode"
        }

        view.findViewById<TextView>(R.id.menuEditProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.menuLogout).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    val session = SessionManager(requireContext())
                    session.isLoggedIn = false
                    findNavController().navigate(R.id.action_settings_to_login)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}