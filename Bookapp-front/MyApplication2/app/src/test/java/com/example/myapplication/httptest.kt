package com.example.myapplication


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.model.RegisterModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class httptest {
//    private fun charToByte(c: Char): Byte {
//
//        return "0123456789ABCDEF".indexOf(c).toByte()
//    }
//    fun hexStringToBytes(hexString: String?): ByteArray? {
//        var hexString = hexString
//        if (hexString == null || hexString == "") {
//            return null
//        }
//        hexString = hexString.toUpperCase()
//        val length = hexString.length / 2
//        val hexChars = hexString.toCharArray()
//        val d = ByteArray(length)
//        for (i in 0..length - 1) {
//            val pos = i * 2
//            d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
//        }
//        return d
//    }

    companion object {
        //        private fun charToByte(c: Char): Byte {
//
//            return "0123456789ABCDEF".indexOf(c).toByte()
//        }
//        fun hexStringToBytes(hexString: String?): ByteArray? {
//            var hexString = hexString
//            if (hexString == null || hexString == "") {
//                return null
//            }
//            hexString = hexString.toUpperCase()
//            val length = hexString.length / 2
//            val hexChars = hexString.toCharArray()
//            val d = ByteArray(length)
//            for (i in 0..length - 1) {
//                val pos = i * 2
//                d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
//            }
//            return d
//        }

        @JvmStatic
        fun main(args: Array<String>) {
//            val editPostModel=EditPostModel()
//            var  partMap = HashMap<String,RequestBody>()
//            partMap.put("publisherid", "4".toRequestBody())
//
//            partMap.put("title","这是标题2".toRequestBody())
//            partMap.put("detail","这是详情2".toRequestBody())
//            partMap.put("price","23.5".toRequestBody())
//            var list=ArrayList<MultipartBody.Part>()
//            var selectList=ArrayList<File>()
//            selectList.add(File("/home/wu/图片/1.png"))
//            selectList.add(File("/home/wu/图片/mvp.png"))
//            for (file in selectList){
//                var requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
//                var formData=MultipartBody.Part.createFormData("file",file.name,requestBody)
//                list.add(formData);
//            }
//            editPostModel.uploadPost(partMap,list).enqueue(object :Callback<ResponseBody>{
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    t.printStackTrace()
//                }
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//                    val list= response.body()?.string()
//                    if (list!=null){
////                        parseJSONwithJSONObject(list)
//                        println(list)
//                    }
//                }
//
//            })

            /**
             * 搜索测试
             */
//            val ResearchResultModel =ResearchResultModel()
//            ResearchResultModel.getResearchResult("标题").enqueue(object :Callback<ResponseBody>{
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    println("查询失败")
//
//                }
//
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                    val jsonData=response.body()?.string()
//
//                    println(jsonData)
//                    if (jsonData!=null){
//                        var jsonArray: JSONArray? =null
//                        try {
//                            jsonArray= JSONArray.parse(jsonData) as JSONArray?
//                        }catch(e:Exception){
//                            e.printStackTrace()
//                        }
//
//                        if (jsonArray != null) {
//                            for(i in 0 until jsonArray.size){
//                                val jsonObject = jsonArray.getJSONObject(i)
//                                val primaryImage=hexStringToBytes(jsonObject.getString("primaryImage"));
//                                println(primaryImage)
////                                val title=jsonObject.getString("title")
////                                val price=jsonObject.getString("price")
//                                val file=File("/home/wu/AndroidStudioProjects/MyApplication2/app/src/images/1.png");
//                                if(!file.exists())
//                                    file.createNewFile()
//                                val bufferedInputStream=ByteArrayInputStream(primaryImage)
//                                val fileOutputStream =FileOutputStream(file);
//                                var i:Int=bufferedInputStream.read()
//                                while (i!=-1){
//                                    fileOutputStream.write(i)
//                                    i=bufferedInputStream.read()
//                                }
//                                fileOutputStream.flush()
//                                bufferedInputStream.close()
//                                fileOutputStream.close()
////                                println(title)
////                                println(price)
//                            }
//                        }
//                    }
//                }
//
//            })
//        }

            /**
             * 注册测试
             */
            val RegisterModel = RegisterModel()
            var map = HashMap<String, String>()
            map.put("logname", "myname")
            map.put("password", "mypassword")
            map.put("again_ password", "mypassword")
            map.put("city", "guangzhou")
            map.put("major", "cs")
            map.put("school", "scnu")
            RegisterModel.registerPost(map).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("registertest", "onFailure: 注册失败")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    print(response.body()?.string())
                }

            })

        }
    }
}


