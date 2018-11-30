package com.sheoran.dinesh.quizadmin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.firebase.FirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected DatabaseReference firebaseDatabaseReference;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init(){
    }

    protected void initFirebase(Context context, String databaseReference) {
        FirebaseHelper firebaseHelper = new FirebaseHelper(context,databaseReference);
        firebaseDatabaseReference = firebaseHelper.getDatabaseReference();
    }
}
