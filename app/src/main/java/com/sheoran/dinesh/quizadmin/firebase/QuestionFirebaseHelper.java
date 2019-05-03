package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

// Created by Dinesh Kumar on 4/30/2019
public class QuestionFirebaseHelper extends FirebaseHelper {
    private static QuestionFirebaseHelper _instance;
    private DatabaseReference _idDatabaseReference;
    private int idNo;
    private IDatabaseMonitor _dataLoadNotifier;
    private ArrayList<Questions> _questionArrayList = new ArrayList<>();

    public QuestionFirebaseHelper(Context context) {
        super(context, Constants.FIREBASE_QUESTION_REF);
        _idDatabaseReference = getDatabaseReference(context, Constants.FIREBASE_ID_NO_REF);
    }

    public static QuestionFirebaseHelper getInstance(Context context) {
        if (_instance == null) {
            _instance = new QuestionFirebaseHelper(context);
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
                    idNo = Constants.INITIAL_ID;
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

            }
        });
    }

    public void updateQuestion(final Questions questions) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : updateQuestion ");
        _databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(questions.getCategoryName()).exists()) {
                    DatabaseReference categRefer = _databaseReference.child(questions.getCategoryName());

                    categRefer.child(questions.getId()).setValue(questions);
                    Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : updateQuestion onDataChange done");

                    if (iDatabaseMonitor != null) {
                        iDatabaseMonitor.onDatabaseChange(true, "Update successfully !");
                    }
                } else {
                    iDatabaseMonitor.onDatabaseChange(false, "Unable to update !");
                    Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : updateQuestion onDataChange category already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.LOG_TAG, "QuestionFirebaseHelper : updateQuestion onCancelled " + databaseError.getMessage());
                iDatabaseMonitor.onDatabaseChange(false, "Error:Unable to update");
            }
        });
    }

    public void requestQuestionByCategoryName(String categoryName) {
        fetchQuestionsByCategoryName(categoryName);
    }

    private void fetchQuestionsByCategoryName(String categName) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : fetchQuestionsByCategoryName starts");
        _databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.child(categName).getChildren().iterator();
                Questions questions;
                _questionArrayList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    questions = dataShot.getValue(Questions.class);
                    _questionArrayList.add(questions);
                }
                Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : fetchQuestionsByCategoryName onDataChange");
                if (_dataLoadNotifier != null) {
                    _dataLoadNotifier.onDatabaseChange(true, "Category fetched");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : Exception : " + databaseError.getMessage());
                if (_dataLoadNotifier != null) {
                    _dataLoadNotifier.onDatabaseChange(false, "Unable to fetch category");
                }
            }
        });
    }

    public boolean deleteQuestion(final Questions questions) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteQuestion 1");
        boolean isDeleted = deleteNode(questions.getCategoryName(), questions.getId());
        if (isDeleted) {
            String id = questions.getId();
            ListIterator<Questions> itr = _questionArrayList.listIterator();
            while (itr.hasNext()) {
                Questions question = itr.next();
                if (id.equals(question.getId())) {
                    itr.remove();
                }
            }
        }
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteQuestion done");
        return isDeleted;
    }

    public boolean deleteQuestionByCategoryName(final String categoryName) {
        return deleteNode(categoryName);
    }

    public boolean deleteNode(String parentName, String childName) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteNode 1");
        try {
            if (_databaseReference.child(parentName) != null) {
                _databaseReference.child(parentName).child(childName).removeValue();
                Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteNode done");
                return true;
            }
            Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteNode not done");
            return false;
        } catch (Exception ex) {
            Log.e(Constants.LOG_TAG, "QuestionFirebaseHelper :deleteNode exception " + ex);
            return false;
        }
    }

    public ArrayList<Questions> getAllQuestionsList() {
        return _questionArrayList;
    }

    public Questions getQuestionById(final String id) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :getQuestionById 1");
        ListIterator<Questions> itr = _questionArrayList.listIterator();
        while (itr.hasNext()) {
            Questions questions = itr.next();
            if (id.equals(questions.getId())) {
                return questions;
            }
        }
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper :getQuestionById question not found");
        return null;
    }

    @Override
    public void setDataNotifier(IDatabaseMonitor dataLoadNotifier) {
        _dataLoadNotifier = dataLoadNotifier;
    }


}
