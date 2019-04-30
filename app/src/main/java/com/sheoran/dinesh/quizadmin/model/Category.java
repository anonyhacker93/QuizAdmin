package com.sheoran.dinesh.quizadmin.model;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import  com.sheoran.dinesh.quizadmin.BR;
import java.io.Serializable;

public class Category extends BaseObservable implements Serializable {
    private String categoryName;

    public Category(){}//need for firebase

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.categoryName);
    }
}
