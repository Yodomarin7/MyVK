package com.example.data.repository

import com.example.data.sources.group.room.GroupEntity
import com.example.data.sources.network.GroupRetrofit
import com.example.data.sources.room.GroupDao
import com.example.domain.models.GroupModel
import com.example.domain.repository.IGroups
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//repository main
class GroupRepository(
    private val dao: GroupDao,
    private val retrofit: GroupRetrofit
    ): IGroups {

    override suspend fun getGroupsDao(): Flow<MutableList<GroupModel.Params>> {

        return dao.getAllGroups().map { it1->
            val groups: MutableList<GroupModel.Params> = mutableListOf<GroupModel.Params>()

            it1.forEach {
                val group: GroupModel.Params = GroupModel.Params(
                    id = it.groupId, 
                    screenName = it.screenName,
                    name = it.groupName,
                    description = it.groupDescription
                )
                groups.add(group)
            }

            return@map groups.toMutableList()
        }
    }

    override suspend fun deleteOneDao(groupId: Int) {
        dao.deleteByGroupId(groupId)
    }

    override suspend fun addOneDao(groupParams: GroupModel.Params) {
        val group = GroupEntity(
            id = 0,
            groupId = groupParams.id,
            screenName = groupParams.screenName,
            groupName = groupParams.name,
            groupDescription = groupParams.description
        )
        val dao = dao.insertOne(group)
    }

    override suspend fun getByIdRetrofit(groupId: String, token: String): GroupModel.ResponsParams {
        return retrofit.getById(groupId, token)
    }

}
















