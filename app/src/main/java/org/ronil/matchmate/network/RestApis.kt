package org.ronil.matchmate.network


import org.ronil.matchmate.models.UsersModel
import retrofit2.Response
import retrofit2.http.*


interface RestApis {
    @GET("/api")
    suspend fun getAllUsers(@Query("results") count: Int = 10): Response<UsersModel>
}