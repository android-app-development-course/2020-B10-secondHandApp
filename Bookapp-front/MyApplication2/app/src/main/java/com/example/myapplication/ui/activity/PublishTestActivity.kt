package com.example.myapplication.ui.activity


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.qqtheme.framework.picker.OptionPicker
import cn.qqtheme.framework.util.ConvertUtils
import cn.qqtheme.framework.widget.WheelView
import com.example.myapplication.ui.adapter.ImageAdapter
import com.example.myapplication.MainActivity
import com.example.myapplication.MyApplication
import com.example.myapplication.R

import com.example.myapplication.model.EditPostModel

import kotlinx.android.synthetic.main.publish_test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.beahugs.imagepicker.utils.ImageSelector
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PublishTestActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE=0x00000011
        private val PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012
    }

    private lateinit var rvImage: RecyclerView
    private lateinit var mAdapter: ImageAdapter
    private lateinit var images:ArrayList<String>
    //private lateinit var sortTx:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.publish_test)
        publishButton.setOnClickListener(){
            publish()
        }
        rvImage=findViewById(R.id.rv_image)
        rvImage.layoutManager = GridLayoutManager(this, 3)
        mAdapter = ImageAdapter(this)
        rvImage.adapter = mAdapter
        addImageButton.setOnClickListener(){
            ImageSelector.builder()
                .useCamera(false) // 使用拍照
                .setCrop(false)  // 使用图片剪切
                //.setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                .setSingle(false)  //设置是否单选
                .canPreview(true) //是否点击放大图片查看,，默认为true
                .setMaxSelectCount(9)//如果设置大于0
                .start(this,
                    REQUEST_CODE
                ); // 打开相册
        }
        sortTx.setOnClickListener(){
            onOptionPicker()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("TAG", "onActivityResult: " )
    Log.e("TAG", "resultCode: "+requestCode )
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
             images = (data.getStringArrayListExtra(ImageSelector.SELECT_RESULT) as ArrayList<String>)
            Log.e("TAG", images.toString())
            var isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE,false)
            Log.e("TAG", "hererererre ")
            mAdapter?.refresh(images)
        }


    }
    fun publish(){
        val editPostModel= EditPostModel()
        var  partMap = HashMap<String,RequestBody>()
        val prefs= this?.getSharedPreferences("ui.activity.LoginActivity", Context.MODE_PRIVATE)
        val logname= prefs?.getString("logname","")
        if (logname==""||logname==null){
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            return
        }
        partMap["publisherid"] = logname!!.toRequestBody()
        partMap["title"] = titleText.text.toString().toRequestBody()
        partMap["detail"] = detailText.text.toString().toRequestBody()
        partMap["price"] = priceText.text.toString().toRequestBody()
        var list=ArrayList<MultipartBody.Part>()
        var selectList=ArrayList<File>()
        for (f in images){
            selectList.add(File(f))
        }
        for (file in selectList){
            var requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
            var formData=MultipartBody.Part.createFormData("file",file.name,requestBody)
            list.add(formData);

        }
        editPostModel.uploadPost(partMap,list).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val list= response.body()?.string()
                val jsonObject=JSONObject(list)
                if (list!=null){
                    val status=jsonObject.getString("status")
                    if (status=="success"){
                        Toast.makeText(this@PublishTestActivity, "发布成功", Toast.LENGTH_SHORT).show()
//                        val intent=Intent(this@PublishTestActivity,
//                            MainActivity1::class.java)
//                        startActivity(intent)
                        finish()

                    }
                }
            }

        })

    }
    fun onOptionPicker() {
        val picker = OptionPicker(
            this, ConvertUtils.toString(assets.open("sort.txt")).split(",")
        )
        picker.setHeight(500)
        picker.setCanceledOnTouchOutside(false)
        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setShadowColor(Color.RED, 40)
        picker.selectedIndex = 1
        picker.setCycleDisable(true)
        picker.setTextSize(11)
        picker.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
            override fun onOptionPicked(index: Int, item: String) {
                //Toast.makeText(this@PublishTestActivity, "\"index=$index, item=$item\"", Toast.LENGTH_SHORT).show()
                sortTx.text=item
            }
        })
        picker.show()
    }


}