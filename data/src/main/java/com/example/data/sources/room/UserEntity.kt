package com.example.data.sources.room

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.NOCASE
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_table",
    indices = [
        Index("user_id", unique = true)
    ]
)
class UserEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id", collate = NOCASE) val userId: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "sex") val sex: Int,
    @ColumnInfo(name = "last_seen") val lastSeen: Int,
    @ColumnInfo(name = "used") val used: Int,
)