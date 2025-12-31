package com.example.buildscapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.buildscapes.adapter.DesignAdapter
import com.example.buildscapes.manager.BookmarkManager

class SavedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSavedGrid(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { setupSavedGrid(it) }
    }

    private fun setupSavedGrid(view: View) {
        val rvSaved = view.findViewById<RecyclerView>(R.id.rvSaved)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvSaved.layoutManager = layoutManager

        val savedData = BookmarkManager.getBookmarks()
        rvSaved.adapter = DesignAdapter(savedData)
    }
}