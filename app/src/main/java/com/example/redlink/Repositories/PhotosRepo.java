package com.example.redlink.Repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.redlink.Models.Photo;
import com.example.redlink.Retrofit.JsonApi;
import com.example.redlink.Retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Singleton
 */
public class PhotosRepo {
    private static final String TAG = "PhotosRepo";

    private static PhotosRepo instance;
    private JsonApi apiRequest;

    public static PhotosRepo getInstance(){
        if (instance == null ){
            instance = new PhotosRepo();
        }
        return instance;
    }

    public PhotosRepo(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(JsonApi.class);
    }

    public MutableLiveData<List<Photo>> getAlbumList(int albumId){
        final MutableLiveData<List<Photo>> data = new MutableLiveData<>();

        apiRequest.getPhotosFromAlbum(albumId).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d(TAG, "onResponse: call OK");
                Log.d(TAG, "statusCode: " + response.code());
                if (response.body() != null) {
                    data.setValue(response.body());

                    for (Photo photo : response.body()){
                        Log.d(TAG, "Photo: " + photo.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                data.setValue(null);
                Log.d(TAG, "onFailure: call");
                Log.d(TAG, "onFailure: " + t.fillInStackTrace());
            }
        });

        return data;
    }



}
