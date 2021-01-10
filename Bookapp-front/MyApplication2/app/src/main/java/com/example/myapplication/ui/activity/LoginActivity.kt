package com.example.myapplication.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.model.LoginModel
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        titleBack.setOnClickListener {
            finish()
        }
        loginButton.setOnClickListener(){
            val model=LoginModel()
            val logname=accountEdit.text.toString()
            val password=passwordEdit.text.toString()
            model.loginPost(logname,password).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "登录失败，请检查网络", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val list= response.body()?.string()
                    println(list)
                    val jsonObject=JSONObject(list)
                    if(list!=null){
                        val status=jsonObject.getString("status")
                        if(status=="success"){
                            //登录成功
                            val token=jsonObject.getString("token")
                            val prefs=getPreferences(Context.MODE_PRIVATE)
                            val editor=prefs.edit()
                            editor.putString("token",token)
                            editor.putString("logname", logname)
                            editor.apply()
//                            val intent= Intent(this@LoginActivity,
//                                MainActivity1::class.java)
//                            startActivity(intent)
                            Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "登录失败，请稍后再试", Toast.LENGTH_SHORT).show()
                    }
                }


            })
        }

        registerClickTextView.setOnClickListener {
            val intent = Intent(this,
                RegisterActivity::class.java)
            startActivity(intent)
        }
    }



}