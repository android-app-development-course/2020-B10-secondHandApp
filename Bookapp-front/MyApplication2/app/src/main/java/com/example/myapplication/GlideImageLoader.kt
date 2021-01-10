package com.example.myapplication

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null) {
            if (imageView != null) {
                Glide
                    .with(context)
                    .load(path)
                    .centerCrop()
                    .into(imageView)
            }
        };
    }

}