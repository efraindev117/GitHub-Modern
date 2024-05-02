package com.android.network.webservice

import com.android.network.model.user.NetworkUsersModelItem
import com.android.network.utils.Constants.USER_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService {

    @GET(USER_PATH)
    suspend fun getGitHubUsers(): Response<List<NetworkUsersModelItem>>

    @POST("login")
    suspend fun login():Response<String>
    /*
     @GET(CHARACTER_PATH)
    suspend fun getGitHubUsers_perPage(
        @Query(ORDER_BY) orderBy: String = ORDER_BY_VALUE
    ): Response<CharacterMarvelResponse>
     */
}
