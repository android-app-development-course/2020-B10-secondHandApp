package com.example.myapplication.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSONArray
import com.example.myapplication.Goods
import com.example.myapplication.MyApplication
import com.example.myapplication.ui.adapter.GoodsAdapter
import com.example.myapplication.R
import com.example.myapplication.model.ResearchResultModel
import kotlinx.android.synthetic.main.search_result.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import com.example.myapplication.util.BaseUtil.hexStringToBytes
///hihih
class SearchResultActivity: AppCompatActivity() {
    private val goodsList = ArrayList<Goods>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_result)
        val words=intent.getStringExtra("words")

        //initGoods() // 初始化数据

        val researchResultModel= ResearchResultModel()
        if (words != null) {
            researchResultModel.getResearchResult(words).enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@SearchResultActivity, "搜索失败请稍后重试", Toast.LENGTH_SHORT).show()
                }
                @SuppressLint("SdCardPath")
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
                            for(i in 0 until jsonArray.size){
                                val jsonObject = jsonArray.getJSONObject(i)
                                val primaryImage=hexStringToBytes(jsonObject.getString("primaryImage"))
                                val imagetype=jsonObject.getString("imagetype")
                                val goodsid=jsonObject.getString("goodsid")
                                val title=jsonObject.getString("title")
                                val price=jsonObject.getString("price")
                                var imagepath=
                                    MyApplication.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
                                imagepath= "$imagepath$goodsid.$imagetype"
                                val file=
                                    File(imagepath);
                                if(!file.exists()){
                                    try {
                                        file.createNewFile()
                                        val bufferedInputStream= ByteArrayInputStream(primaryImage)
                                        val fileOutputStream = FileOutputStream(file);
                                        var i:Int=bufferedInputStream.read()
                                        while (i!=-1){
                                            fileOutputStream.write(i)
                                            i=bufferedInputStream.read()
                                        }
                                        fileOutputStream.flush()
                                        bufferedInputStream.close()
                                        fileOutputStream.close()
                                    }catch (e:Exception){
                                        e.printStackTrace()
                                    }
                                }


                                val goods= Goods(
                                    goodsid,
                                    title,
                                    imagepath,
                                    price
                                )
                                goodsList.add(goods)
                            }
                        }
                    }
                    val adapter =
                        GoodsAdapter(
                            this@SearchResultActivity,
                            R.layout.goods_item,
                            goodsList
                        )
                    listView.adapter = adapter
                }

            })
        }

    }

//    private fun initGoods() {
//        repeat(3) {
//            goodsList.add(Goods("学而思秘籍", R.drawable.xueersi,23))
//            goodsList.add(Goods("中学教材全解", R.drawable.jiaocaiquanjie,45))
//            goodsList.add(Goods("高考必刷题", R.drawable.bishuati,56))
//            goodsList.add(Goods("五年中考三年模拟", R.drawable.zhongkao,60))
//        }
//    }

}