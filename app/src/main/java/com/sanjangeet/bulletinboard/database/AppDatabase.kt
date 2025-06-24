package com.sanjangeet.bulletinboard.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanjangeet.bulletinboard.database.entities.GroupEntity
import com.sanjangeet.bulletinboard.database.entities.PostEntity
import com.sanjangeet.bulletinboard.database.entities.TokenEntity
import com.sanjangeet.bulletinboard.database.dao.TokenDao
import com.sanjangeet.bulletinboard.database.dao.GroupDao
import com.sanjangeet.bulletinboard.database.dao.PostDao

@Database(entities = [
    TokenEntity::class,
    GroupEntity::class,
    PostEntity::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun groupDao(): GroupDao
    abstract fun postDao(): PostDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bulletin_board_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
