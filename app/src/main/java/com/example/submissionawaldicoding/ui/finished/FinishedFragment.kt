package com.example.submissionawaldicoding.ui.finished

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.submissionawaldicoding.ui.detail.EventDetailAdapter
import com.example.submissionawaldicoding.data.remote.response.ListEventsItem
import com.example.submissionawaldicoding.databinding.FragmentFinishedBinding
import com.example.submissionawaldicoding.ui.detail.DetailActivity

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFinished.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFinished.addItemDecoration(itemDecoration)

        mainViewModel.event.observe(viewLifecycleOwner) { events ->
            setEventData(events)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setEventData(events: List<ListEventsItem>) {
        val adapter = EventDetailAdapter { event ->
            Log.d("FinishedFragment", "Selected Event ID: ${event.id}")
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", event.id.toString())
            }
            startActivity(intent)
        }
        adapter.submitList(events)
        binding.rvFinished.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFinished.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
