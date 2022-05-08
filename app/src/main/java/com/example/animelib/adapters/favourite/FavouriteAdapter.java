package com.example.animelib.adapters.favourite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.R;
import com.example.animelib.activities.BrowseActivity;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Favourite> favouriteList;

    public FavouriteAdapter(Context context, List<Favourite> favourites) {
        this.favouriteList = favourites;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item_fav_viewed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, int position) {
        Favourite favourite = favouriteList.get(position);
        holder.name.setText(favourite.getName());
        holder.viewMain.setOnClickListener(view -> view.getContext().startActivity(new Intent(view.getContext(), BrowseActivity.class)));
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final View viewMain;
        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.tvAnimeName);
            viewMain = view;
        }
    }
}
