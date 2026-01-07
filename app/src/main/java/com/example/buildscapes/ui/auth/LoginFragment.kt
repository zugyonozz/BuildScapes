package com.example.buildscapes.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogin = view.findViewById<Button>(R.id.btnDoLogin)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPass = view.findViewById<EditText>(R.id.etPassword)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()

            // need extend
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                Toast.makeText(context, "Login Success (Dummy)", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_login_to_home)
            } else {
                Toast.makeText(context, "Isi dulu woi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}