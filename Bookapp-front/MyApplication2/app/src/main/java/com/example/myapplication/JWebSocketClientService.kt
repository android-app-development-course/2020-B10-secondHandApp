package com.example.myapplication

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.example.myapplication.model.ChatModel
import com.example.myapplication.model.Msg
import com.example.myapplication.net.Util
import com.example.myapplication.ui.activity.MainActivity1
import okhttp3.Call
import retrofit2.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.io.IOException
import java.net.URI


class JWebSocketClientService : Service() {
    var client: JWebSocketClient? = null
    private val mBinder = JWebSocketClientBinder()
    val chatModel=ChatModel()
    //灰色保活
    class GrayInnerService : Service() {
        override fun onStartCommand(
            intent: Intent,
            flags: Int,
            startId: Int
        ): Int {
//            startForeground(GRAY_SERVICE_ID, Notification())
            stopForeground(true)
            stopSelf()
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(intent: Intent): IBinder? {
            return null
        }
    }
    //锁屏唤醒
    private var wakeLock : PowerManager.WakeLock? = null

    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    @SuppressLint("InvalidWakeLockTag")
    private fun acquireWakeLock() {
        if (null == wakeLock) {
            val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE, "PostLocationService")
            if (null != wakeLock) {
                wakeLock!!.acquire()
            }
        }
    }

    //用于Activity和service通讯
    inner class JWebSocketClientBinder : Binder() {
        val service: JWebSocketClientService
            get() = this@JWebSocketClientService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //初始化websocket
        initSocketClient()
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE) //开启心跳检测

        //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，隐藏Notification上的图标
//            startForeground(
//                GRAY_SERVICE_ID,
//                Notification()
//            )
        } else if (Build.VERSION.SDK_INT in 19..24) {
            //Android4.3 - Android7.0，隐藏Notification上的图标
            val innerIntent = Intent(
                this,
                GrayInnerService::class.java
            )
            startService(innerIntent)
//            startForeground(
//                GRAY_SERVICE_ID,
//                Notification()
//            )
        } else {
            //Android7.0以上app启动后通知栏会出现一条"正在运行"的通知
//            startForeground(
//                GRAY_SERVICE_ID,
//                Notification()
//            )
        }
        acquireWakeLock()
        return START_STICKY
    }

    override fun onDestroy() {
        closeConnect()
        super.onDestroy()
    }

    /**
     * 初始化websocket连接
     */
    private fun initSocketClient() {
        val uri = URI.create(Util.ws)
        Log.e("initSocketClient: ", uri.toString())
        client = object : JWebSocketClient(uri) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMessage(message: String) {
                println("onMessage:$message")
                var msgjson:JSONObject= JSONObject()
                if (!message.startsWith("[")){
                    msgjson= JSONObject(message)
                    Log.e("JWebSocketClientService", "收到的消息："+msgjson)
                    val intent = Intent()
                    intent.action = "com.xch.servicecallback.content"
                    intent.putExtra("message", msgjson.getString("msgContent"))
                    intent.putExtra("toName",msgjson.getString("toName"))
                    intent.putExtra("fromName",msgjson.getString("fromName"))
                    sendBroadcast(intent)
                    checkLockAndShowNotification(message)
                }

                //println(msgjson)


            }

             override fun onOpen(handshakedata: ServerHandshake) {
                super.onOpen(handshakedata)
                 val prefs=getSharedPreferences("ui.activity.LoginActivity", Context.MODE_PRIVATE)
                 val name=prefs.getString("logname","")
                 if(name!="")
                    client?.send("online$name")
                Log.e("JWebSocketClientService", "websocket连接成功")
            }
        }
        connect()
    }

    /**
     * 连接websocket
     */
    private fun connect() {
        object : Thread() {
            override fun run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client?.connectBlocking()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    fun sendMsg(toName:String,msg: String) {
//        if (null != client) {
//            Log.e("JWebSocketClientService", "发送的消息：$msg")
//            client!!.send(msg)
//        }
        var map=HashMap<String,String>()
        val prefs=getSharedPreferences("ui.activity.LoginActivity",Context.MODE_PRIVATE)
        val fromName=prefs.getString("logname","")
        if (fromName=="")
        {
            //没找到logname
            Toast.makeText(MyApplication.context, "出错了，请先登录或重新登录", Toast.LENGTH_SHORT).show()
            return
        }
        map["toName"]=toName
        map["fromName"]=fromName!!
        map["msgContent"]=msg
        chatModel.sendMsg(map).enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                println("sendFailure")
            }

            override fun onResponse(
                call: retrofit2.Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                println("sendsuccess")
            }

        })
    }

    /**
     * 断开连接
     */
    private fun closeConnect() {
        try {
            client?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client = null
        }
    }
    //    -----------------------------------消息通知--------------------------------------------------------
    /**
     * 检查锁屏状态，如果锁屏先点亮屏幕
     *
     * @param content
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkLockAndShowNotification(content: String) {
        //管理锁屏的一个服务
        val km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (km.isKeyguardLocked) { //锁屏
            //获取电源管理器对象
            val pm =
                this.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isInteractive) {
                @SuppressLint("InvalidWakeLockTag") val wl =
                    pm.newWakeLock(
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright"
                    )
                wl.acquire() //点亮屏幕
                wl.release() //任务结束后释放
            }
            sendNotification(content)
            //改
        } else {
            sendNotification(content)
        }
    }

    /**
     * 发送通知
     *
     * @param content
     */
    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(content: String) {
        val jsoncontent=JSONObject(content)
        val intent = Intent()
        intent.setClass(this, MainActivity1::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notifyManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel= NotificationChannel(CHANNEL_ONE_ID,CHANNEL_ONE_NAME,NotificationManager.IMPORTANCE_HIGH)
//             notificationChannel.enableLights(true)
//             notificationChannel.lightColor=(Color.RED)
//             notificationChannel.setShowBadge(true)
//             notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
             notifyManager.createNotificationChannel(notificationChannel)

        } else {

        }
        val notification: Notification = NotificationCompat.Builder(this,CHANNEL_ONE_ID)//改
            //.setAutoCancel(true) // 设置该通知优先级
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(jsoncontent.getString("fromName"))
            .setContentText(jsoncontent.getString("msgContent"))
            .setVisibility(VISIBILITY_PUBLIC)
            .setWhen(System.currentTimeMillis()) // 向通知添加声音、闪灯和振动效果
            //.setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_ALL or Notification.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .build()
        //notifyManager.notify(1, notification) //id要保证唯一
        startForeground(GRAY_SERVICE_ID,notification)
    }

    private val mHandler = Handler(Looper.myLooper()!!)//改
    private val heartBeatRunnable: Runnable = object : Runnable {
        override fun run() {
            Log.e("JWebSocketClientService", "心跳包检测websocket连接状态")
            if (client != null) {
                if (client!!.isClosed) {
                    reconnectWs()
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null
                initSocketClient()
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE)
        }
    }

    /**
     * 开启重连
     */
    private fun reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable)
        object : Thread() {
            override fun run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连")
                    client?.reconnectBlocking()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    companion object {
        private const val GRAY_SERVICE_ID = 1001
        val  CHANNEL_ONE_ID = "CHANNEL_ONE_ID"
        val CHANNEL_ONE_NAME= "CHANNEL_ONE_ID"
        //    -------------------------------------websocket心跳检测------------------------------------------------
        private const val HEART_BEAT_RATE = 10 * 1000 //每隔10秒进行一次对长连接的心跳检测
            .toLong()
    }
}