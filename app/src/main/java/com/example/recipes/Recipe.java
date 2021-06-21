package com.example.recipes;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String recipeName;
    private int time;
    private int difficulty;
    private String imageUri;
    private Map<String, String> ingredients;
    private String preparations;


    //constractor
    public Recipe(){}

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public Recipe(String recipeName, int time, int difficulty, String imageUri, Map<String, String> ingredients, String preparations) {
        this.recipeName = recipeName;
        this.time = time;
        this.difficulty = difficulty;
        this.imageUri = imageUri;
        this.ingredients = ingredients;
        this.preparations = preparations;
    }

    //geters and seters

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String  getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
