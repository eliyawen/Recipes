package com.example.recipes;

import java.util.ArrayList;
import java.util.Comparator;

public class Category {
    private String categoryName;
    private ArrayList<Recipe> recipes;

    //constractor

    public Category(){}

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.recipes = new ArrayList<Recipe>();
        Recipe r = new Recipe("temp","1624369854167.jpg");
        this.recipes.add(r);
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

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }
}
    class sortByName implements Comparator<String>{
        public int compare(String a, String b){
            return a.compareTo(b);
        }
    }

