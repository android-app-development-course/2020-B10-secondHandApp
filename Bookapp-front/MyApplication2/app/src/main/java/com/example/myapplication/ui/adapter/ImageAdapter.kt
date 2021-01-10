package com.example.myapplication.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import org.beahugs.imagepicker.utils.ImageUtil
import org.beahugs.imagepicker.utils.UriUtils
import org.beahugs.imagepicker.utils.VersionUtils

import java.util.ArrayList


class ImageAdapter(private var mContext: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var mImages: ArrayList<String>? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var isAndroidQ=VersionUtils.isAndroidQ()
    public fun getImages() : ArrayList<String>? {return mImages}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=mInflater.inflate(R.layout.adapter_image,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mImages==null)
            0
        else
            mImages!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image= mImages?.get(position)
        //是否剪切返回的图片
        val isCutImage= ImageUtil.isCutImage(mContext,image)
        if (isAndroidQ&&!isCutImage){
            Glide.with(mContext).load(UriUtils.getImageContentUri(mContext,image)).into(holder.ivImage)
        }else{
            Glide.with(mContext).load(image).into(holder.ivImage)
        }
    }

    fun refresh(images: ArrayList<String>){
        mImages=images
        Log.e("TAG", "refresh: $images")
        notifyDataSetChanged()
    }
    companion object class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var ivImage:ImageView= itemView.findViewById(R.id.iv_image)
    }
}
