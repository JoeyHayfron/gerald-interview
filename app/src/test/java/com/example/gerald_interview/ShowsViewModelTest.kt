package com.example.gerald_interview

import androidx.lifecycle.Observer
import com.example.gerald_interview.data.models.ApiResponse
import com.example.gerald_interview.data.network.RetrofitClient
import com.example.gerald_interview.data.repos.ShowsRepo
import com.example.gerald_interview.ui.viewmodels.ShowsViewModel
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsViewModelTest {

    lateinit var server: MockWebServer
    lateinit var viewModel: ShowsViewModel

    @Mock
    lateinit var showsRepo: ShowsRepo

    @Mock
    lateinit var apiResponseObserver: Observer<ApiResponse>

    lateinit var retrofitClient: RetrofitClient



    @Before
    fun setup(){
        server = MockWebServer()
        viewModel = ShowsViewModel(showsRepo)
        viewModel.getShows().observeForever(apiResponseObserver)

        server.start()
        retrofitClient = RetrofitClient()
    }

    @After
    fun shutDown(){
        server.shutdown()
    }


    @Test
    fun fetchShowsList(){
        val response = MockResponse()
            .setBody(MockResponseFileReader("api_response.json").content)
        server.enqueue(response)

        val gson = Gson()
        val actualResponse = retrofitClient.getShows().execute()

        assertEquals(response.getBody(), actualResponse)
    }
}