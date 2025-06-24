package com.sanjangeet.bulletinboard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjangeet.bulletinboard.database.entities.GroupEntity

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(groups: List<GroupEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(group: GroupEntity)

    @Query("SELECT * FROM `groups` WHERE id = :groupId") // <table or subquery> expected, got 'groups'
    suspend fun get(groupId: Int): GroupEntity?

    @Query("DELETE FROM `groups` WHERE id = :groupId")
    suspend fun delete(groupId: Int)
}
