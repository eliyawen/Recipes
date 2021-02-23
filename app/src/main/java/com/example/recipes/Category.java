package com.example.recipes;

import java.util.ArrayList;

public class Category {
    private String categoryName;
    private ArrayList<Recipe> recipes;

    //constractor

    public Category(String categoryName) {
        this.categoryName = categoryName;
        //no need to put recipes, because that happends in other actions
    }

    //getters and settes

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
