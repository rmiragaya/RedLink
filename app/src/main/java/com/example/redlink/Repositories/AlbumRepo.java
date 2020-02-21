package com.example.redlink.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.redlink.Models.Album;
import com.example.redlink.Retrofit.JsonApi;
import com.example.redlink.Retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Singleton
 */
public class AlbumRepo {
    private static final String TAG = "AlbumRepo";

    private static AlbumRepo instance;
    private JsonApi apiRequest;

    public static AlbumRepo getInstance(){
        if (instance == null ){
            instance = new AlbumRepo();
        }
        return instance;
    }

    public AlbumRepo(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(JsonApi.class);
    }


    public MutableLiveData<List<Album>> getAlbumList(){
        final MutableLiveData<List<Album>> data = new MutableLiveData<>();

        apiRequest.getAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                Log.d(TAG, "onResponse: call OK");
                if (response.body() != null) {
                    data.setValue(response.body());

                    for (Album album : response.body()){
                        Log.d(TAG, "Album: " + album.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                data.setValue(null);
                Log.d(TAG, "onFailure: call");
                Log.d(TAG, "onFailure: " + t.fillInStackTrace());
            }
        });

        return data;
    }












}
