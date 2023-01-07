package com.example.data.repository

import com.example.data.sources.network.UserVK
import com.example.data.sources.preference.UserParamsSharedPreferences
import com.example.data.sources.room.UserEntity
import com.example.data.sources.room.UsersDao
import com.example.domain.models.UserModel
import com.example.domain.repository.IUsers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val dao: UsersDao,
    private val vk: UserVK,
    private val userParamsSharedPreferences: UserParamsSharedPreferences
) : IUsers {

    override suspend fun saveSex(i: Int) {
        userParamsSharedPreferences.saveSex(i)
    }
    override suspend fun getSex() : Int {
        return userParamsSharedPreferences.getSex()
    }

    override suspend fun saveLastSeen(i: Int) {
        userParamsSharedPreferences.saveLastSeen(i)
    }
    override suspend fun getLastSeen(): Int {
        return userParamsSharedPreferences.getLastSeen()
    }

    override suspend fun saveShowRed(b: Boolean) {
        userParamsSharedPreferences.saveShowRed(b)
    }
    override suspend fun getShowRed(): Boolean {
        return userParamsSharedPreferences.getShowRed()
    }

    override suspend fun saveShowYellow(b: Boolean) {
        userParamsSharedPreferences.saveShowYellow(b)
    }
    override suspend fun getShowYellow(): Boolean {
        return userParamsSharedPreferences.getShowYellow()
    }

    override suspend fun saveShowGreen(b: Boolean) {
        userParamsSharedPreferences.saveShowGreen(b)
    }
    override suspend fun getShowGreen(): Boolean {
        return userParamsSharedPreferences.getShowGreen()
    }

    override suspend fun saveShowBlack(b: Boolean) {
        userParamsSharedPreferences.saveShowBlack(b)
    }
    override suspend fun getShowBlack(): Boolean {
        return userParamsSharedPreferences.getShowBlack()
    }

    override suspend fun getUsersVK(
        grouId: String,
        offset: Int,
        count: Int,
        user: UserModel.Params): UserModel.ResponsParams {

        return vk.getUsersVK(grouId, offset, count, user)
    }

    override suspend fun getUsersDao(): Flow<MutableList<UserModel.Params>> {
        return dao.getAllUsers().map { it1->
            val users: MutableList<UserModel.Params> = mutableListOf<UserModel.Params>()

            it1.forEach {
                var used = UserModel.Used.NONE

                when(it.used) {
                    0 -> { used = UserModel.Used.RED}
                    1 -> { used = UserModel.Used.YELLOW}
                    2 -> { used = UserModel.Used.GREEN}
                    3 -> { used = UserModel.Used.NONE}
                    4 -> { used = UserModel.Used.BLACK}
                }

                val user: UserModel.Params = UserModel.Params(
                    id = it.userId,
                    sex = it.sex,
                    lastSeen = it.lastSeen,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    used = used
                )
                users.add(user)
            }

            return@map users.toMutableList()
        }
    }

    override suspend fun updateOneDao(userParams: UserModel.Params) {
        var used = 3

        used = when(userParams.used) {
            UserModel.Used.RED -> {
                0
            }
            UserModel.Used.YELLOW -> {
                1
            }
            UserModel.Used.GREEN -> {
                2
            }
            UserModel.Used.NONE -> {
                3
            }
            UserModel.Used.BLACK -> {
                4
            }
        }

        val user = UserEntity(
            id = 0,
            userId = userParams.id,
            sex = userParams.sex,
            lastSeen = userParams.lastSeen,
            firstName = userParams.firstName,
            lastName = userParams.lastName,
            used = used
        )
        dao.insertOne(user)
    }

    override suspend fun deleteOneDao(userId: Int) {
        dao.deleteByUserId(userId)
    }

}