package com.example.gymfix.data

import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

data class LoginReq(val email: String, val password: String)
data class LoginRes(val ok: Boolean, val name: String?, val error: String?)

interface ApiService {
    @POST("login.php") //hace llamado a la funcionalidad de el login.php
    suspend fun login(@Body body: LoginReq): Response<LoginRes>
}

object ApiClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/BDAPI/")   // esta es mi url, si quieres que funcione debe tener una ruta como C:\xampp\htdocs\BDAPI y ahi dentro, el backend osea los login.php etc
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
