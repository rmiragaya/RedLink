package com.example.redlink.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.redlink.Models.Album;
import com.example.redlink.Models.Photo;
import com.example.redlink.Repositories.AlbumRepo;
import com.example.redlink.Repositories.PhotosRepo;

import java.util.List;

public class MainActivityVM extends ViewModel {

    private MutableLiveData<List<Album>> mAlbums;
    private MutableLiveData<List<Photo>> mPhotos;

    public void init(){
        if (mAlbums != null){
            return;
        }
        AlbumRepo mRepo = AlbumRepo.getInstance();
        mAlbums = mRepo.getAlbumList();
    }

    public LiveData<List<Album>> getLaptops(){
        return mAlbums;
    }

    public void searchPhotos(int albumId){
        PhotosRepo mRepo = PhotosRepo.getInstance();
        mPhotos = mRepo.getAlbumList(albumId);
    }

    public LiveData<List<Photo>> getPhotos(){
        return mPhotos;
    }
}
