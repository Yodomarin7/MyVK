package com.example.domain.usecase

import com.example.domain.models.GroupModel
import com.example.domain.models.UserModel
import com.example.domain.repository.IGroups
import com.example.domain.repository.IUsers
import kotlinx.coroutines.flow.*

//UseCase
class GetUsersUseCase(private val repository: IUsers) {

    suspend fun run(grouId: String,
                    _offset: Int,
                    user: UserModel.Params,
                    showRed: Boolean,
                    showYellow: Boolean,
                    showGreen: Boolean,
                    showBlack: Boolean): UserModel.ResponsParams {

        val items: MutableList<UserModel.Params> = arrayListOf()
        val daoUsers: List<UserModel.Params> = repository.getUsersDao().first()


        var offset = _offset
        var vkUsers: UserModel.ResponsParams? = null

        while (items.size < 21) {
            vkUsers = repository.getUsersVK(grouId, offset, 999, user)
            offset += 999

            when(vkUsers) {
                is UserModel.ResponsParams.Failed -> {
                    println("ResponsParams.Failed")
                    break
                }
                is UserModel.ResponsParams.Success -> {
//                    if(vkUsers.params.size == 0) {
//                        println("vkUsers.params.size == 0")
//                        break
//                    }

                    run lit@ {
                        vkUsers.params.forEach { vk ->
                            for(dao in daoUsers) {
                                if(dao.id == vk.id) vk.used = dao.used
                            }

                            if(!showRed && vk.used == UserModel.Used.RED) {  }
                            else if(!showYellow && vk.used == UserModel.Used.YELLOW) {  }
                            else if(!showGreen && vk.used == UserModel.Used.GREEN) {  }
                            else if(!showBlack && vk.used == UserModel.Used.BLACK) {  }
                            else {
                                items.add(vk)
                                if(items.size >= 170) { offset += 170
                                    return@lit }
                            }
                        }

                    }
                }
            }
        }

        return if(items.size < 1) vkUsers!!
        else UserModel.ResponsParams.Success(items, offset)
    }
}