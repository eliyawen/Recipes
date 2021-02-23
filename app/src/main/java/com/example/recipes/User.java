package com.example.recipes;

import java.util.ArrayList;

public class User {
    private ArrayList<Category> categoryList;


    public User(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
        Category Csalad = new Category("salad");
        Category Cmain = new Category("main");
        Category Cpastry = new Category("pastry");
        Category Cdessert = new Category("dessert");
        categoryList.add(Csalad);
        categoryList.add(Cmain);
        categoryList.add(Cpastry);
        categoryList.add(Cdessert);
    }
}
