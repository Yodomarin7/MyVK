package com.example.data.sources.network

import com.example.data.AppRetrofit
import com.example.domain.models.GroupModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class GroupRetrofit {

    data class MyResponse(
        val error: MyError,
        val response: List<MySuccess>
    )

    data class MyError(
        val error_code: Int,
        val error_msg: String
    )

    data class MySuccess(
        val id: Int,
        val name: String,
        val screenName: String,
    )

    interface GroupApi {
        @GET("groups.getById?group_id=rynok_avto&access_token=vk1.a.FTIG7cVfG3_avoiSpCRovmLe5kQZZIxM4W8U38WLMzBlSwxz2gDEoyfnJoUqp3Hu1GJyAOfXDyXjkpvYVHM5Zr0HAKIT_bI7Hnm1tRYqpwBaoM9p14PlC3lXsZb9znQPTEDPCX8e8Uwleh6QuBCITYkZzfU4Za4BuvaWkfj8KD89VrntyQNMtAtyk4UVPeTDhVkdXY91bM7ffP687ENSdw&v=5.131")
        suspend fun getById1(): Response<MyResponse>

        @GET("groups.getById")
        suspend fun getById(
            @Query("group_id") groupId: String,
            @Query("access_token") token: String,
            @Query("v") version: String
        ): Response<MyResponse>
    }

    suspend fun getById(groupId: String, token: String): GroupModel.ResponsParams {
        try {
            val response = AppRetrofit.groupApi.getById(groupId, token, "5.131")
            if(response.isSuccessful) {

                return if (response.body()?.response?.get(0)?.name != null) {
                    GroupModel.ResponsParams.Success(
                        GroupModel.Params(
                            id = response.body()!!.response!![0]!!.id,
                            screenName = response.body()!!.response!![0]?.screenName ?: "",
                            name = response.body()!!.response!![0]!!.name,
                        )
                    )
                } else if (response.body()?.error?.error_code == 5) {
                    GroupModel.ResponsParams.Failed(0, response.body()!!.error!!.error_msg)
                } else if (response.body()?.error?.error_msg != null) {
                    GroupModel.ResponsParams.Failed(1, response.body()!!.error!!.error_msg)
                } else {
                    GroupModel.ResponsParams.Failed(1, "Error: " + response.message())
                }
            } else {
                return GroupModel.ResponsParams.Failed(1, response.message())
            }
        } catch (e: Exception) {
            return GroupModel.ResponsParams.Failed(1, "Exception: $e")
        }
    }

}