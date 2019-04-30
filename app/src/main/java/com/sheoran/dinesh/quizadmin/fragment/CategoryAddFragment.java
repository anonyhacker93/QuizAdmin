package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.firebase.CategoryFirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryAddFragment extends BaseFragment {

    private TextView _edTxtAddCategory;
    private CategoryFirebaseHelper _categoryFirebaseHelper;

    public CategoryAddFragment() {
        _categoryFirebaseHelper = CategoryFirebaseHelper.getInstance(getContext(), (msg) -> {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_add, container, false);
        _edTxtAddCategory = view.findViewById(R.id.ed_txt_add_categ);
        Button btnAddCategory = view.findViewById(R.id.btn_add_categ);
        btnAddCategory.setOnClickListener((viewIns) -> {
            addCategory();
        });
        return view;
    }

    private void addCategory() {
        final String categName = _edTxtAddCategory.getText().toString();
        if (!categName.isEmpty()) {
            _categoryFirebaseHelper.addCategory(categName);
            _edTxtAddCategory.setText("");
        }
    }


}
