package com.example.redlink.Retrofit;

import com.example.redlink.Models.Album;
import com.example.redlink.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApi {

    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("photos")
    Call<List<Photo>> getPhotosFromAlbum(@Query("albumId") int albumId);
}
