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
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

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
                    btnLogin.isEnabled = false
                    btnLogin.text = "Logging in..."

                    // 🔥 LOGIN FIREBASE
                    auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            btnLogin.isEnabled = true
                            btnLogin.text = getString(R.string.login)

                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                val name = user?.displayName ?: "User"
                                val session = SessionManager(requireContext())
                                session.isLoggedIn = true

                                Toast.makeText(context, "Welcome back, $name! 🎉", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_login_to_home)
                            } else {
                                Toast.makeText(context, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

        tvSignUp?.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }
    }
}