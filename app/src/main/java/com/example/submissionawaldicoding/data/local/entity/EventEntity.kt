package com.example.submissionawaldicoding.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_entity")
data class EventEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val mediaCover: String,
    val imageLogo: String,
    val link: String,
    val description: String,
    val summary: String,
    val ownerName: String,
    val beginTime: String,
    val endTime: String,
    val quota: Int,
    val registrants: Int,
    val cityName: String,
    val category: String
)