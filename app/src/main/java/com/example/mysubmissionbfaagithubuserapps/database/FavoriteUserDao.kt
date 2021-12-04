package com.example.mysubmissionbfaagithubuserapps.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM tb_favorite")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM tb_favorite WHERE tb_favorite.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM tb_favorite WHERE tb_favorite.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}