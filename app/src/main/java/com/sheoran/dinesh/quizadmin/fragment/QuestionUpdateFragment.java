package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.model.Questions;

public class QuestionUpdateFragment extends Fragment {
    public final static String QUESTIONS_KEY = "Questions Key";

    public QuestionUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Questions questions = (Questions) bundle.getSerializable(QUESTIONS_KEY);
            if (questions != null) {
                updateQuestion(questions);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_update, container, false);
        return view;
    }

    private void updateQuestion(Questions questions) {
        if (questions != null) {

        }
    }


}
