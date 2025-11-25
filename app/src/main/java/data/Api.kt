package com.example.gymfix.data

import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.Call

data class LoginReq(val name: String, val passwordd: String)
data class LoginRes(val status: String, val tipo: String?, val message: String?, val data: UserData?)
data class UserData(val id: String, val name: String, val tipo_usuario: String)
data class FormClientResponse(val status: String, val message: String)

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("name") name: String,
        @Field("passwordd") password: String
    ): Response<LoginRes>

    @FormUrlEncoded
    @POST("formclient.php")
    fun updateForm(
        @Field("id") id: String,
        @Field("genero") genero: String,
        @Field("edad") edad: String,
        @Field("peso") peso: String,
        @Field("altura") altura: String,
        @Field("objetivo") objetivo: String
    ): Call<FormClientResponse>
}

object ApiClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/smartFix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
