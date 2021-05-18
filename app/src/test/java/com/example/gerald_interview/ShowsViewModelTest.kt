package com.example.gerald_interview

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsViewModelTest {
    private lateinit var showVieModel: ShowsViewModel

    @Mock
    lateinit var repository: ShowsRepo

    @Before
    fun setup(){
        showVieModel = ShowsViewModel(repository)
    }

    @Test
    fun fetchShowsList(){
        val mockResponse = mock(ApiResponse::class.java)
        val apiResponse = showVieModel.getShows()
        assertEquals(mockResponse, apiResponse)
    }
}