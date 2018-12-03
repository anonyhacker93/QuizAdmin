package com.sheoran.dinesh.quizadmin.model;

import com.sheoran.dinesh.quizadmin.firebase.IFirebasable;

import java.io.Serializable;

public class Category implements IFirebasable,Serializable {
    private String categoryName;


    public Category() {

    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
