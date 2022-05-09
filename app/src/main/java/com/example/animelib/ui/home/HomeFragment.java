package com.example.animelib.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.animelib.R;
import com.example.animelib.adapters.main.MainScreenRVConfig;
import com.example.animelib.databinding.FragmentHomeBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        init();

        binding.bShowMore.setOnClickListener(view -> {
            initCardItem(1);
        });

        return root;
    }

    private void init() {
        initCardItem(1);
        //search("Дети на ");
    }

    private void initCardItem(int limit) {
        //вывод данных
        if(!binding.bShowMore.isChecked()){
            Query ref = FirebaseDatabase.getInstance().getReference("AnimeList");
            new FireBaseHelper(ref, requireContext()).readData((anime, keys) ->
                    new MainScreenRVConfig().setConfig(binding.rvMainCards, getContext(), anime));
        }else{
            Query ref = FirebaseDatabase.getInstance().getReference("AnimeList").limitToFirst(limit);
            new FireBaseHelper(ref, requireContext()).readData((anime, keys) ->
                    new MainScreenRVConfig().setConfig(binding.rvMainCards, getContext(), anime));
        }

    }

//    public void search(String query){
//        new FireBaseHelper().searchData(query, (anime, keys) ->
//                new MainScreenRVConfig().setConfig(binding.rvMainCards, getContext(), anime));
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}