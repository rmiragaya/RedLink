package com.example.redlink.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redlink.Models.Photo;
import com.example.redlink.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.ViewHolder>{

    private ArrayList<Photo> photoArrayList;

    public PhotosRecyclerAdapter(ArrayList<Photo> photoArrayList) {
        this.photoArrayList = photoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo currentPhoto = photoArrayList.get(position);
        holder.id.setText(String.valueOf(currentPhoto.getId()));
        holder.title.setText(currentPhoto.getTitle());
        Picasso.get().load(currentPhoto.getThumbnailUrl()).fit().centerInside().placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, title;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.photo_id_id);
            title = itemView.findViewById(R.id.title_photo_id);
            imageView = itemView.findViewById(R.id.photo_imageview_id);
        }
    }
}
