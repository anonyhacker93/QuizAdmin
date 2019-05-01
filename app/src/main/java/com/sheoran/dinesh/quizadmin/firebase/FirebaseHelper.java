package com.sheoran.dinesh.quizadmin.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sheoran.dinesh.quizadmin.util.Constants;

abstract public class FirebaseHelper {
    protected DatabaseReference _databaseReference;
    protected IDatabaseMonitor iDatabaseMonitor;

    public FirebaseHelper(Context context, String referenceName) {
        initFirebase(context, referenceName);
    }

    private void initFirebase(Context context, String referenceName) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        _databaseReference = firebaseDatabase.getReference(referenceName);
        Log.d(Constants.LOG_TAG,"FirebaseHelper : deleteNode");
    }

    protected DatabaseReference getDatabaseReference(Context context, String referenceName) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference(referenceName);
    }


    public boolean deleteNode(String childName) {
        Log.d(Constants.LOG_TAG,"FirebaseHelper : deleteNode");
        try {
            if (_databaseReference.child(childName) != null) {
                _databaseReference.child(childName).removeValue();
                if (iDatabaseMonitor != null) {
                    iDatabaseMonitor.onDatabaseChange(true,"Deleted successfully !");
                }
                return true;
            }
        } catch (Exception ex) {
            if (iDatabaseMonitor != null) {
                iDatabaseMonitor.onDatabaseChange(false,"Unable to delete");
            }
        }
        return false;
    }


    abstract public void setDataNotifier(IDatabaseMonitor dataLoadNotifier);

    public interface IDatabaseMonitor {
        void onDatabaseChange(boolean isSuccess,String msg);
    }
}
