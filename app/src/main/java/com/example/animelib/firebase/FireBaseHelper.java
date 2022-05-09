package com.example.animelib.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Shader;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class FireBaseHelper {
    private FirebaseDatabase db;
    private final DatabaseReference dbRef;
    private final List<Anime> animeList = new ArrayList<>();
    private final Query query;
    private final Context context;
    private SharedPreferences prefs;

    //конструктор
    public FireBaseHelper(Query query, Context context){
        db = FirebaseDatabase.getInstance();
        this.dbRef = db.getReference("AnimeList");
        this.query = query;
        this.context = context;
        prefs = context.getSharedPreferences("SAVES", Context.MODE_PRIVATE);
    }

    //чтение данных из базы
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

    //чтение из базы только избранных
    public void readFavouriteData(DataStatus dataStatus){
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    try{
                        for (String set : prefs.getStringSet("favourite", new HashSet<>())) {
                            if(Objects.equals(dataSnapshot.getKey(), set)){
                                addListItems(dataSnapshot);
                            }
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

    //чтение из базы только просмотренных
    public void readViewedData(DataStatus dataStatus){
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animeList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    keys.add(dataSnapshot.getKey());
                    try{
                        for (String set : prefs.getStringSet("viewed", new HashSet<>())) {
                            if(Objects.equals(dataSnapshot.getKey(), set)){
                                addListItems(dataSnapshot);
                            }
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

    //todo convert to local
    public void setIsFavourite(String key, Boolean bool){
        dbRef.child(key).child("favourite").setValue(bool);
    }

    public void setIsViewed(String key, Boolean bool){
        dbRef.child(key).child("viewed").setValue(bool);
    }

    //поиск данных
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

    //доавбление данных в лист
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
                dataSnapshot.getKey());
        animeList.add(anime);
        Log.i("ID", dataSnapshot.getKey());
    }
}
