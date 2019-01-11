package com.sheoran.dinesh.quizadmin.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
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
import com.sheoran.dinesh.quizadmin.firebase.FirebaseHelper;
import com.sheoran.dinesh.quizadmin.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDisplayFragment extends BaseFragment implements CategoryRecyclerClickListener {
    public static final String CATEGORY_KEY = "com.sheoran.dinesh.quizadmin.fragment.CategoryDisplayFragment";
    private RecyclerView _recyclerView;
    private CategoryDisplayRecyclerAdapter _recyclerAdapter;
    private ArrayList<Category> _arrayList;
    private FirebaseHelper _firebaseHelper;

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
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
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
   //testing git
    @Override
    public void onLongClickListener(String str) {
        deleteCategoryDialog(new Category(str));
    }

    private void deleteCategoryDialog(final Category category) {
        AlertDialog.Builder deleteCategory = new AlertDialog.Builder(getContext());
        deleteCategory.setMessage(getResources().getString(R.string.deleteSelectedCategory) +" "+category.getCategoryName()+ "?");
        deleteCategory.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCategory(category);
            }
        });
        deleteCategory.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteCategory.show();
    }

    private void deleteCategory(final Category deleteCategory) {
        FirebaseHelper ref1 = new FirebaseHelper(getContext(),Constants.FIREBASE_CATEGORY_REF);
        boolean isDeleted = ref1.deleteNode(getContext(),deleteCategory.getCategoryName()); //Delete item from firebase
        FirebaseHelper ref2 =new FirebaseHelper(getContext(),Constants.FIREBASE_QUESTION_REF);
        ref2.deleteNode(getContext(),deleteCategory.getCategoryName());

       /* if (isDeleted) {
            String id = deleteCategory.getCategoryName();
            ListIterator<Category> itr = _arrayList.listIterator();
            while (itr.hasNext()) {
                Category category = itr.next();
                if (id.equals(category.getCategoryName())) {
                    itr.remove();
                }
            }
        }*/

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
