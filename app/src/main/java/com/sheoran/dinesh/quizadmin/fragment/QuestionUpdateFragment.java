package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheoran.dinesh.quizadmin.R;

public class QuestionUpdateFragment extends Fragment {

    public QuestionUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_update, container, false);
        return view;
    }

}
