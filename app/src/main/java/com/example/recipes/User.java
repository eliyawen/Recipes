package com.example.recipes;

import java.util.ArrayList;

public class User {
    private ArrayList<Recipe> RecipesList;
    private String password;

    public User(){}

    public User(ArrayList<Recipe> recipesList, String password) {
        RecipesList = recipesList;
        this.password = password;
    }

    public ArrayList<Recipe> getRecipesList() {
        return RecipesList;
    }

    public void setRecipesList(ArrayList<Recipe> recipesList) {
        RecipesList = recipesList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
