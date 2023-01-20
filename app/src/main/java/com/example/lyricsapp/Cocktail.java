package com.example.lyricsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Cocktail")
public class Cocktail implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String ingredients;
    private String ytlink;
    private String receipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Cocktail(String title, String ingredients, String receipe, String ytlink) {
        this.title = title;
        this.ingredients = ingredients;
        this.receipe = receipe;
        this.ytlink = ytlink;
    }

    public String getYtlink() {
        return ytlink;
    }

    public void setYtlink(String ytlink) {
        this.ytlink = ytlink;
    }

    public String getReceipe() {
        return receipe;
    }

    public void setReceipe(String receipe) {
        this.receipe = receipe;
    }

}
