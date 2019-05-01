package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sheoran.dinesh.quizadmin.R;

public class HomeFragment extends BaseFragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnAddQuestion = view.findViewById(R.id.btn_home_add_question);
        Button btnAddCategory = view.findViewById(R.id.btn_home_add_category);
        Button btnShowCategory = view.findViewById(R.id.btn_home_show_category);

        btnAddQuestion.setOnClickListener((v) -> {
            replaceFragment(new QuestionAddFragment(), R.id.home_fragment_container);

        });

        btnAddCategory.setOnClickListener((v) -> {
            replaceFragment(new CategoryAddFragment(), R.id.home_fragment_container);
        });

        btnShowCategory.setOnClickListener((v) -> {
            replaceFragment(new CategoryDisplayFragment(), R.id.home_fragment_container);
        });
        return view;
    }

}
