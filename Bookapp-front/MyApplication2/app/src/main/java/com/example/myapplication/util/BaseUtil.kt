package com.example.myapplication.util

import android.content.Context
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.example.myapplication.R

import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.InputStream


object BaseUtil {
    public fun parseJSONWithJSONObject(jsonData:String):JSONArray{
        return JSONArray(jsonData);
    }
    private fun charToByte(c: Char): Byte {

        return "0123456789ABCDEF".indexOf(c).toByte()
    }
    fun hexStringToBytes(hexString: String?): ByteArray? {
        var hexString = hexString
        if (hexString == null || hexString == "") {
            return null
        }
        hexString = hexString.toUpperCase()
        val length = hexString.length / 2
        val hexChars = hexString.toCharArray()
        val d = ByteArray(length)
        for (i in 0..length - 1) {
            val pos = i * 2
            d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
        }
        return d
    }

//    private fun stringToSpinnearBean(sortString: String):ArrayList<SpinnearBean?>?{
//        if (sortString == "") {
//            return null
//        }
//        val allData = JSONObject(sortString) //全部内容变为一个项
//        val jsonArr: JSONArray =
//            allData.getJSONArray(LISTROOTNODE) //取出数组
//        for (x in 0 until jsonArr.length()) {
//            var model: SpinnearBean? = SpinnearBean()
//            val jsonobj: JSONObject = jsonArr.getJSONObject(x)
//            model!!.paraName = jsonobj.getString(KEY_LISTITEM_NAME)
//            model.paraValue = jsonobj.getString(KEY_LISTITEM_VALUE)
//            if (jsonobj.has(KEY_LISTITEM_CHECKCOLOR)) {
//                model.checkColor = jsonobj.getString(KEY_LISTITEM_CHECKCOLOR)
//            }
//            model.isSelectedState = false
//            itemsList.add(model)
//            model = null
//        }
//        return itemsList
//    }
//    @Throws(Exception::class)

//    private fun parseJsonArray(fileName: String): ArrayList<SpinnearBean?>? {
//        val itemsList = ArrayList<SpinnearBean?>()
//        val jsonStr: String = getStringFromAssert(this, fileName)
//        if (jsonStr == "") {
//            return null
//        }
//        val allData = JSONObject(jsonStr) //全部内容变为一个项
//        val jsonArr: JSONArray =
//            allData.getJSONArray(LISTROOTNODE) //取出数组
//        for (x in 0 until jsonArr.length()) {
//            var model: SpinnearBean? = SpinnearBean()
//            val jsonobj: JSONObject = jsonArr.getJSONObject(x)
//            model!!.paraName = jsonobj.getString(KEY_LISTITEM_NAME)
//            model.paraValue = jsonobj.getString(KEY_LISTITEM_VALUE)
//            if (jsonobj.has(KEY_LISTITEM_CHECKCOLOR)) {
//                model.checkColor = jsonobj.getString(KEY_LISTITEM_CHECKCOLOR)
//            }
//            model.isSelectedState = false
//            itemsList.add(model)
//            model = null
//        }
//        return itemsList
//    }

//    fun getStringFromAssert( filePath: String): String {
//        var content = "" // 结果字符串
//        try {
//            val fileContent=this::class.java.getResource("assets").file
//            val `is`: InputStream =  // 打开文件
//            var ch = 0
//            val out = ByteArrayOutputStream() // 实现了一个输出流
//            while (`is`.read().also({ ch = it }) != -1) {
//                out.write(ch) // 将指定的字节写入此 byte 数组输出流
//            }
//            val buff: ByteArray = out.toByteArray() // 以 byte 数组的形式返回此输出流的当前内容
//            out.close() // 关闭流
//            `is`.close() // 关闭流
//            content = String(buff, charset("utf-8")) // 设置字符串编码
//        } catch (e: java.lang.Exception) {
//            Toast.makeText(mContext, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT)
//                .show()
//        }
//        return content
//    }


}