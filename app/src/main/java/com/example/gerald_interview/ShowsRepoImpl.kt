package com.example.gerald_interview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SHOWS_REPO_IMPL"

class ShowsRepoImpl: ShowsRepo {
    private val retrofitClient = RetrofitClient()

    override fun getShows(): LiveData<ApiResponse> {
        val data = MutableLiveData<ApiResponse>()
        retrofitClient.getShows().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                data.value = response.body()
                Log.d(TAG, "RESPONSE: ${response.body()}")
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//               data.value = null
                Log.d(TAG, "Error: ${t}")
            }
        })
        return data
    }

}