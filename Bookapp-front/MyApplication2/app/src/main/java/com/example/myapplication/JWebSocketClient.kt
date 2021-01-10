package com.example.myapplication

import android.util.Log
import com.alibaba.fastjson.JSON
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


open class JWebSocketClient(serverUri: URI?) :
    WebSocketClient(serverUri, Draft_6455()) {
    override fun onOpen(handshakedata: ServerHandshake) {
//        Log.e("JWebSocketClient", "onOpen()")
        println("onopen()")
    }

    override fun onMessage(message: String) {
//        Log.e("JWebSocketClient", "onMessage()")
        println("onmessage()")
        println(message)
        //val jsonMsg= JSON.parse(message)

    }

    override fun onClose(
        code: Int,
        reason: String,
        remote: Boolean
    ) {
//        Log.e("JWebSocketClient", "onClose()")
        println("onclose()")
    }

    override fun onError(ex: Exception) {
//        Log.e("JWebSocketClient", "onError()")
        println("onerror()")
        ex.printStackTrace()
    }
}