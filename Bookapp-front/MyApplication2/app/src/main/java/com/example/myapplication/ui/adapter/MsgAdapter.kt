package com.example.myapplication.ui.adapter

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.model.Msg
import com.example.myapplication.ui.activity.ChatActivity

import java.lang.IllegalArgumentException

class MsgAdapter(private var msgList:MutableList<Msg>): RecyclerView.Adapter<MsgAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tHead: ImageView = view.findViewById(R.id.Head)
        val tName: TextView = view.findViewById(R.id.Name)
        val tMsg: TextView = view.findViewById(R.id.Text)
        val tTime:TextView=view.findViewById(R.id.time)
        fun bind( msg: Msg   ){

            tHead.setBackgroundResource(R.drawable.head1)
            tMsg.text = msg.lastMsg
            tName.text = msg.name
            tTime.text=msg.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val tView = LayoutInflater.from(parent.context).inflate(R.layout.msg_layout, parent, false)
        return ViewHolder(tView) //返回控件+布局
    }

    //对聊天控件的消息文本进行赋值
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = msgList[position]
        holder.bind(msg)
        holder.itemView.setOnClickListener {

                val name=msg.name
                val intent=Intent(MyApplication.context,ChatActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("name",name)
                MyApplication.context.startActivity(intent)

        }
    }

    //返回项数
    override fun getItemCount(): Int {
        return msgList.size
    }
    fun update(data:MutableList<Msg>){
        msgList=data
        notifyDataSetChanged()
    }
}