package com.example.animelib.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.animelib.adapters.favourite.FavouriteRVConfig;
import com.example.animelib.databinding.FragmentFavouritesBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        initCardItem();
    }

    private void initCardItem() {
        new FireBaseHelper().readFavouriteData(new DataStatus() {
            @Override
            public void DataIsLoaded(List<Anime> anime, List<String> keys) {
                new FavouriteRVConfig().setConfig(binding.rvFavourites, getContext(), anime);
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