package com.example.myapplication.api


import com.example.myapplication.model.ChatMessage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("publishServlet")
    fun postPublishRequest(@PartMap partMap:@JvmSuppressWildcards Map<String,RequestBody>, @Part files:List<MultipartBody.Part>): Call<ResponseBody>

    @GET("SearchServlet")
    fun getSearchResult(@Query("words") words:String):Call<ResponseBody>

    @FormUrlEncoded
    @POST("registerServlet")
    fun postRegisterRequest(@FieldMap map:Map<String,String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("loginServlet")
    fun postLoginRequest(@Field("logname") logname:String,@Field("password") password:String):Call<ResponseBody>

    @FormUrlEncoded
    @POST("screenSearchServlet")
    fun getScreenSearchResult(@FieldMap map: Map<String, String>):Call<ResponseBody>

    @FormUrlEncoded
    @POST("SendMsg")
    fun sendMsg(@FieldMap msg: Map<String,String>):Call<ResponseBody>

    @GET("detailServlet")
    fun getDetail(@Query("goodsid") goodsid:String):Call<ResponseBody>
}