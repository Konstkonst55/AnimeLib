package com.example.animelib.ui.viewed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.animelib.adapters.favourite.FavouriteRVConfig;
import com.example.animelib.adapters.main.MainScreenRVConfig;
import com.example.animelib.databinding.FragmentViewedBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class ViewedFragment extends Fragment {

    private ViewedViewModel viewedViewModel;
    private FragmentViewedBinding binding;
    private Query query;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewedViewModel = new ViewModelProvider(this).get(ViewedViewModel.class);

        binding = FragmentViewedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        query = FirebaseDatabase.getInstance().getReference("AnimeList");
        initCardItems();
    }

    private void initCardItems() {
        //вывод данных
        new FireBaseHelper(query, requireContext()).readViewedData((anime, keys) ->
                new FavouriteRVConfig().setConfig(binding.rvViewed, getContext(), anime));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}