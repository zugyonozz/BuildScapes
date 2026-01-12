package com.example.buildscapes.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.buildscapes.R
import com.example.buildscapes.adapter.DesignAdapter
import com.example.buildscapes.ui.viewmodel.DesignViewModel
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {

    private val viewModel: DesignViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSaved = view.findViewById<RecyclerView>(R.id.rvSaved)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        rvSaved.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.allSavedDesigns.collect { savedList ->
                // Update Adapter otomatis setiap data berubah!
                rvSaved.adapter = DesignAdapter(savedList) { item ->
                    val bundle = Bundle().apply {
                        putInt("id", item.id)
                        putString("title", item.title)
                        putString("imageUrl", item.imageUrl)
                    }
                    findNavController().navigate(R.id.postDetailFragment, bundle)
                }
            }
        }
    }
}