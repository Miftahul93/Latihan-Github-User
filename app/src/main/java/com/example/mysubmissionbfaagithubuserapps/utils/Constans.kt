package com.example.mysubmissionbfaagithubuserapps.utils

class Constans {
    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val MY_GITHUB_USER_API = "BuildConfig.MyGithubUserApiKey"
        const val SEARCH = "/search/users"
        const val DETAIL = "/users/{username}"
        const val DETAIL_FOLLOWERS = "/users/{username}/followers"
        const val DETAIL_FOLLOWING = "/users/{username}/following"
        const val TIMES = 3000
    }
}