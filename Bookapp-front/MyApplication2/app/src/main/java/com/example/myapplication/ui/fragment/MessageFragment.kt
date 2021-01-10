package com.example.myapplication.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewModel.MsgViewModel
import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.LoginModel
import com.example.myapplication.model.Msg
import com.example.myapplication.ui.activity.ChatActivity
import com.example.myapplication.ui.activity.LoginActivity
import com.example.myapplication.ui.adapter.MsgAdapter


class MessageFragment:Fragment() {
    inner class ChatMessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("message")
            //val toName=intent.getStringExtra("toName")
            val fromName=intent.getStringExtra("fromName")
            println("msgfgmonrecive")
            for (item in msgViewModel._msglist.value!!){
                if (item.name==fromName){
                    item.lastMsg= message.toString()
                    return
                }

            }
            val msg=Msg("",fromName!!,message!!,"time")
            msgViewModel._msglist.value!!.add(msg)
            adapter.update(msgViewModel._msglist.value!!)
            }

        }
    private fun doRegisterReceiver() {
        chatMessageReceiver = ChatMessageReceiver()
        val filter =
            IntentFilter("com.xch.servicecallback.content")
        context?.registerReceiver(chatMessageReceiver, filter)
    }
    var rootView: View? =null


    lateinit var msgViewModel: MsgViewModel
    lateinit var recyclerView : RecyclerView
    lateinit var adapter:MsgAdapter
    private var chatMessageReceiver: ChatMessageReceiver? = null
    companion object{
        var messageFragment: MessageFragment? = null
        fun getNewInstance(): MessageFragment {
            if (messageFragment == null) {
                messageFragment =
                    MessageFragment()
            }
            return messageFragment!!
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_message,container,false)
        }
        val parent: ViewGroup? = rootView?.parent as ViewGroup?
        parent?.removeView(rootView)


        return rootView
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
   //建立消息数据列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
          //隐藏顶部状态栏

        val prefs= context?.getSharedPreferences("ui.activity.LoginActivity", Context.MODE_PRIVATE)
        val token= prefs?.getString("token","")
        if (token==""||token==null){
            val intent=Intent(context,LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
        setupViewModel()

        recyclerView= view!!.findViewById(R.id.recyclerView)
        //initMsg()   //初始化
        recyclerView.layoutManager = LinearLayoutManager(context)  //布局为线性垂直
        adapter = MsgAdapter(msgViewModel.msglist.value?:ArrayList())   //建立适配器实例
        recyclerView.adapter = adapter  //传入适配器
        doRegisterReceiver()
    }

    private fun setupViewModel(){
        msgViewModel= ViewModelProviders.of(this).get(MsgViewModel::class.java)
        msgViewModel.msglist.observe(this, Observer<MutableList<Msg>> { adapter.update(it) })
    }
//    override fun onResume() {
//        super.onResume()
//        msgViewModel.getData()
//    }
//    fun initMsg(){
//        msgList.add(
//            Msg(
//                R.drawable.head1,
//                "蔡师傅工作室2号店",
//                "支持面交，可以线下交易"
//            )
//        )
//        msgList.add(
//            Msg(
//                R.drawable.head2,
//                "耗子尾汁",
//                "不讲武德"
//            )
//        )
//    }
}
