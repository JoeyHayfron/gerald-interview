package com.example.gerald_interview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ShowsViewModel(private val showsRepo: ShowsRepoImpl = ShowsRepoImpl()) :ViewModel() {

    fun getShows(): LiveData<ApiResponse>{
        return showsRepo.getShows()
    }
}