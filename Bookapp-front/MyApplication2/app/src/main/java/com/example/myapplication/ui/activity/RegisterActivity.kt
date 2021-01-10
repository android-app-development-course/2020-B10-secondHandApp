package com.example.myapplication.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.model.RegisterModel
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Callback
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        register_titleBack.setOnClickListener {
            finish()
        }
        register_cityInput.setOnClickListener {
            createDialog("城市")
        }
        register_collegeInput.setOnClickListener {
            createDialog("学校")
        }
    registerButton.setOnClickListener(){
        val registerModel=RegisterModel()
        val logname=register_usernameInput.text.toString()
        val password=register_passwdInput.text.toString()
        val again_password=register_passwdConfirmInput.text.toString()
        val city=register_cityInput.text.toString()
        val school=register_collegeInput.text.toString()
        val major=register_majorInput.text.toString()
        val map=HashMap<String,String>()
        map["logname"] = logname
        map["password"] = password
        map["again_password"] = again_password
        map["city"] = city
        map["school"]=school
        map["major"]=major

        registerModel.registerPost(map).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "注册失败，请重试", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val list=(response.body()?.string())
                 val jsonObject= JSONObject(list)
                if (list!=null){
                    val status=jsonObject.getString("status")
                    if (status=="success"){
                        val intent= Intent(this@RegisterActivity,
                            LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                    }
                    if(status=="failure"){
                        val errormessage=jsonObject.getString("errormessage")
                        Toast.makeText(this@RegisterActivity, errormessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }
    }

    private fun createDialog(s:String){
        val arrayCity = arrayOf("广东广州","广东深圳")
        val arrayCollege = arrayOf("华南师范大学")
        AlertDialog.Builder(this).apply {
            setTitle("请选择$s")
            if (s=="城市"){
            setItems(arrayCity, DialogInterface.OnClickListener { dialog, which ->
                register_cityInput.text = arrayCity[which]
                register_cityInput.typeface = Typeface.DEFAULT_BOLD
                 })
            }else if (s=="学校"){
                setItems(arrayCollege, DialogInterface.OnClickListener { dialog, which ->
                    register_collegeInput.text = arrayCollege[which]
                    register_collegeInput.typeface = Typeface.DEFAULT_BOLD
                })
            }else   {
                setMessage("加载出错")
            }
            show()
        }
    }


}