package com.example.domain.usecase

import com.example.domain.models.UserModel
import com.example.domain.repository.IUsers
import kotlinx.coroutines.flow.*

//UseCase
class GetUsersUseCase(private val repository: IUsers) {

    suspend fun run(grouId: String,
                    user: UserModel.Params,
                    showRed: Boolean,
                    showYellow: Boolean,
                    showGreen: Boolean): UserModel.ResponsParams {

        val items: MutableList<UserModel.Params> = arrayListOf()
        val daoUsers: List<UserModel.Params> = repository.getUsersDao().first()


        var offset = 0
        var vkUsers = repository.getUsersVK(grouId, offset, 50, user)

        while (items.size < 100) {

            when(vkUsers) {
                is UserModel.ResponsParams.Failed -> {
                    break
                }
                is UserModel.ResponsParams.Success -> {
                    if(vkUsers.params.size == 0) break

                    vkUsers.params.forEach { vk ->
                        for(dao in daoUsers) {
                            if(dao.id == vk.id) vk.used = dao.used
                        }

                        if(!showRed && vk.used == UserModel.Used.RED) { }
                        else if(!showYellow && vk.used == UserModel.Used.YELLOW) { }
                        else if(!showGreen && vk.used == UserModel.Used.GREEN) { }
                        else { items.add(vk) }

                        //if(showRed || vk.used != UserModel.Used.RED) items.add(vk)
                    }
                }
            }
            offset += 100
            vkUsers = repository.getUsersVK(grouId, offset, 100, user)
        }

        return if(items.size < 1) vkUsers
        else UserModel.ResponsParams.Success(items)
    }
}