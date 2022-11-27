package com.example.data.sources.room

import androidx.room.*
import com.example.data.sources.group.room.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("DELETE FROM users_table WHERE user_id = :userId")
    suspend fun deleteByUserId(userId: Int)

    @Update
    suspend fun update(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(user: UserEntity)

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    /*
    @Query("SELECT id, group_name FROM groups_table WHERE group_id = :groupId")
    fun getNameByGroupId(groupId: String): Flow<GroupGetName?>

    @Update(entity = GroupEntity::class)
    suspend fun updateGroupName(name: GroupUpdateName)
    */
}