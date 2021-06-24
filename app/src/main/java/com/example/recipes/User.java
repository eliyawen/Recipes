package com.example.recipes;

import java.util.ArrayList;

public class User {
    //private ArrayList<Recipe> RecipesList;
    private String password;

    public User(){}

    public User(String password) {
        this.password = password;
        //this.RecipesList = new ArrayList<Recipe>();
        //Recipe r = new Recipe("temp");
        //this.RecipesList.add(r);
    }

    //public ArrayList<Recipe> getRecipesList() {
      //  return RecipesList;
    //}

    //public void setRecipesList(ArrayList<Recipe> recipesList) {
       // RecipesList = recipesList;
    //}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
