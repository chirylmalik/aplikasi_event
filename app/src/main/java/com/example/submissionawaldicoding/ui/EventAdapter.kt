package com.example.submissionawaldicoding.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawaldicoding.data.remote.ListEventsItem
import com.example.submissionawaldicoding.databinding.ItemUpcomingEventBinding

class EventAdapter : androidx.recyclerview.widget.ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUpcomingEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class MyViewHolder(val binding: ItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(binding.ivBanner.context)
                .load(event.mediaCover)
                .into(binding.ivBanner)

            binding.tvEvent.text = "${event.name}"

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}