package com.example.recipes;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String recipeName;
    private String time;
    private int difficulty;
    private String imageString;
    private Map<String, String> ingredients;
    private String preparations;


    //constractor
    public Recipe(){}

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public Recipe(String recipeName, String time, int difficulty, String imageString, Map<String, String> ingredients, String preparations) {
        this.recipeName = recipeName;
        this.time = time;
        this.difficulty = difficulty;
        this.imageString = ;
        this.ingredients = ingredients;
        this.preparations = preparations;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }
}


