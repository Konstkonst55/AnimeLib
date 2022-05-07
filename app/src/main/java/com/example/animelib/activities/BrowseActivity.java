package com.example.animelib.activities;

import android.os.Bundle;
import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.animelib.classes.DownloadImageTask;
import com.example.animelib.classes.PlayerConfig;
import com.example.animelib.databinding.ActivityBrowseBinding;
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

        init();

        //todo binding.tvType.setText(getString(R.string.title_type, "nbg"));
    }

    private void init() {
        loadData();
        playVideo();
    }

    private void loadData() {
        new DownloadImageTask(binding.ivPicture).execute(
                "https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/d94c0b17-8fd9-4a97-94f6-31623d8fc163/300x450"
        );
    }

    private void playVideo() {
        oilPlayer = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                binding.ypvVideo.setVisibility(View.VISIBLE);
                youTubePlayer.loadVideo("hRj5rQCC_XM");
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Snackbar.make(binding.getRoot(), "Ошибка воспроизведения", Snackbar.LENGTH_LONG).show();
            }
        };
    }
}