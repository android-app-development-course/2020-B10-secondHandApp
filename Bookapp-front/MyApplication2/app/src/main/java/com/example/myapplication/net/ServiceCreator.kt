package com.example.myapplication.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {//127.0.0.1（测试类应改为这个） //10.0.2.2(虚拟机应改为这个）
    private const val BASE_URL="http://42.192.89.235:8080/app_war exploded/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)
}