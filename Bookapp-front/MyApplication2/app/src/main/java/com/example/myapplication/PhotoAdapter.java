package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.RoundTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * 商品类的适配器，用于RecyclerView空间的瀑布平显示
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photoList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo_item_photo);
        }
    }
    public PhotoAdapter(List<Photo> PhotoList){
        photoList=PhotoList;
    }
    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_photo_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Photo photo=photoList.get(position);
        File file=new File(photo.getPhotoPhotoID());
        System.out.println(photo.getPhotoPhotoID());
        Picasso.get().load(file).placeholder(R.mipmap.loading).transform(new RoundTransform(10,0)).into(holder.photo);
    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
