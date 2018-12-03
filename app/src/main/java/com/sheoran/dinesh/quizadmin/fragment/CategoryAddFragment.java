package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryAddFragment extends BaseFragment {

    private TextView _edTxtAddCategory;

    public CategoryAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_add, container, false);
        _edTxtAddCategory = view.findViewById(R.id.ed_txt_add_categ);
        Button btnAddCategory = view.findViewById(R.id.btn_add_categ);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });
        return view;
    }

    private void addCategory() {
        final String categName = _edTxtAddCategory.getText().toString();
        if (inputValidation(categName)) {
            uploadOnFirebase(categName);
            _edTxtAddCategory.setText("");
        }
    }

    private boolean inputValidation(String str) {
        if (str.isEmpty()) {
            Toast.makeText(getContext(), "Please input category name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void uploadOnFirebase(final String categName){
        final DatabaseReference reference = initFirebase(getContext(),Constants.FIREBASE_CATEGORY_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category category = new Category(categName);
                if (dataSnapshot.child(categName).exists()){
                    Toast.makeText(getContext(), "Category already exists !", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child(categName).setValue(category);
                    Toast.makeText(getContext(), "Category added successfully !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Oh ! Unable to access : "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
