package com.example.myapplication

import com.example.myapplication.net.HttpUtil
import okhttp3.*
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        thread{
            try {
                print("start")
                val client= OkHttpClient()
                /*val requestBody= FormBody.Builder()
                    //.add("publisherid","1235")
                    //.add("price","23.5")
                    .build()*/
                val request= Request.Builder()
                    .url("https://www.baidu.com")
                    //.post(requestBody)
                    .build()
                val response=client.newCall(request).execute()
                val responseData=response.body?.string()
                //if (responseData!=null){
                    print("$responseData  123 ")
               // }
            }catch (e:Exception){
                e.printStackTrace()
            }


        }

    }

}