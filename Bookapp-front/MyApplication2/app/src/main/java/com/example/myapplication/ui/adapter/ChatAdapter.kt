package com.example.myapplication.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(private val context: Context,private val mChatMessageList:ArrayList<ChatMessage>) :BaseAdapter(){
    //var mChatMessageList: ArrayList<ChatMessage>? = null
    var inflater: LayoutInflater? = LayoutInflater.from(context)
    //var context: Context? = null



    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (mChatMessageList!![position].isMeSend === 1) 0 // 返回的数据位角标
        else 1
    }

    override fun getCount(): Int {
        return mChatMessageList!!.size
    }


    override fun getItem(i: Int): Any? {
        return mChatMessageList!![i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(
        i: Int,
        view: View?,
        viewGroup: ViewGroup?
    ): View? {
        var view = view
        val mChatMessage: ChatMessage = mChatMessageList!![i]
        val content: String = mChatMessage.content
        val time = formatTime(mChatMessage.time)
        val isMeSend: Int = mChatMessage.isMeSend
        val isRead: Int = mChatMessage.isRead ////是否已读（0未读 1已读）
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            if (isMeSend == 0) { //对方发送
                view = inflater?.inflate(R.layout.item_chat_receive_text, viewGroup, false)
                holder.tv_content = view!!.findViewById(R.id.tv_content)
                holder.tv_sendtime = view.findViewById(R.id.tv_sendtime)

            } else {
                view = inflater?.inflate(R.layout.item_chat_send_text, viewGroup, false)
                holder.tv_content = view!!.findViewById(R.id.tv_content)
                holder.tv_sendtime = view.findViewById(R.id.tv_sendtime)
                holder.tv_isRead = view.findViewById(R.id.tv_isRead)
            }
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.tv_sendtime!!.text = time
        holder.tv_content!!.visibility = View.VISIBLE
        holder.tv_content!!.text = content


        //如果是自己发送才显示未读已读
        if (isMeSend == 1) {
            if (isRead == 0) {
                holder.tv_isRead!!.text = "未读"
                holder.tv_isRead!!.setTextColor(
                    context!!.resources.getColor(R.color.jmui_jpush_blue)
                )
            } else if (isRead == 1) {
                holder.tv_isRead!!.text = "已读"
                holder.tv_isRead!!.setTextColor(Color.GRAY)
            } else {
                holder.tv_isRead!!.text = ""
            }
        } else {
//            holder.tv_display_name!!.visibility = View.VISIBLE
//            holder.tv_display_name!!.text = "服务器"
        }
        return view
    }

    internal class ViewHolder {
        var tv_content: TextView? = null
        var tv_sendtime: TextView? = null
//        var tv_display_name: TextView? = null
        var tv_isRead: TextView? = null
    }


    /**
     * 将毫秒数转为日期格式
     *
     * @param timeMillis
     * @return
     */
    private fun formatTime(timeMillis: String): String {
        val timeMillisl = timeMillis.toLong()
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(timeMillisl)
        return simpleDateFormat.format(date)
    }
}