package com.example.mysubmissionbfaagithubuserapps.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "tb_favorite")
data class FavoriteUser(

    @ColumnInfo(name = "avatarUrl")
    val avatar_url: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "username")
    val login: String
) : Serializable