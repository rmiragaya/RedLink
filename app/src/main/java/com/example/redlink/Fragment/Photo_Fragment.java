package com.example.redlink.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redlink.Adapters.PhotosRecyclerAdapter;
import com.example.redlink.Models.Photo;
import com.example.redlink.R;

import java.util.ArrayList;

public class Photo_Fragment extends Fragment {
    private static final String TAG = "Photo_Fragment";
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Photo> mParam1;

    public Photo_Fragment() {
    }

    public static Photo_Fragment newInstance(ArrayList<Photo> param1) {
        Photo_Fragment fragment = new Photo_Fragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    private void initRecycler(View v, ArrayList<Photo> photoArrayList) {
        PhotosRecyclerAdapter adapter = new PhotosRecyclerAdapter(photoArrayList);
        RecyclerView recyclerView = v.findViewById(R.id.photo_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_, container, false);
        initRecycler(v, mParam1);
        return v;
    }


}
