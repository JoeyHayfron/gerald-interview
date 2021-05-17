package com.example.gerald_interview

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val api: Api

    companion object{
        private const val BASE_URL = "http://a.jsrdn.com/"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getShows() : Call<ApiResponse> {
        return api.getShows()
    }
}