package com.example.gerald_interview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowsViewModel(private val showsRepo: ShowsRepo = ShowsRepoImpl()) :ViewModel() {
    private val showURL = MutableLiveData<String>()
    private val immutableShowURL: LiveData<String> = showURL

    fun getShows(): LiveData<ApiResponse>{
        return showsRepo.getShows()
    }

    fun getShowURL(): LiveData<String>{
        return immutableShowURL
    }

    fun setVideoURL(show: Show){
        showURL.value = show.content.video
    }

}