package com.example.buildscapes.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buildscapes.R
import android.widget.Button

class LandingFragment : Fragment(R.layout.fragment_landing) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_login)
        }

        view.findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_signup)
        }
    }
}