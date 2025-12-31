package com.example.buildscapes.network

import com.example.buildscapes.model.DesignItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("designs")
    fun getDesigns(): Call<List<DesignItem>>
}