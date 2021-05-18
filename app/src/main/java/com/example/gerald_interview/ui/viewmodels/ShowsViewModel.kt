package com.example.gerald_interview.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gerald_interview.data.models.ApiResponse
import com.example.gerald_interview.data.models.Show
import com.example.gerald_interview.data.repos.ShowsRepo
import com.example.gerald_interview.data.repos.ShowsRepoImpl

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

    fun groupShows(shows: List<Show>): Map<String, List<Show>>{
        val groupedShows = mutableMapOf<String, MutableList<Show>>()
        for(i in shows){
            if(groupedShows.containsKey(i.category))
                groupedShows[i.category]?.add(i)
            else
                groupedShows[i.category] = mutableListOf(i)
        }
        return groupedShows
    }

}