package com.example.data.sources.network

import android.util.Log
import com.example.domain.models.UserModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.groups.GroupsService
import com.vk.sdk.api.groups.dto.GroupsGetMembersFieldsResponse
import com.vk.sdk.api.groups.dto.GroupsGetMembersSort
import com.vk.sdk.api.groups.dto.GroupsUserXtrRole
import com.vk.sdk.api.users.dto.UsersFields
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserVK {

    suspend fun getUsersVK(
        grouId: String,
        offset: Int,
        count: Int,
        user: UserModel.Params): UserModel.ResponsParams {

        return suspendCoroutine { it ->
            val fields: List<UsersFields>? = listOf(
                UsersFields.CAN_WRITE_PRIVATE_MESSAGE, UsersFields.SEX,
                UsersFields.LAST_SEEN, UsersFields.BDATE, UsersFields.FIRST_NAME_ABL, UsersFields.LAST_NAME_ABL)

            VK.execute(
                GroupsService().groupsGetMembers(grouId,
                    GroupsGetMembersSort.ID_DESC, offset, count, fields = fields),

                object: VKApiCallback<GroupsGetMembersFieldsResponse> {
                    override fun success(result: GroupsGetMembersFieldsResponse) {
                        val itms: List<GroupsUserXtrRole> = result.items
                        val myItms: MutableList<UserModel.Params> = arrayListOf()

                        val unixTime = (System.currentTimeMillis() / 1000L).toInt()

                        itms.forEach {

                            val sex = it.sex?.value ?: 0
                            val lastSeen = it.lastSeen?.time ?: 0
                            val tme: Int = (unixTime - lastSeen!!)/60/60/24
                            val canWriteMsg = it.canWritePrivateMessage?.value ?: 0

                            if((user.sex == 0 || user.sex == sex) && tme < user.lastSeen && canWriteMsg == 1) {
                                //items.add(MyItem(it.lastNameAbl, it.firstNameAbl, tme, it.id.value.toString()))
                                myItms.add(UserModel.Params(
                                    id = it.id.value.toInt(),
                                    sex = it.sex?.value ?: 0,
                                    lastSeen = tme,
                                    firstName = it.firstName ?: "NOT",
                                    lastName = it.lastName ?: "NOT"
                                ))
                            }
                        }

                        it.resume(UserModel.ResponsParams.Success(myItms))
                    }

                    override fun fail(error: Exception) {
                        //Log.e("NEBUR", "groupsGetMembers: ERR, " + error.message);
                        var errStr = error.message
                        if(errStr == null) errStr = "Ошибка"
                        it.resume(UserModel.ResponsParams.Failed(0, errStr))
                    }
                }
            )
        }
    }
}