package com.example.redlink.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redlink.Models.Album;
import com.example.redlink.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "AlbumRecyclerAdapter";

    private ArrayList<Album> albumList;
    private ArrayList<Album> albumListFull;
    private OnItemClickListener mListener;

    /* interface for onclick on album in recyclerView */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public AlbumRecyclerAdapter(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album currentAlbum = albumList.get(position);
        holder.id.setText(String.valueOf(currentAlbum.getId()));
        holder.title.setText(currentAlbum.getTitle());
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, title;
        ConstraintLayout mLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_id);
            title = itemView.findViewById(R.id.titulo_id);
            mLayout = itemView.findViewById(R.id.recyclerLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    /* update recycler */
    public void updateData(ArrayList<Album> nuevaAlbumList){
        this.albumList = nuevaAlbumList;
        this.albumListFull = new ArrayList<>(nuevaAlbumList);
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    private Filter myFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            Log.d(TAG, "performFiltering: call");
            List<Album> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                Log.d(TAG, "charSequence null o 0");
                Log.d(TAG, "albumListFull.size: " + albumListFull.size());
                filteredList.addAll(albumListFull);
            } else {
                for (Album movie: albumListFull) {
                    if (movie.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            albumList.clear();
            albumList.addAll((Collection<? extends Album>) filterResults.values);
            notifyDataSetChanged();
        }
    };















}
