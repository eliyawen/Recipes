package com.example.recipes;

import java.util.ArrayList;

public class User {
    //private ArrayList<Recipe> RecipesList;
    private String password;

    public User(){}

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
