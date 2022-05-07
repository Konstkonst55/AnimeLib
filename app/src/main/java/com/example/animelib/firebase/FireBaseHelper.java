package com.example.animelib.firebase;

import androidx.annotation.NonNull;

import com.example.animelib.adapters.MainScreenCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseHelper {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private List<Anime> animeList = new ArrayList<>();

    public FireBaseHelper(){
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("animeList");
    }

    public void readData(DataStatus dataStatus){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Anime anime = keyNode.getValue(Anime.class);
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
