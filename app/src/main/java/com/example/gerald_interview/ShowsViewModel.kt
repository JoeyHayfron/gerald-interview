package com.example.gerald_interview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowsViewModel(private val showsRepo: ShowsRepoImpl = ShowsRepoImpl()) :ViewModel(), InnerAdapter.OnShowListener {
    private val showURL = MutableLiveData<String>()
    private val immutableShowURL: LiveData<String> = showURL

    fun getShows(): LiveData<ApiResponse>{
        return showsRepo.getShows()
    }

    fun getShowURL(){
        Log.d("LLL", "Something")
    }

    override fun onShowClicked(show: Show) {
        Log.d("HHH", show.toString())
        getShowURL()
//        showURL.value = show.content.video
    }
}