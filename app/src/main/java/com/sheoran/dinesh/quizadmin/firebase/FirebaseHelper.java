package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference _databaseReference;

    public FirebaseHelper(Context context, String referenceName) {
        initFirebase(context, referenceName);
    }

    /**
     * @param context
     * @param referenceName name of pointing node/child in firebase
     */
    private void initFirebase(Context context, String referenceName) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        _databaseReference = firebaseDatabase.getReference(referenceName);
    }

    public DatabaseReference getDatabaseReference() {
        return _databaseReference;
    }

    /**
     * @param context
     * @param childName name of node which need to be remove from firebase
     * @return true if delete node succesfully from firebase
     */
    public boolean deleteNode(Context context, String childName) {
        try {
            if (_databaseReference.child(childName) != null) {
                _databaseReference.child(childName).removeValue();
                return true;
            }
            {
                Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean deleteNode(Context context,String parentName,String childName){
        try {
            if (_databaseReference.child(parentName) != null) {
                _databaseReference.child(parentName).child(childName).removeValue();
                return true;
            }
            {
                Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
        }
        return false;

    }
}
