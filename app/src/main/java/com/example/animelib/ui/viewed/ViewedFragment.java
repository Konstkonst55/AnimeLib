package com.example.animelib.ui.viewed;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.adapters.favourite.FavouriteRVConfig;
import com.example.animelib.adapters.main.MainScreenRVConfig;
import com.example.animelib.constatnts.Const;
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
    private static Context thisContext;
    private static RecyclerView rvCards;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewedViewModel = new ViewModelProvider(this).get(ViewedViewModel.class);

        binding = FragmentViewedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        thisContext = requireContext();
        rvCards = binding.rvViewed;
        query = FirebaseDatabase.getInstance().getReference(Const.DOCUMENT_TITLE);
        initCardItems();
    }

    private void initCardItems() {
        //вывод данных
        new FireBaseHelper(query, requireContext()).readViewedData((anime, keys) ->
                new FavouriteRVConfig().setConfig(binding.rvViewed, getContext(), anime));
    }

    public static void search(String query){
        Query ref = FirebaseDatabase
                .getInstance()
                .getReference(Const.DOCUMENT_TITLE)
                .orderByChild("name")
                .startAt(query)
                .endAt(query + Const.DOT);
        new FireBaseHelper(ref, thisContext).readViewedData((anime, keys) ->
                new MainScreenRVConfig().setConfig(rvCards, thisContext, anime));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}