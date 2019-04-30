package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.IToast;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

// Created by Dinesh Kumar on 4/30/2019
public class QuestionFirebaseHelper extends FirebaseHelper {
    private static QuestionFirebaseHelper _instance;
    private DatabaseReference _idDatabaseReference;
    private static IToast _iToast;
    private int idNo;

    public QuestionFirebaseHelper(Context context) {
        super(context, Constants.FIREBASE_QUESTION_REF);
        _idDatabaseReference = getDatabaseReference(context,Constants.FIREBASE_ID_NO_REF);
    }

    public static QuestionFirebaseHelper getInstance(Context context, IToast iToast) {
        if (_instance == null) {
            _iToast = iToast;
            _instance = new QuestionFirebaseHelper(context);
            _instance.idNo = Constants.INITIAL_ID;
        }
        return _instance;
    }

    public void incrementId() {
        _idDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object id = dataSnapshot.getValue();
                if (id != null) {
                    idNo = Integer.parseInt(id.toString());
                } else {
                    idNo = 1;
                }
                idNo++;
                _idDatabaseReference.setValue(idNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(Constants.LOG_TAG, "QuestionAddFragment : incrementId() " + databaseError.getMessage());
            }
        });
    }

    public void addQuestion(final Questions questions) {
        questions.setId("" + idNo);
        _databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DatabaseReference categRefer = _databaseReference.child(questions.getCategoryName());

                categRefer.child(questions.getId()).setValue(questions);
                incrementId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                _iToast.create("Error :" + databaseError.getMessage());
            }
        });
    }

    public void updateQuestion(final Questions questions) {

        _databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(questions.getCategoryName()).exists()) {
                    DatabaseReference categRefer = _databaseReference.child(questions.getCategoryName());

                    categRefer.child(questions.getId()).setValue(questions);

                    //_iToast.create("Question Updated Successfully !!");
                } else {
                  //  _iToast.create("Oh ! unable to Update !!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                _iToast.create("Error :" + databaseError.getMessage());
            }
        });
    }

}
