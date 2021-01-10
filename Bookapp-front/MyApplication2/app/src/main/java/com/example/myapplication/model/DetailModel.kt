package com.example.myapplication.model

import com.example.myapplication.api.ApiService
import com.example.myapplication.net.ServiceCreator
import okhttp3.ResponseBody
import retrofit2.Call

class DetailModel {
    fun getDetail(goodsid:String): Call<ResponseBody> {
        return ServiceCreator.create(ApiService::class.java).getDetail(goodsid)
    }
}