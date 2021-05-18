package com.example.gerald_interview.data.repos

import androidx.lifecycle.LiveData
import com.example.gerald_interview.data.models.ApiResponse

interface ShowsRepo {
    fun getShows() : LiveData<ApiResponse>
}