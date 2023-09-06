package com.example.mathsolver.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface WolframAlphaService {
    @GET("result")
    @Headers("Content-type:text/plain")
    suspend fun evaluateExpression(
        @Query("appid") appId: String,
        @Query("i") expression: String
    ): Response<String>
}
