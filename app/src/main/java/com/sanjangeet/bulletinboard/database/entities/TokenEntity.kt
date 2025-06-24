package com.sanjangeet.bulletinboard.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: Int = 0,
    val refresh: String
)
