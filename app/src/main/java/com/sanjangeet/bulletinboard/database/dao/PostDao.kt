package com.sanjangeet.bulletinboard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjangeet.bulletinboard.database.entities.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(post: PostEntity)

    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE groupId = :groupId")
    suspend fun get(groupId: Int): List<PostEntity>

    @Query("DELETE FROM posts WHERE groupId = :groupId")
    suspend fun delete(groupId: Int)
}
