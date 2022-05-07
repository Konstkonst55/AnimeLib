package com.example.animelib.adapters;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.firebase.Anime;

import java.util.List;

public class MainScreenRVConfig {

    public void setConfig(RecyclerView rv, Context context, List<Anime> animeList){
        MainScreenCardAdapter mainScreenCardAdapter = new MainScreenCardAdapter(animeList, context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(mainScreenCardAdapter);
    }
}
