package com.sheoran.dinesh.quizadmin.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.CategoryDisplayRecyclerAdapter;
import com.sheoran.dinesh.quizadmin.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDisplayFragment extends BaseFragment implements CategoryRecyclerClickListener {
    public static final String CATEGORY_KEY = "com.sheoran.dinesh.quizadmin.fragment.CategoryDisplayFragment";
    private RecyclerView _recyclerView;
    private CategoryDisplayRecyclerAdapter _recyclerAdapter;
    private ArrayList<Category> _arrayList;

    public CategoryDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_display, container, false);
        _recyclerView = view.findViewById(R.id.categoryDisplayRecycler);
        init();
        _recyclerAdapter = new CategoryDisplayRecyclerAdapter(getContext(), this, _arrayList);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        _recyclerView.setAdapter(_recyclerAdapter);
        return view;
    }

    @Override
    public void onSingleClickListener(String str) {
        Toast.makeText(getContext(), "Edit Categ", Toast.LENGTH_SHORT).show();

        Bundle bundle =new Bundle();
        bundle.putSerializable(CATEGORY_KEY,new Category(str));
        QuestionDisplayFragment questionDisplayFragment = new QuestionDisplayFragment();
        questionDisplayFragment.setArguments(bundle);
        replaceFragment(questionDisplayFragment,R.id.home_fragment_container);
    }

    @Override
    public void onLongClickListener(String str) {
        Toast.makeText(getContext(), "Delete Categ", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        _arrayList = new ArrayList<>();
        loadCategory();
    }

    private void loadCategory() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading Data");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final DatabaseReference reference = initFirebase(getContext(), Constants.FIREBASE_CATEGORY_REF);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                Category category;
                _arrayList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    category = dataShot.getValue(Category.class);
                    _arrayList.add(category);
                }
                _recyclerAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("QuizAdminTag : ", "Exception : " + databaseError.getMessage());
                Toast.makeText(getContext(), "Exception : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
