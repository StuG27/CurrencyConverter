package com.volynkun.cft_focus_start.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface Api {
    @GET
    fun download(
        @Url fileUrl: String
    ): Call<String>
}