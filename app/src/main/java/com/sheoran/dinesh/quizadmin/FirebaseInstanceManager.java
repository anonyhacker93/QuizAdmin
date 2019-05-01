package com.sheoran.dinesh.quizadmin;

import android.content.Context;

import com.sheoran.dinesh.quizadmin.firebase.CategoryFirebaseHelper;
import com.sheoran.dinesh.quizadmin.firebase.QuestionFirebaseHelper;
import com.sheoran.dinesh.quizadmin.util.Constants;

// Created by Dinesh Kumar on 5/1/2019
public class FirebaseInstanceManager {
    private static FirebaseInstanceManager _instance;
    private  QuestionFirebaseHelper _questionFirebaseHelper;
    private  CategoryFirebaseHelper _categoryFirebaseHelper;

    public static FirebaseInstanceManager getInstance(Context context){
        if(_instance == null){
            _instance =new FirebaseInstanceManager();
            _instance.init(context);
        }
        return _instance;
    }

    private  void init(Context context) {
        _questionFirebaseHelper = QuestionFirebaseHelper.getInstance(context);
        _categoryFirebaseHelper = CategoryFirebaseHelper.getInstance(context);
    }

    public  QuestionFirebaseHelper getQuestionFirebaseHelper(){
        return _questionFirebaseHelper;
    }

    public  CategoryFirebaseHelper getCategoryFirebaseHelper(){
        return _categoryFirebaseHelper;
    }

}
