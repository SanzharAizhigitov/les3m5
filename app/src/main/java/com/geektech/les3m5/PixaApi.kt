package com.geektech.les3m5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {
    @GET("/api/")
    fun getImages(
        @Query("key") key: String = "33923372-9aec56f5b75fc3a718c89c6ff",
        @Query("q") pictureWord: String,
        @Query("per_page") perPage:Int = 5,
        @Query("page") page:Int
    ):Call<PixaModel>
}