package com.example.myapplication.net

import android.util.Log
import com.google.gson.JsonObject
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

object HttpUtil {
    fun sendOkHttpRequest(address:String, callback: okhttp3.Callback){
        val client=OkHttpClient()
        val requestBody=FormBody.Builder()
            .add("publisherid","1235")
            .add("price","23.5")
            .build()
        val request= Request.Builder()
            .url("http://127.0.0.1:8080/app_war_exploded/$address")
            .post(requestBody)
            .build()
        print("http://127.0.0.1:8080/app_war_exploded/$address")
        client.newCall(request).enqueue(callback)
    }
//    fun  GetHttpEntity( response:Response) : JSONObject {
//
//        var line:String;
//         var result: JsonObject ;
//         var entityStringBuilder=StringBuilder();
//        try {
//            var b =  BufferedReader( InputStreamReader(response.body., "UTF-8"),8*1024);
//            while ((line=b.readLine())!=null) {
//                entityStringBuilder.append(line+"/n");
//            }
}