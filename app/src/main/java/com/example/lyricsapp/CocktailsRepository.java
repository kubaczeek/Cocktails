package com.example.lyricsapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CocktailsRepository {
    private final CocktailDao cocktailDao;
    private final LiveData<List<Cocktail>> cocktails;

    CocktailsRepository(Application application){
        CocktailDatabase database= CocktailDatabase.getDatabase(application);
        cocktailDao = database.cocktailDao();
        cocktails= cocktailDao.findAll();
    }
    LiveData<List<Cocktail>> findAllCocktails(){ return cocktails; }

    void insert(Cocktail cocktail){
        CocktailDatabase.databaseWriteExecutor.execute(()-> cocktailDao.insert(cocktail));
    }
    void update(Cocktail cocktail){
        CocktailDatabase.databaseWriteExecutor.execute(()-> cocktailDao.update(cocktail));
    }
    void delete(Cocktail cocktail){
        CocktailDatabase.databaseWriteExecutor.execute(()-> cocktailDao.delete(cocktail));
    }
}
