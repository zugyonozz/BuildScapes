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
        val tvLogin = view.findViewById<TextView>(R.id.tvGoToLogin)

        // Input Fields
        val etEmail = view.findViewById<EditText>(R.id.etSignEmail)
        val etUser = view.findViewById<EditText>(R.id.etSignUsername)
        val etPass = view.findViewById<EditText>(R.id.etSignPassword)
        val etConf = view.findViewById<EditText>(R.id.etSignConfirmPass)

        // Klik tombol Sign Up
        btnSignUp.setOnClickListener {
            // need extend
            if (etEmail.text.isEmpty() || etUser.text.isEmpty() || etPass.text.isEmpty()) {
                Toast.makeText(context, "required fill!", Toast.LENGTH_SHORT).show()
            } else if (etPass.text.toString() != etConf.text.toString()) {
                Toast.makeText(context, "Password isn't match!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Account ${etUser.text} successfully created!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_signup_to_home)
            }
        }

        tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}