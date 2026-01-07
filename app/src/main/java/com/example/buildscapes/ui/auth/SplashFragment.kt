package com.example.buildscapes.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buildscapes.R
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.example.buildscapes.util.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = SessionManager(requireContext())
        lifecycleScope.launch {
            delay(3000)
            if(session.isFirstRun) {
                findNavController().navigate(R.id.action_splash_to_welcome)
                session.isFirstRun = false
            } else if(session.isLoggedIn) {
                findNavController().navigate(R.id.action_splash_to_discover)
            } else {
                findNavController().navigate(R.id.action_splash_to_login)
            }
        }
    }
}