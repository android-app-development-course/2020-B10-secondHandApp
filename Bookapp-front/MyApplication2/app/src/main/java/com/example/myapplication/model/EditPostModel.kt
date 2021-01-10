package com.example.myapplication.model

import com.example.myapplication.api.ApiService
import com.example.myapplication.net.ServiceCreator
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import java.util.*

class EditPostModel {
    public fun uploadPost(partMap:Map<String, RequestBody>, file:List<MultipartBody.Part>): Call<ResponseBody> {
        return ServiceCreator.create(ApiService::class.java).postPublishRequest(partMap ,file)
    }
}