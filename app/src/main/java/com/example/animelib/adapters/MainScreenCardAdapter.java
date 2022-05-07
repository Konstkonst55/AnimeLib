package com.example.animelib.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.activities.BrowseActivity;
import com.example.animelib.firebase.Anime;

import java.util.List;

public class MainScreenCardAdapter extends RecyclerView.Adapter<MainScreenCard> {

    private final List<Anime> cardList;
    private final Context context;

    public MainScreenCardAdapter(List<Anime> cards, Context context) {
        this.cardList = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public MainScreenCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainScreenCard(parent, context);
    }

    @Override
    public void onBindViewHolder(MainScreenCard holder, int position) {
        holder.bind(cardList.get(position));
        holder.itemView.setOnClickListener(view -> view.getContext().startActivity(new Intent(view.getContext(), BrowseActivity.class)));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
