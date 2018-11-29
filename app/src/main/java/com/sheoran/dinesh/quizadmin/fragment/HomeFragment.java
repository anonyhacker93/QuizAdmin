package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sheoran.dinesh.quizadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnAddQuestion = view.findViewById(R.id.btn_home_add_question);
        Button btnUpdateQuestion = view.findViewById(R.id.btn_home_update_question);
        Button btnDeleteQuestion = view.findViewById(R.id.btn_home_delete_question);
        Button btnDisplayQuestion = view.findViewById(R.id.btn_home_display_question);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new QuestionAddFragment());
            }
        });

        btnUpdateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new QuestionUpdateFragment());
            }
        });

        btnDisplayQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new QuestionDisplayFragment());
            }
        });
        return view;
    }
    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
