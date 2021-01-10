package com.example.myapplication.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.Goods
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.ui.activity.DetailActivity
import java.io.File

class GoodsAdapter(activity: Activity, val resourceId: Int, data: List<Goods>) : ArrayAdapter<Goods>(activity, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val goodsImage: ImageView = view.findViewById(R.id.GoodsImage)
            val goodsName: TextView = view.findViewById(R.id.GoodsName)
            val goodsPrice: TextView = view.findViewById(R.id.GoodsPrice)
            viewHolder = ViewHolder(goodsImage, goodsName,goodsPrice)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val goods = getItem(position) // 获取当前项的Goods实例
        if (goods != null) {

           // val file = File(goods.image)
            //val bitmap = BitmapFactory.decodeFile(file.toString())
            Glide.with(viewHolder.GoodsImage.context).load(goods.image).into(viewHolder.GoodsImage)
            //viewHolder.GoodsImage.setImageBitmap(bitmap)
            viewHolder.GoodsName.text = goods.name
            viewHolder.GoodsPrice.text="￥"+goods.price
        }
        view.setOnClickListener(){
            val intent=Intent(context,DetailActivity::class.java)
            intent.putExtra("goodsid",goods!!.goodsid)
            intent.putExtra("firstImage",goods!!.image)
//            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        return view
    }

    inner class ViewHolder(val GoodsImage: ImageView, val GoodsName: TextView,val GoodsPrice:TextView)

}