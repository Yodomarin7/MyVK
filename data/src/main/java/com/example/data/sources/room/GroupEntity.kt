package com.example.data.sources.group.room

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.NOCASE
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "groups_table",
    indices = [
        Index("group_id", unique = true)
    ]
)
class GroupEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "group_id", collate = NOCASE) val groupId: Int,
    @ColumnInfo(name = "screen_name", collate = NOCASE) val screenName: String,
    @ColumnInfo(name = "group_name") val groupName: String,
    @ColumnInfo(name = "group_description") val groupDescription: String,
)

/*
data class GroupGetName(
    val id: Int,
    val group_name: String
)

data class GroupUpdateName(
    val id: Int,
    val group_name: String
)
*/