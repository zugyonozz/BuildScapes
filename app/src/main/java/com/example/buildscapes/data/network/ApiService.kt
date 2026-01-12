package com.example.buildscapes.data.network

import com.example.buildscapes.data.model.DesignItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("designs")
    fun getDesigns(): Call<List<DesignItem>>
}