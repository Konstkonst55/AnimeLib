package com.example.animelib.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.animelib.adapters.MainScreenRVConfig;
import com.example.animelib.databinding.FragmentHomeBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;

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
            //todo ShowMore
        });

        return root;
    }

    private void init() {
        initCardItem();
    }

    private void initCardItem() {
        new FireBaseHelper().readData(new DataStatus() {
            @Override
            public void DataIsLoaded(List<Anime> anime, List<String> keys) {
                new MainScreenRVConfig().setConfig(binding.rvMainCards, getContext(), anime);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}