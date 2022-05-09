package com.example.animelib.activities;

import android.os.Bundle;
import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.animelib.R;
import com.example.animelib.classes.DownloadImageTask;
import com.example.animelib.classes.PlayerConfig;
import com.example.animelib.databinding.ActivityBrowseBinding;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class BrowseActivity extends YouTubeBaseActivity {

    private ActivityBrowseBinding binding;
    private YouTubePlayer.OnInitializedListener oilPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bPlay.setOnClickListener(view -> binding.ypvVideo.initialize(PlayerConfig.API_KEY, oilPlayer));

        binding.bBack.setOnClickListener(view -> onBackPressed());

        binding.srUpdate.setOnRefreshListener(() -> {
            loadData();
            binding.srUpdate.setRefreshing(false);
        });

        binding.cbFavourite.setOnClickListener(view -> {
            Snackbar.make(BrowseActivity.this, view, "Сохранено", Snackbar.LENGTH_LONG).show();
            new FireBaseHelper().setIsFavourite(getIntent().getStringExtra("key"), binding.cbFavourite.isChecked());
            //todo переделать на локальное хранение просомтренных и избранных
        });

        binding.cbViewed.setOnClickListener(view -> {
            Snackbar.make(BrowseActivity.this, view, "Сохранено", Snackbar.LENGTH_LONG).show();
            new FireBaseHelper().setIsViewed(getIntent().getStringExtra("key"), binding.cbViewed.isChecked());
            //todo переделать на локальное хранение просомтренных и избранных
        });

        init();
    }

    private void init() {
        loadData();
        playVideo();
    }

    private void loadData() {
        binding.tvNameBrowse.setText(getIntent().getStringExtra("name"));
        binding.tvType.setText(getString(R.string.title_type, getIntent().getStringExtra("type")));
        binding.tvEpisode.setText(getString(R.string.title_episode, getIntent().getStringExtra("episodes")));
        binding.tvGenre.setText(getString(R.string.title_genre, getIntent().getStringExtra("genre")));
        binding.tvDuration.setText(getString(R.string.title_duration, getIntent().getStringExtra("duration")));
        binding.tvDescription.setText(getString(R.string.title_description, getIntent().getStringExtra("description")));
        binding.tvDate.setText(getString(R.string.title_date, getIntent().getStringExtra("date")));
        binding.cbFavourite.setChecked(getIntent().getBooleanExtra("is_favourite", false));
        binding.cbViewed.setChecked(getIntent().getBooleanExtra("is_viewed", false));
        new DownloadImageTask(binding.ivPicture).execute(getIntent().getStringExtra("image"));
    }

    private void playVideo() {
        oilPlayer = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                binding.ypvVideo.setVisibility(View.VISIBLE);
                youTubePlayer.loadVideo(getIntent().getStringExtra("video"));
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Snackbar.make(binding.getRoot(), "Ошибка воспроизведения", Snackbar.LENGTH_LONG).show();
            }
        };
    }
}