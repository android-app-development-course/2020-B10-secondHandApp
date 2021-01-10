package com.example.myapplication

import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.ChatModel
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.URI

class websockettest {
companion object {
    @JvmStatic
    fun main(args: Array<String>) {
        val client=JWebSocketClient(URI("ws://echo.websocket.org"))//ws://localhost:8888
        Thread{
                try {
                    client.connectBlocking()
                    client.send("onlinerose")
//                    val model=ChatModel()
//                    var message=HashMap<String,String>()
//                    message["fromName"] = "rose"
//                    message["toName"]="jack"
//                    message["msgContent"]="hello"
//                    model.sendMsg(message).enqueue(object : Callback<ResponseBody> {
//                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                            println("onfailure")
//                        }
//
//                        override fun onResponse(
//                            call: Call<ResponseBody>,
//                            response: Response<ResponseBody>
//                        ) {
//                            println("onresponse")
//                        }
//
//                    })
                }catch (e:Exception){
                    e.printStackTrace()
                }

        }.start()
    }
}
}