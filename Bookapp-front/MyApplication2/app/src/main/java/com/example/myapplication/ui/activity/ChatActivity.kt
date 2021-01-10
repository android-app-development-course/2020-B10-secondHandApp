package com.example.myapplication.ui.activity

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.JWebSocketClient
import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.ui.adapter.ChatAdapter
import com.example.myapplication.JWebSocketClientService
import com.example.myapplication.JWebSocketClientService.JWebSocketClientBinder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlin.collections.ArrayList

class ChatActivity :AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null
    private var client: JWebSocketClient? = null
    private var binder: JWebSocketClientBinder? = null
    private var jWebSClientService: JWebSocketClientService? = null
    private var et_content: EditText? = null
    private var listView: ListView? = null
    private var btn_send: Button? = null
    private var chatMessageList: ArrayList<ChatMessage> = ArrayList() //消息列表
    private var name:String?=null
    private var adapter_chatMessage: ChatAdapter? = null
    private var chatMessageReceiver: ChatMessageReceiver? = null

    private val serviceConnection: ServiceConnection =
        object : ServiceConnection {
            override fun onServiceConnected(
                componentName: ComponentName,
                iBinder: IBinder
            ) {
                Log.e("MainActivity", "服务与活动成功绑定")
                binder = iBinder as JWebSocketClientBinder
                jWebSClientService = binder!!.service
                client = jWebSClientService!!.client
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                Log.e("MainActivity", "服务与活动成功断开")
            }
        }

    inner class ChatMessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("message")
            //val toName=intent.getStringExtra("toName")
            val fromName=intent.getStringExtra("fromName")
            if (fromName==name){
                val time=System.currentTimeMillis().toString() + ""
                val chatMessage = message?.let { ChatMessage(it,time,0,1) }

                if (chatMessage != null) {
                    chatMessageList.add(chatMessage)
                }
                initChatMsgListView()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_chat)
        mContext = this
        name=intent.getStringExtra("name")
        tv_groupOrContactName.text=name
        //启动服务
        startJWebSClientService()
        //绑定服务
        bindService()
        //注册广播
        doRegisterReceiver()
        //检测通知是否开启
        checkNotification(mContext)
        findViewById()
        initView()
    }

    /**
     * 绑定服务
     */
    private fun bindService() {
        val bindIntent = Intent(
            mContext,
            JWebSocketClientService::class.java
        )
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE)
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private fun startJWebSClientService() {
        val intent =
            Intent(mContext, JWebSocketClientService::class.java)
        startService(intent)
    }

    /**
     * 动态注册广播
     */
    private fun doRegisterReceiver() {
        chatMessageReceiver = ChatMessageReceiver()
        val filter =
            IntentFilter("com.xch.servicecallback.content")
        registerReceiver(chatMessageReceiver, filter)
    }


    private fun findViewById() {
        listView = findViewById(R.id.chatmsg_listView)
        btn_send = findViewById(R.id.btn_send)
        et_content = findViewById(R.id.et_content)
        btn_send!!.setOnClickListener(this)
    }

    private fun initView() {
        //监听输入框的变化
        et_content!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (et_content!!.text.toString().length > 0) {
                    btn_send!!.visibility = View.VISIBLE
                } else {
                    btn_send!!.visibility = View.GONE
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_send -> {
                val content = et_content!!.text.toString()
                if (content.length <= 0) {
                    Toast.makeText(mContext, "消息不能为空", Toast.LENGTH_SHORT).show()
                    return
                }
                if (client != null && client!!.isOpen) {
                    jWebSClientService!!.sendMsg(name!!,content)

                    //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
                    val chatMessage = ChatMessage(content,System.currentTimeMillis().toString() + "",1,1)
                    chatMessageList.add(chatMessage)
                    initChatMsgListView()
                    et_content!!.setText("")
                } else {
                    Toast.makeText(mContext, "连接已断开，请稍后再试", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }
    }

    private fun initChatMsgListView() {
        adapter_chatMessage = mContext?.let { ChatAdapter(it, chatMessageList) }
        listView!!.adapter = adapter_chatMessage
        listView!!.setSelection(chatMessageList.size)
    }


    /**
     * 检测是否开启通知
     *
     * @param context
     */
    private fun checkNotification(context: Context?) {
        if (!isNotificationEnabled(context)) {
            AlertDialog.Builder(context).setTitle("温馨提示")
                .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, which -> setNotification(context) })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, which -> }).show()
        }
    }

    /**
     * 如果没有开启通知，跳转至设置界面
     *
     * @param context
     */
    private fun setNotification(context: Context?) {
        val localIntent = Intent()
        //直接跳转到应用通知设置的代码：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            localIntent.putExtra("app_package", context!!.packageName)
            localIntent.putExtra("app_uid", context.applicationInfo.uid)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            localIntent.addCategory(Intent.CATEGORY_DEFAULT)
            localIntent.data = Uri.parse("package:" + context!!.packageName)
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts(
                    "package",
                    context!!.packageName,
                    null
                )
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW
                localIntent.setClassName(
                    "com.android.settings",
                    "com.android.setting.InstalledAppDetails"
                )
                localIntent.putExtra(
                    "com.android.settings.ApplicationPkgName",
                    context!!.packageName
                )
            }
        }
        context!!.startActivity(localIntent)
    }

    /**
     * 获取通知权限,监测是否开启了系统通知
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun isNotificationEnabled(context: Context?): Boolean {
        val CHECK_OP_NO_THROW = "checkOpNoThrow"
        val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
        val mAppOps =
            context!!.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val appInfo = context.applicationInfo
        val pkg = context.applicationContext.packageName
        val uid = appInfo.uid
        var appOpsClass: Class<*>? = null
        try {
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass.getMethod(
                CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                String::class.java
            )
            val opPostNotificationValue =
                appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
            val value = opPostNotificationValue[Int::class.java] as Int
            return checkOpNoThrowMethod.invoke(
                mAppOps,
                value,
                uid,
                pkg
            ) as Int == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}