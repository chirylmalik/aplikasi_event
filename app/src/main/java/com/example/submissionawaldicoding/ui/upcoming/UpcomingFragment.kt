package com.example.submissionawaldicoding.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawaldicoding.ui.detail.EventDetailAdapter
import com.example.submissionawaldicoding.data.remote.response.ListEventsItem
import com.example.submissionawaldicoding.databinding.FragmentUpcomingBinding
import com.example.submissionawaldicoding.ui.detail.DetailActivity

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val upcomingViewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcoming.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUpcoming.addItemDecoration(itemDecoration)

        upcomingViewModel.event.observe(viewLifecycleOwner) { events ->
            setEventData(events)
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setEventData(events: List<ListEventsItem>) {
        val adapter = EventDetailAdapter { event ->
            Log.d("UpcomingFragment", "Selected Event ID: ${event.id}")
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", event.id.toString())
            }
            startActivity(intent)
        }
        adapter.submitList(events)
        binding.rvUpcoming.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvUpcoming.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}