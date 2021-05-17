package com.example.gerald_interview

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("test/interview.json")
    fun getShows() : Call<ApiResponse>
}