package com.example.mysubmissionbfaagithubuserapps.api

import com.example.mysubmissionbfaagithubuserapps.utils.Constans
import com.example.mysubmissionbfaagithubuserapps.model.User
import com.example.mysubmissionbfaagithubuserapps.model.UserDetailResponse
import com.example.mysubmissionbfaagithubuserapps.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Constans.SEARCH)
    @Headers("Autorization: token ${Constans.MY_GITHUB_USER_API}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET(Constans.DETAIL)
    @Headers("Autorization: token ${Constans.MY_GITHUB_USER_API}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET(Constans.DETAIL_FOLLOWERS)
    @Headers("Autorization: token ${Constans.MY_GITHUB_USER_API}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET(Constans.DETAIL_FOLLOWING)
    @Headers("Autorization: token ${Constans.MY_GITHUB_USER_API}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}