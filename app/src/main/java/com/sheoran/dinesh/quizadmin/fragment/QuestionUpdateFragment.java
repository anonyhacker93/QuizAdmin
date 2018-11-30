package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.model.Questions;

public class QuestionUpdateFragment extends Fragment {
    public final static String QUESTIONS_KEY = "Questions Key";

    private Button _btnUpdate;
    private TextView _txtQuestion;
    private TextView _txtOption1;
    private TextView _txtOption2;
    private TextView _txtOption3;
    private TextView _txtOption4;
    private Spinner _spinnerRightAnswer;

    public QuestionUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_update, container, false);
        initViews(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Questions questions = (Questions) bundle.getSerializable(QUESTIONS_KEY);
            if (questions != null) {
                setQuestion(questions);
            }
        }
        return view;
    }

    private void initViews(View view) {
        _txtQuestion = view.findViewById(R.id.edit_text_question);
        _txtOption1 = view.findViewById(R.id.option1);
        _txtOption2 = view.findViewById(R.id.option2);
        _txtOption3 = view.findViewById(R.id.option3);
        _txtOption4 = view.findViewById(R.id.option4);
        _spinnerRightAnswer = view.findViewById(R.id.correctAnsrSpinner);
        _btnUpdate = view.findViewById(R.id.updateQuestionBtn);
    }

    private void setQuestion(Questions questions) {
        if (questions != null) {
            _txtQuestion.setText(questions.getQuestion());
            _txtOption1.setText(questions.getOption1());
            _txtOption2.setText(questions.getOption2());
            _txtOption3.setText(questions.getOption3());
            _txtOption4.setText(questions.getOption4());

            int rightAnsIndex = 0;
            String rightAns =  questions.getRightAnswer();

            if(rightAns.equals(questions.getOption1())){
                rightAnsIndex = 1;
            }
            else if(rightAns.equals(questions.getOption2())){
                rightAnsIndex = 2;
            }
            if(rightAns.equals(questions.getOption3())){
                rightAnsIndex = 3;
            }
            else if(rightAns.equals(questions.getOption4())){
                rightAnsIndex = 4;
            }

            _spinnerRightAnswer.setSelection(rightAnsIndex);
        }
    }


}
