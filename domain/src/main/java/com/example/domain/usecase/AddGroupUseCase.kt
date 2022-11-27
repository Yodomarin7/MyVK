package com.example.domain.usecase

import com.example.domain.models.GroupModel
import com.example.domain.repository.ICommon
import com.example.domain.repository.IGroups

class AddGroupUseCase(
    private val groupsRepository: IGroups,
    private val commonRepository: ICommon
) {

    /*
    добавить новую группу в локальную хранилище(бд)

     */
    suspend fun run(groupParams: GroupModel.Params): GroupModel.ResponsParams {
        val token = commonRepository.getVkAuthToken()
        val result: GroupModel.ResponsParams = groupsRepository.getByIdRetrofit(groupParams.screenName, token)

        when(result) {
            is GroupModel.ResponsParams.Success -> {
                groupsRepository.addOneDao(
                    GroupModel.Params(
                        id = result.params.id,
                        screenName = result.params.screenName,
                        name = result.params.name,
                        description = groupParams.description
                    ))
            }
            is GroupModel.ResponsParams.Failed -> {

            }
        }

        return result
    }
}