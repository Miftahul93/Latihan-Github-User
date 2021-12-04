package com.example.mysubmissionbfaagithubuserapps.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String
)
