package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
  private DatabaseReference _databaseReference;

    public FirebaseHelper(Context context,String referenceName){
        initFirebase(context,referenceName);
    }

    private void initFirebase(Context context,String referenceName) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        _databaseReference = firebaseDatabase.getReference(referenceName);
    }

    public DatabaseReference getDatabaseReference(){
        return _databaseReference;
    }
}
