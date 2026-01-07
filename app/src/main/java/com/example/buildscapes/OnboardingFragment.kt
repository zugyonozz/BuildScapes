package com.example.buildscapes

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.buildscapes.adapter.OnboardingAdapter
import com.example.buildscapes.adapter.OnboardingItemAdapter
import com.example.buildscapes.util.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerOnboarding)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutIndicator)
        val btnAction = view.findViewById<Button>(R.id.btnOnboardingAction)
        
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
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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
                findNavController().navigate(R.id.action_onboarding_to_welcome)
            }
        }
    }
}