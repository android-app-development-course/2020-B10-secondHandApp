package com.example.myapplication.viewModel

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSONArray
import com.example.myapplication.MyApplication.Companion.context
import com.example.myapplication.model.Goods
import com.example.myapplication.R
import com.example.myapplication.model.ScreenSearchModel
import com.example.myapplication.util.BaseUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

class IndexViewModel :  ViewModel(){
    private var city=""
    var university=""
    var keywords=""
    val num=30
    val index=0
    var sort=""

     var _goodslist=MutableLiveData<MutableList<Goods>>().apply {value= ArrayList()

    }
    val goodslist:LiveData<MutableList<Goods>> = _goodslist

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList




    fun getData(){
        val model= ScreenSearchModel()
        var map=HashMap<String,String>()
        map["city"]=city
        map["university"]=university
        map["words"]=keywords
        map["num"]=num.toString()
        map["index"]=index.toString()
        map["sort"]=sort

        model.getScreenSearchResult(map).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "加载失败了诶～", Toast.LENGTH_SHORT).show()
                _isViewLoading.value=false
                _onMessageError.value="faliure"
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                
                val jsonData=response.body()?.string()
                println("this is$jsonData")
                if (jsonData!=null){
                    var jsonArray: JSONArray? =null
                    try {
                        jsonArray= JSONArray.parse(jsonData) as JSONArray?
                    }catch(e: Exception){
                        e.printStackTrace()
                    }
                    if (jsonArray != null) {
                        var list= ArrayList<Goods>()
                        for(i in 0 until jsonArray.size){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val primaryImage=
                                BaseUtil.hexStringToBytes(jsonObject.getString("primaryImage"))
                            val imagetype=jsonObject.getString("imagetype")
                            val goodsid=jsonObject.getString("goodsid")
                            val title=jsonObject.getString("title")
                            val price=jsonObject.getString("price")
                            //var imagepath=context.getString(R.string.search_result_image_path)
                            var imagepath= context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
                            var path=File(imagepath)
                            if(!path.exists()){
                                if (path.mkdirs()){
                                    println("successssssssssssssssss")
                                }else{
                                    println("failllllllllllllll")
                                }




                            }
                            imagepath= "$imagepath$goodsid.$imagetype"
                            val file=
                                File(imagepath)
                            if(!file.exists()){
                                try {
                                    if (file.createNewFile()){
                                        //Toast.makeText(context, "createsuccess", Toast.LENGTH_SHORT).show()
                                    }else{
                                        //Toast.makeText(context, "createfail", Toast.LENGTH_SHORT).show()
                                    }

                                    val bufferedInputStream= ByteArrayInputStream(primaryImage)
                                    val fileOutputStream = FileOutputStream(file)
                                    var i:Int=bufferedInputStream.read()
                                    while (i!=-1){
                                        fileOutputStream.write(i)
                                        i=bufferedInputStream.read()
                                    }
                                    fileOutputStream.flush()
                                    bufferedInputStream.close()
                                    fileOutputStream.close()
                                }catch (e: Exception){
                                    e.printStackTrace()
                                    //Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                            println(imagepath)
                            val goods= Goods(goodsid,title,price,imagepath)
                            list.add(goods)

                        }
                        _goodslist.value=(list)
                        _goodslist.postValue(_goodslist.value)
                        Log.e("TAG", "onResponse: "+list.toString() )
                    }
                }
            }

        })
    }
}