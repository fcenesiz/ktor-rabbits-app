package com.fcenesiz.ktorrabbitsapp.data

import retrofit2.http.GET

interface RabbitsApi {

    @GET("/randomrabbit")
    suspend fun getRandomRabbit(): Rabbit

    companion object{
        const val BASE_URL = "http://172.20.240.1:8081"
    }
}