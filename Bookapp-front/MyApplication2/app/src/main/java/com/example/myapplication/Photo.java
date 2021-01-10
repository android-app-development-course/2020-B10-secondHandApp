package com.example.myapplication;
import android.net.Uri;

import java.net.URI;

public class Photo {
    /**
     * 商品类，就是首页的商品的显示的类
     */
    private String photoID;
    public Photo(String photoImageID){
        photoID=photoImageID;
    }
    public String getPhotoPhotoID(){
        return photoID;
    }
}

