package com.example.myapplication.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Photo
import com.example.myapplication.PhotoAdapter
import com.example.myapplication.R
import com.example.myapplication.model.DetailModel
import com.example.myapplication.util.BaseUtil.hexStringToBytes
import kotlinx.android.synthetic.main.activity_good_detail.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URI
import java.util.*

import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_good_detail)
        val firstImage=intent.getStringExtra("firstImage")
        Log.e("TAG", "onCreate: $firstImage" )
        val goodsid=intent.getStringExtra("goodsid")
        Log.e("detailactivity", "onCreate: $goodsid")
        var publisherid=""
        val list=ArrayList<Photo>()
        list.add(Photo(firstImage))
        val model=DetailModel()
        connect_seller.setOnClickListener(){
            val intent= Intent(this,ChatActivity::class.java)
            intent.putExtra("name",publisherid)
            startActivity(intent)
        }
        model.getDetail(goodsid!!).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               val jsonData=response.body()?.string()
                Log.e("detail onresp", "onResponse: $jsonData" )
                val jsonObject=JSONObject(jsonData)
                publisherid=jsonObject.getString("publisherid")
                good_detail_user_name.text=publisherid
                //good_detail_time.text=jsonObject.getString("publishTime")
                good_detail_send_money.text="￥"+jsonObject.getString("price")
                good_title.text=jsonObject.getString("title")
                good_detail_content.text=jsonObject.getString("detail")
                val imagesstr=jsonObject.getString("images")
                val imageobj=JSONObject(imagesstr)
                for (key in imageobj.keys()){
                    var imagepath=getString(R.string.search_result_image_path)+key
                    val file= File(imagepath)
                    if(!file.exists()){
                        try {
                            file.createNewFile()
                            val bufferedInputStream= ByteArrayInputStream(hexStringToBytes(imageobj.getString(key)))
                            val fileOutputStream = FileOutputStream(file);
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
                        }
                    }
                    list.add(Photo(imagepath))

                }
                val adapter=PhotoAdapter(list);
                good_detail_recycle.layoutManager = LinearLayoutManager(this@DetailActivity)
                good_detail_recycle.adapter=adapter
            }

        })
//        list.add( Photo(Uri.parse("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2422437459,525040718&fm=26&gp=0.jpg")))
//        list.add( Photo(Uri.parse("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2422437459,525040718&fm=26&gp=0.jpg")))
//        val adapter=PhotoAdapter(list)
//        good_detail_recycle.layoutManager = LinearLayoutManager(this)
//        good_detail_recycle.adapter=adapter

    }
}