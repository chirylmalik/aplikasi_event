package com.example.submissionawaldicoding.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawaldicoding.ui.detail.EventDetailAdapter
import com.example.submissionawaldicoding.data.local.room.EventDatabase
import com.example.submissionawaldicoding.databinding.FragmentFavoriteBinding
import com.example.submissionawaldicoding.ui.detail.DetailActivity
import com.example.submissionawaldicoding.utils.toListEventsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: EventDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = EventDatabase.getDatabase(requireContext())
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val favorites = database.eventDao().getAllFavorites()
            val listEvents = favorites.map { it.toListEventsItem() }

            CoroutineScope(Dispatchers.Main).launch {
                if (listEvents.isEmpty()) {
                    binding.tvEmptyFavorite.visibility = View.VISIBLE
                } else {
                    binding.tvEmptyFavorite.visibility = View.GONE
                }
                val adapter = EventDetailAdapter { event ->
                    Log.d("FavoriteFragment", "Selected Event ID: ${event.id}")
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra("EVENT_ID", event.id.toString())
                    }
                    startActivity(intent)
                }
                adapter.submitList(listEvents)
                binding.rvFavorite.adapter = adapter
                binding.progressBar.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}