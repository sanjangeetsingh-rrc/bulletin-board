package com.sanjangeet.bulletinboard.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["groupId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PostEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val groupId: Int
)
