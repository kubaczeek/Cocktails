package com.example.lyricsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CocktailViewModel extends AndroidViewModel {
    private final CocktailsRepository cocktailsRepository;
    private final LiveData<List<Cocktail>> cocktails;
    public CocktailViewModel(@NonNull Application application){
        super(application);
        cocktailsRepository =new CocktailsRepository(application);
        cocktails = cocktailsRepository.findAllCocktails();
    }
    public LiveData<List<Cocktail>> findAll(){return cocktails;}
    public void insert(Cocktail cocktail){
        cocktailsRepository.insert(cocktail);}
    public void update(Cocktail cocktail){
        cocktailsRepository.update(cocktail);}
    public void delete(Cocktail cocktail){
        cocktailsRepository.delete(cocktail);}
}
