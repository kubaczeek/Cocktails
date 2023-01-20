package com.example.lyricsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cocktail cocktail);
    @Update
    void update(Cocktail cocktail);
    @Delete
    void delete(Cocktail cocktail);
    @Query("DELETE FROM cocktail")
    void deleteAll();
    @Query("SELECT * FROM cocktail ORDER BY title")
    LiveData<List<Cocktail>> findAll();
    @Query("SELECT * FROM cocktail WHERE title LIKE :title")
    List<Cocktail> findCocktailWithTitle(String title);
}

