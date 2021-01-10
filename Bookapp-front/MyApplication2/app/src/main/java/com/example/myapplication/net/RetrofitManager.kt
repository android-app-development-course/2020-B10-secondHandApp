package com.example.myapplication.net

import com.example.myapplication.api.ApiService
import com.example.myapplication.api.UrlConstant
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    /*val service:ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){}

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)  //自己配置
            .client(getOkHttpClient())
           // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           // .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        //val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        //httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        //val cacheFile = File(MyApplication.context.cacheDir, "cache")
        //val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
            //.addInterceptor(addQueryParameterInterceptor())  //参数添加
            //.addInterceptor(addHeaderInterceptor()) // token过滤
//              .addInterceptor(addCacheInterceptor())
           // .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
           // .cache(cache)  //添加缓存
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }*/
}