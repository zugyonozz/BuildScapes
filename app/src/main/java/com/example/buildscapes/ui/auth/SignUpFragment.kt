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

class SignUpFragment : Fragment(R.layout.fragment_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSignUp = view.findViewById<Button>(R.id.btnDoSignUp)
        val tvLogin = view.findViewById<TextView>(R.id.tv_login_link)

        val etEmail = view.findViewById<EditText>(R.id.etSignEmail)
        val etUser = view.findViewById<EditText>(R.id.etSignUsername)
        val etPass = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etSignPassword)
        val etConf = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etSignConfirmPass)

        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val username = etUser.text.toString().trim()
            val password = etPass.text.toString().trim()
            val confirmPass = etConf.text.toString().trim()

            when {
                email.isEmpty() || username.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(context, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
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
                    // ✅ TODO: Tambahkan Firebase/API untuk register
                    Toast.makeText(context, "Akun $username berhasil dibuat! 🎉", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signup_to_login)
                }
            }
        }

        tvLogin.setOnClickListener {
            // Kembali ke Login
            findNavController().popBackStack()
        }
    }
}