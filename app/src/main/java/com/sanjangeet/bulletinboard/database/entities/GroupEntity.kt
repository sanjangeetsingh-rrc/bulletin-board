package com.sanjangeet.bulletinboard.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey val id: Int,
    val name: String
)
