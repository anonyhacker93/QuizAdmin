package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Created by Dinesh Kumar on 4/30/2019
public class CategoryFirebaseHelper extends FirebaseHelper {

    private static CategoryFirebaseHelper _instance;
    private ArrayList<Category> _categoryArrayList;
    private IDatabaseMonitor _iDatabaseMonitor;

    private CategoryFirebaseHelper(Context context) {
        super(context, Constants.FIREBASE_CATEGORY_REF);
    }

    public static CategoryFirebaseHelper getInstance(Context context) {
        if (_instance == null) {
            _instance = new CategoryFirebaseHelper(context);
        }
        return _instance;
    }

    public void addCategory(final String categName) {

        _databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category category = new Category(categName);
                if (dataSnapshot.child(categName).exists()) {
                } else {
                    _databaseReference.child(categName).setValue(category);
                    requestLoadCategory();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void requestLoadCategory() {
        Log.d(Constants.LOG_TAG, "CategoryFirebaseHelper : requestLoadCategory : start");
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

                if (_iDatabaseMonitor != null) {
                    _iDatabaseMonitor.onDatabaseChange(true,"Load successfully !");
                    Log.d(Constants.LOG_TAG, "CategoryFirebaseHelper : requestLoadCategory : load successfully !");
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                _iDatabaseMonitor.onDatabaseChange(false,"Error:Unable to load");
                Log.d(Constants.LOG_TAG, "CategoryFirebaseHelper : requestLoadCategory : " + databaseError.getMessage());
            }
        });
    }

    public void deleteCategory(Category category) {
        deleteNode(category.getCategoryName());
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

    @Override
    public void setDataNotifier(IDatabaseMonitor dataLoadNotifier) {
        _iDatabaseMonitor = dataLoadNotifier;
    }
}
