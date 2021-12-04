package com.example.mysubmissionbfaagithubuserapps.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysubmissionbfaagithubuserapps.api.ApiClient
import com.example.mysubmissionbfaagithubuserapps.database.DatabaseFavoriteUser
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUser
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUserDao
import com.example.mysubmissionbfaagithubuserapps.model.UserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<UserDetailResponse>()

    private var userDB: DatabaseFavoriteUser? = DatabaseFavoriteUser.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDB?.favoriteUserDao()
    
    fun setUserDetail(username: String) {
        ApiClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun addToFavoriteUser(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                avatarUrl,
                id,
                username
            )
            userDao?.addToFavorite(user)
        }
    }

    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    suspend fun checkUserFavorite(id: Int) = userDao?.checkUser(id)
}