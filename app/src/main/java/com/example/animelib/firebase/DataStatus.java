package com.example.animelib.firebase;

import com.example.animelib.adapters.MainScreenCard;

import java.util.List;

public interface DataStatus{
    void DataIsLoaded(List<Anime> anime, List<String> keys);
    void DataIsInserted();
    void DataIsUpdated();
    void DataIsDeleted();
}