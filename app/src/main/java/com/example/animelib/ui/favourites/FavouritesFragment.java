package com.example.animelib.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.animelib.adapters.favourite.Favourite;
import com.example.animelib.adapters.favourite.FavouriteAdapter;
import com.example.animelib.databinding.FragmentFavouritesBinding;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    ArrayList<Favourite> favourites = new ArrayList<>();

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
        binding.rvFavourites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFavourites.setAdapter(new FavouriteAdapter(getContext(), favourites));
        for (int i = 0; i < 20; i++) {
            favourites.add(new Favourite("sdgsafasdfasfasdfsdafsdafasdfsadfsdafsdafsdafsdafsadfsadfasdfasdfasdfsadf"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}