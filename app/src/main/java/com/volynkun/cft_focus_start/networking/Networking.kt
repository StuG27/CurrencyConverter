package com.volynkun.cft_focus_start.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create


object Networking {

    private val okhttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .client(okhttpClient)
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api: Api
        get() = retrofit.create()

}