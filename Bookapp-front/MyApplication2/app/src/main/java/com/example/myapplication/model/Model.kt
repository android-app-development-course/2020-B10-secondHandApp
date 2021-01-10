package com.example.myapplication.model

import java.io.Serializable

data class Goods(val goodsid:String,val title:String,val price:String,val image:String):Serializable
data class ChatMessage(val content:String,val time:String,val isMeSend:Int,val isRead:Int):Serializable
//消息中心的模型
data class Msg(val icon:String, val name:String, var lastMsg:String, val time: String)