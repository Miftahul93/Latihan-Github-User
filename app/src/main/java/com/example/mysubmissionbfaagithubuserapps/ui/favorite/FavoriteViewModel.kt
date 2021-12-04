package com.example.mysubmissionbfaagithubuserapps.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mysubmissionbfaagithubuserapps.database.DatabaseFavoriteUser
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUser
import com.example.mysubmissionbfaagithubuserapps.database.FavoriteUserDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDB: DatabaseFavoriteUser? = DatabaseFavoriteUser.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDB?.favoriteUserDao()

    fun getUserFavorite(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}