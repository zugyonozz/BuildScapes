package com.example.buildscapes.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.R
import com.example.buildscapes.util.SessionManager

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogin = view.findViewById<Button>(R.id.btnDoLogin)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPass = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)
        val tvSignUp = view.findViewById<TextView>(R.id.tvSignupLink)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()

            when {
                email.isEmpty() || pass.isEmpty() -> {
                    Toast.makeText(context, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
                }
                pass.length < 6 -> {
                    Toast.makeText(context, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val session = SessionManager(requireContext())
                    session.isLoggedIn = true

                    Toast.makeText(context, "Login berhasil! Welcome back 🎉", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_login_to_home)
                }
            }
        }

        tvSignUp?.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }
    }
}