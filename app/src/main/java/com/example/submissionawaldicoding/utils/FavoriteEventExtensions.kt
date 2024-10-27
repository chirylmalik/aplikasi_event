package com.example.submissionawaldicoding.utils

import com.example.submissionawaldicoding.data.local.entity.EventEntity
import com.example.submissionawaldicoding.data.remote.response.ListEventsItem

fun EventEntity.toListEventsItem(): ListEventsItem {
    return ListEventsItem(
        id = this.id,
        name = this.name,
        mediaCover = this.mediaCover,
        link = this.link,
        description = this.description,
        ownerName = this.ownerName,
        beginTime = this.beginTime,
        endTime = this.endTime,
        quota = this.quota,
        registrants = this.registrants,
        cityName = this.cityName,
        category = this.category,
        summary = this.summary,
        imageLogo = this.imageLogo,
    )
}