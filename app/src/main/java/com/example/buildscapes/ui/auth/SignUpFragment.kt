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
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val btnSignUp = view.findViewById<Button>(R.id.btnDoSignUp)
        val tvLogin = view.findViewById<TextView>(R.id.tv_login_link)
        val etEmail = view.findViewById<EditText>(R.id.etSignEmail)
        val etPass = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etSignPassword)
        val etConf = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etSignConfirmPass)

        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()
            val confirmPass = etConf.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(context, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(context, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(context, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                }
                password != confirmPass -> {
                    Toast.makeText(context, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    btnSignUp.isEnabled = false
                    btnSignUp.text = "Creating Account..."

                    // Create account and go directly to setup profile
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Account created! Please complete your profile 🎉", Toast.LENGTH_SHORT).show()

                                // Navigate to setup profile (stay logged in)
                                findNavController().navigate(R.id.action_signup_to_setup)
                            } else {
                                btnSignUp.isEnabled = true
                                btnSignUp.text = getString(R.string.sign_up)
                                Toast.makeText(context, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

        tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}