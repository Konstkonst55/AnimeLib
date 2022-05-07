package com.example.animelib.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseHelper {
    private final DatabaseReference dbRef;
    private final List<Anime> animeList = new ArrayList<>();

    public FireBaseHelper(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("AnimeList");
    }

    public void readData(DataStatus dataStatus){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    //Anime anime = dataSnapshot.getValue(Anime.class);
                    // FIXME: 08.05.2022 use getValue without child
                    Anime anime = new Anime(dataSnapshot.child("name").getValue(String.class),
                            dataSnapshot.child("genre").getValue(String.class),
                            dataSnapshot.child("episodes").getValue(String.class),
                            dataSnapshot.child("duration").getValue(String.class),
                            dataSnapshot.child("image").getValue(String.class),
                            dataSnapshot.child("video").getValue(String.class),
                            dataSnapshot.child("type").getValue(String.class),
                            dataSnapshot.child("description").getValue(String.class),
                            dataSnapshot.child("date").getValue(String.class));
                    animeList.add(anime);
                }
                dataStatus.DataIsLoaded(animeList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
