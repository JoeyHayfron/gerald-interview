package com.example.gerald_interview

import androidx.lifecycle.LiveData

interface ShowsRepo {
    fun getShows() : LiveData<ApiResponse>
}