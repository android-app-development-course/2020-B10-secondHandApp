package com.example.myapplication.ui.mine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.shouye.IndexFragment
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.mine.*

class MineFragment:Fragment() {

    var rootView: View? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object{
        var mineFragment: MineFragment? = null
        fun getNewInstance(): MineFragment {
            if (mineFragment == null) {
                mineFragment = MineFragment()
            }
            return MineFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView==null){
            rootView=inflater.inflate(R.layout.mine,container,false)
        }
        val parent: ViewGroup? = rootView?.parent as ViewGroup?
        if(parent!=null){
            parent.removeView(rootView)
        }
        return rootView
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val prefs= context?.getSharedPreferences("ui.activity.LoginActivity", Context.MODE_PRIVATE)
        val logname= prefs?.getString("logname","")
        user_name.text=logname
        logout.setOnClickListener() {
            val ed = prefs?.edit()
            if(ed!=null){
                ed.remove("logname")
                ed.remove("token")
                ed.apply()
                user_name.text="未登录请登录"
                Toast.makeText(context, "注销成功", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "注销失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}