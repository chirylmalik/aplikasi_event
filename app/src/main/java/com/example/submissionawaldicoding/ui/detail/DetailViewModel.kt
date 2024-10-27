package com.example.submissionawaldicoding.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawaldicoding.data.remote.response.DetailEventResponse
import com.example.submissionawaldicoding.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {
    private val _eventDetail = MutableLiveData<DetailEventResponse?>()
    val eventDetail: MutableLiveData<DetailEventResponse?> get() = _eventDetail

    fun fetchEventDetail(id: String) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getDetailEvent(id)
        call.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    val detailEventResponse = response.body()
                    if (detailEventResponse != null && !detailEventResponse.error!!) {
                        _eventDetail.value = detailEventResponse
                    }
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
            }
        })
    }
}