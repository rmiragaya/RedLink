package com.example.redlink.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.redlink.Adapters.AlbumRecyclerAdapter;
import com.example.redlink.Fragment.Photo_Fragment;
import com.example.redlink.Models.Album;
import com.example.redlink.Models.Photo;
import com.example.redlink.R;
import com.example.redlink.Utils.Tools;
import com.example.redlink.ViewModel.MainActivityVM;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumRecyclerAdapter.OnItemClickListener {
    private static final String TAG = "MainActivity";
    private static final String SEARCH = "search";

    private AlbumRecyclerAdapter adapter;
    private ArrayList<Album> albumArrayList = new ArrayList<>();
    private MainActivityVM mainActivityVM;

    /* Lottie */
    private LottieAnimationView loading;
    private ConstraintLayout loadingBkg;

    private SearchView searchView;
    private String currentQuerry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        loadingBkg = findViewById(R.id.loadingBkg);

        Tools.checkTls(this);

        initRecycler();

        getAlbums();

        if (savedInstanceState != null){
            currentQuerry = savedInstanceState.getString(SEARCH);
            Log.d(TAG, "restoreSearch: " + currentQuerry);
        }

    }

    private void getAlbums(){
        mainActivityVM = ViewModelProviders.of(this).get(MainActivityVM.class);
        mainActivityVM.init();
        mainActivityVM.getLaptops().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                Log.d(TAG, "onChanged: call");

                if (albums == null){
                    Log.d(TAG, "sin conexión");
                    showToast();
                    hideLoadingAnimation();
                    return;
                }

                if(!albums.isEmpty()){
                    hideLoadingAnimation();
                    albumArrayList = new ArrayList<>(albums);
                    adapter.updateData(albumArrayList);
                }
            }
        });
    }

    private void initRecycler(){
        adapter = new AlbumRecyclerAdapter(albumArrayList);
        RecyclerView recyclerView = findViewById(R.id.recy_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
    }


    @Override
    public void onItemClick(int position) {
        showLoadingAnimation();
        Toast.makeText(this, albumArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        int albumId = albumArrayList.get(position).getId();

        mainActivityVM.searchPhotos(albumId);
        mainActivityVM.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {

                if (photos == null){
                    showToast();
                    hideLoadingAnimation();
                    return;
                }

                if(!photos.isEmpty()){
                    hideLoadingAnimation();
                    ArrayList<Photo> photosArraylist = new ArrayList<>(photos);
                    openFragment(photosArraylist);
                }
            }
        });
    }

    private void openFragment(ArrayList<Photo> photosResult){
        Photo_Fragment fragment = Photo_Fragment.newInstance(photosResult);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_right, R.anim.enter_right, R.anim.exit_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_photo_container, fragment, "PHOTO_FRAGMENT").commit();
    }

    private void hideLoadingAnimation(){
        loading.setVisibility(View.GONE);
        loadingBkg.setVisibility(View.GONE);
    }

    private void showLoadingAnimation(){
        loading.setVisibility(View.VISIBLE);
        loadingBkg.setVisibility(View.VISIBLE);
    }

    private void showToast(){
        Toast toast = Toast.makeText(MainActivity.this, getString(R.string.sin_conexion), Toast.LENGTH_LONG);
        TextView v = toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        if (!currentQuerry.isEmpty()) {
            menuItem.expandActionView();
            searchView.setQuery(currentQuerry, true);
            searchView.clearFocus();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String search = searchView.getQuery().toString();
        Log.d(TAG, "onSaveInstanceState: " + search);
        outState.putString(SEARCH, search );
    }

}
