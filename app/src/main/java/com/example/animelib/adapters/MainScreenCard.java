package com.example.animelib.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.R;
import com.example.animelib.classes.DownloadImageTask;
import com.example.animelib.firebase.Anime;

public class MainScreenCard extends RecyclerView.ViewHolder {

    private final TextView name;
    private final ImageView image;
    private String key;

    public MainScreenCard(ViewGroup parent, Context context){
        super(LayoutInflater.from(context).inflate(R.layout.card_item_main, parent, false));
        name = itemView.findViewById(R.id.tvName);
        image = itemView.findViewById(R.id.ivAnimePicture);
    }

    public void bind(Anime anime, String key){
        name.setText(anime.getName());
        new DownloadImageTask(image).execute(anime.getImage());
        this.key = key;
    }
}
