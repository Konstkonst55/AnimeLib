package com.example.animelib.firebase;

import android.text.BoringLayout;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireBaseHelper {
    private FirebaseDatabase db;
    private final DatabaseReference dbRef;
    private final List<Anime> animeList = new ArrayList<>();
    private final Query query;

    public FireBaseHelper(Query query){
        db = FirebaseDatabase.getInstance();
        this.dbRef = db.getReference("AnimeList");
        this.query = query;
    }

    public void readData(DataStatus dataStatus){
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    addListItems(dataSnapshot);
                }
                dataStatus.DataIsLoaded(animeList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //todo
            }
        });
    }

    public void readFavouriteData(DataStatus dataStatus){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    try{
                        if(dataSnapshot.child("favourite").getValue(Boolean.class)){
                            addListItems(dataSnapshot);
                        }
                        dataStatus.DataIsLoaded(animeList, keys);
                    }catch (Exception ignored){
                        
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //todo
            }
        });
    }

    public void readViewedData(DataStatus dataStatus){
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    try{
                        if(dataSnapshot.child("viewed").getValue(Boolean.class)){
                            addListItems(dataSnapshot);
                        }
                        dataStatus.DataIsLoaded(animeList, keys);
                    }catch (Exception ignored){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //todo
            }
        });
    }

    public void setIsFavourite(String key, Boolean bool){
        dbRef.child(key).child("favourite").setValue(bool);
    }

    public void setIsViewed(String key, Boolean bool){
        dbRef.child(key).child("viewed").setValue(bool);
    }

    public void searchData(String searchQuery, DataStatus dataStatus){
        query.orderByChild("AnimeList")
            .startAt(searchQuery)
            .endAt(searchQuery+"\uf8ff")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    animeList.clear();
                    List<String> keys = new ArrayList<>();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        keys.add(dataSnapshot.getKey());
                        addListItems(dataSnapshot);
                    }
                    dataStatus.DataIsLoaded(animeList, keys);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //todo
                }
            });
    }

    private void addListItems(DataSnapshot dataSnapshot) {
        Anime anime = new Anime(dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("genre").getValue(String.class),
                dataSnapshot.child("episodes").getValue(String.class),
                dataSnapshot.child("duration").getValue(String.class),
                dataSnapshot.child("image").getValue(String.class),
                dataSnapshot.child("video").getValue(String.class),
                dataSnapshot.child("type").getValue(String.class),
                dataSnapshot.child("description").getValue(String.class),
                dataSnapshot.child("date").getValue(String.class),
                dataSnapshot.child("favourite").getValue(Boolean.class),
                dataSnapshot.child("viewed").getValue(Boolean.class),
                dataSnapshot.getKey());
        animeList.add(anime);
        Log.i("ID", dataSnapshot.getKey());
    }
}
