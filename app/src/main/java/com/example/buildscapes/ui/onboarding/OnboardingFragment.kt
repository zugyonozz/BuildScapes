package com.example.buildscapes.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.buildscapes.R
import com.example.buildscapes.adapter.OnboardingAdapter
import com.example.buildscapes.adapter.OnboardingItemAdapter
import com.example.buildscapes.util.SessionManager

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private lateinit var dots: List<View>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerOnboarding)
        val btnAction = view.findViewById<Button>(R.id.btnOnboardingAction)

        dots = listOf(
            view.findViewById(R.id.dot1),
            view.findViewById(R.id.dot2),
            view.findViewById(R.id.dot3)
        )

        val onboardingItemAdapters = listOf(
            OnboardingItemAdapter(
                R.drawable.bg_house_exterior,
                "Explore inspiring home designs to spark your next big idea.",
                ""
            ),
            OnboardingItemAdapter(
                R.drawable.bg_kitchen,
                "Discover interior layouts that bring comfort, style, and character.",
                ""
            ),
            OnboardingItemAdapter(
                R.drawable.bg_materials,
                "Find the right materials to build stronger, smarter structures.",
                ""
            )
        )

        viewPager.adapter = OnboardingAdapter(onboardingItemAdapters)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                updateDots(position)

                if (position == onboardingItemAdapters.size - 1) {
                    btnAction.text = "GET STARTED"
                } else {
                    btnAction.text = "NEXT"
                }
            }
        })

        btnAction.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < onboardingItemAdapters.size - 1) {
                viewPager.currentItem = currentItem + 1
            } else {
                SessionManager(requireContext()).isFirstRun = false
                findNavController().navigate(R.id.action_onboarding_to_landing)
            }
        }
    }

    private fun updateDots(currentPosition: Int) {
        dots.forEachIndexed { index, dot ->
            val layoutParams = dot.layoutParams

            if (index == currentPosition) {
                layoutParams.width = dpToPx(24)
                dot.setBackgroundResource(R.drawable.dot_active)
            } else {
                layoutParams.width = dpToPx(8)
                dot.setBackgroundResource(R.drawable.dot_inactive)
            }

            dot.layoutParams = layoutParams
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}