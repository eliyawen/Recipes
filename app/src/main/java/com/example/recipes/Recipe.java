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
    private String key;
    private String ingredients;
    //private Map<String, String> ingredients;
    private String preparationMethod;


    //constractor
    public Recipe(){}

    public Recipe(String recipeName, String key){
        this.recipeName= recipeName;
        this.key=key;
    }

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public Recipe(String recipeName, String time, int difficulty, String key, String ingredients, String preparationMethod) {
        this.recipeName = recipeName;
        this.time = time;
        this.difficulty = difficulty;
        this.key = key;
        this.ingredients = ingredients;
        this.preparationMethod = preparationMethod;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String  getIngredients() {
        return ingredients;
    }

    public void setIngredients(String  ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }
}


