package com.example.submissionawaldicoding.data.remote.retrofit

import com.example.submissionawaldicoding.data.remote.response.DetailEventResponse
import com.example.submissionawaldicoding.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events?active=1")
    fun getUpcomingEvent(): Call<EventResponse>

    @GET("events?active=0")
    fun getFinishedEvent(): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>
}