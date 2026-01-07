package com.example.buildscapes.ui.auth

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        val logo: ImageView = view.findViewById(R.id.iv_brand_anim)
        val text: TextView = view.findViewById(R.id.tv_brand_invisible)

        val spannable = SpannableString("BUILDSCAPE")
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#2B2B2B")),
            0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#C0C0C0")),
            5, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text.text = spannable

        lifecycleScope.launch {
            delay(300)
            ObjectAnimator.ofFloat(logo, "translationX", -180f).apply {
                duration = 800
                start()
            }

            delay(500)

            text.visibility = View.VISIBLE
            text.translationX = -80f
            text.alpha = 0f

            text.animate()
                .translationX(0f)
                .alpha(1f)
                .setDuration(1000)
                .start()

            delay(1200)
            when {
                session.isFirstRun -> { findNavController().navigate(R.id.action_splash_to_welcome) }
                session.isLoggedIn -> { findNavController().navigate(R.id.action_splash_to_discover) }
                else -> { findNavController().navigate(R.id.action_splash_to_login) }
            }
        }
    }
}