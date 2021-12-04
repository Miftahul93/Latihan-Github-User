package com.example.mysubmissionbfaagithubuserapps.api

import com.example.mysubmissionbfaagithubuserapps.utils.Constans
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constans.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: ApiService = retrofit.create(ApiService::class.java)
}