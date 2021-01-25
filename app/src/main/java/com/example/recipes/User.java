package com.example.recipes;

import java.util.ArrayList;

public class User {
    private ArrayList<Recipe> arrsalad, arrmain, arrpastry, arrdesert;
    private String username;

    public User(ArrayList<Recipe> arrsalad, ArrayList<Recipe> arrmain, ArrayList<Recipe> arrpastry, ArrayList<Recipe> arrdesert) {
        this.arrsalad = arrsalad;
        this.arrmain = arrmain;
        this.arrpastry = arrpastry;
        this.arrdesert = arrdesert;
    }
}
