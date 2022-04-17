package com.example.mysubmissionbfaagithubuserapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class DatabaseFavoriteUser : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: DatabaseFavoriteUser? = null

        fun getDatabase(context: Context): DatabaseFavoriteUser? {
            if (INSTANCE == null) {
                synchronized(DatabaseFavoriteUser::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseFavoriteUser::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}