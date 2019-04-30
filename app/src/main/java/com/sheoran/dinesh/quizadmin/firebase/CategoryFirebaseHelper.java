package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.IToast;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Created by Dinesh Kumar on 4/30/2019
public class CategoryFirebaseHelper extends FirebaseHelper {

    private static CategoryFirebaseHelper _instance;
    private static IToast _iToast;
    private ArrayList<Category> _categoryArrayList;


    private CategoryFirebaseHelper(Context context) {
        super(context, Constants.FIREBASE_CATEGORY_REF);
    }

    public static CategoryFirebaseHelper getInstance(Context context, IToast iToast) {
        if (_instance == null) {

            _instance = new CategoryFirebaseHelper(context);
            _instance.loadCategories();
        }
        _iToast = iToast;
        return _instance;
    }

    public void addCategory(final String categName) {

        _databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category category = new Category(categName);
                if (dataSnapshot.child(categName).exists()) {
                    _iToast.create("Category already exists !");
                } else {
                    _databaseReference.child(categName).setValue(category);
                    _iToast.create("Category added successfully !");
                    loadCategories();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                _iToast.create("Oh ! Unable to access : " + databaseError.getMessage());
            }
        });
    }

    private void loadCategories() {
        _categoryArrayList = new ArrayList<>();
        _databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                Iterator<DataSnapshot> itr = ds.getChildren().iterator();
                while (itr.hasNext()) {
                    DataSnapshot shot = itr.next();
                    Category category = shot.getValue(Category.class);
                    if (category != null) {
                        _categoryArrayList.add(category);
                    }
                }
                Log.d("QuizAdmin", "CategoryFirebaseHelper : loadCategories : load successfully !");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("QuizAdmin", "CategoryFirebaseHelper : loadCategories : " + databaseError.getMessage());
            }
        });
    }

    public ArrayList<Category> getCategoryList() {
        return _categoryArrayList;
    }

    public List<String> getCategoryNames() {
        List<String> categoryNameList = new ArrayList<>();
        for (Category category : getCategoryList()) {
            categoryNameList.add(category.getCategoryName());
        }
        return categoryNameList;
    }

}
