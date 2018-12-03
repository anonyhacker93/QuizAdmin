package com.sheoran.dinesh.quizadmin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.google.firebase.database.DatabaseReference;
import com.sheoran.dinesh.quizadmin.firebase.FirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected DatabaseReference initFirebase(Context context, String databaseReference) {
        FirebaseHelper firebaseHelper = new FirebaseHelper(context,databaseReference);
        DatabaseReference firebaseDatabaseReference = firebaseHelper.getDatabaseReference();
        return firebaseDatabaseReference;
    }

    protected void replaceFragment(Fragment fragment,int container) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
