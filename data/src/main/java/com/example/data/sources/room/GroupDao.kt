package com.example.data.sources.room

import androidx.room.*
import com.example.data.sources.group.room.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("DELETE FROM groups_table WHERE group_id = :groupId")
    suspend fun deleteByGroupId(groupId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(group: GroupEntity)

    @Query("SELECT * FROM groups_table ORDER BY id ASC")
    fun getAllGroups(): Flow<List<GroupEntity>>

    /*
    @Query("SELECT id, group_name FROM groups_table WHERE group_id = :groupId")
    fun getNameByGroupId(groupId: String): Flow<GroupGetName?>

    @Update(entity = GroupEntity::class)
    suspend fun updateGroupName(name: GroupUpdateName)
    */
}