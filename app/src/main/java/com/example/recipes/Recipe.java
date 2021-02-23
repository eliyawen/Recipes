package com.example.recipes;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;

public class Recipe {
    private String recipeName;
    private int time;
    private int difficulty;
    private Bitmap image;
    private ArrayList<String> ingredients;
    private String preparations;


    //constractor
    public Recipe(String recipeName, int time, int difficulty, Bitmap image, ArrayList<String> ingredients, String preparations) {
        this.recipeName = recipeName;
        this.time = time;
        this.difficulty = difficulty;
        this.image = image;
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }
}
