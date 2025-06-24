package com.sanjangeet.bulletinboard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjangeet.bulletinboard.database.entities.TokenEntity

@Dao
interface TokenDao {

    @Query("SELECT * FROM tokens WHERE id = 0")
    suspend fun get(): TokenEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(token: TokenEntity)

    @Query("DELETE FROM tokens WHERE id = 0")
    suspend fun delete()
}
