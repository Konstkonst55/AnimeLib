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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    private Query query;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        query = FirebaseDatabase.getInstance().getReference("AnimeLib");
        initCardItem();
    }

    private void initCardItem() {
        new FireBaseHelper(query).readFavouriteData((anime, keys) ->
                new FavouriteRVConfig().setConfig(binding.rvFavourites, getContext(), anime));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}