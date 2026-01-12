package com.example.buildscapes.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildscapes.R
import com.example.buildscapes.adapter.NotificationAdapter
import com.example.buildscapes.data.model.NotificationItem

class NotificationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNotifications = view.findViewById<RecyclerView>(R.id.rvNotifications)
        rvNotifications.layoutManager = LinearLayoutManager(requireContext())

        // Data Dummy Notifikasi
        val dummyData = listOf(
            NotificationItem(
                1,
                "Welcome to BuildScapes!",
                "Start exploring the best architectural designs now.",
                "Just now",
                true
            ),
            NotificationItem(
                2,
                "New Feature Alert",
                "You can now save designs to your bookmark collection.",
                "5m ago",
                true
            ),
            NotificationItem(
                3,
                "Design of the Week",
                "Check out the new Modern Villa in Bali.",
                "1h ago",
                false
            ),
            NotificationItem(
                4,
                "Profile Update",
                "Please complete your profile to get better recommendations.",
                "1d ago",
                false
            )
        )

        rvNotifications.adapter = NotificationAdapter(dummyData)
    }
}