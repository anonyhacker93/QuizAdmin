package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

public class QuestionUpdateFragment extends BaseFragment {
    public final static String QUESTIONS_KEY = "Questions Key";

    private Button _btnUpdate;
    private TextView _txtQuestion;
    private TextView _txtOption1;
    private TextView _txtOption2;
    private TextView _txtOption3;
    private TextView _txtOption4;
    private Spinner _spinnerRightAnswer;
    private String _updateQuestioId;
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
        _updateQuestioId = null;
        initViews(view);
        initFirebase(getContext(), Constants.FIREBASE_QUESTION_REF);
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
        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(_updateQuestioId);
            }
        });
    }

    private void setQuestion(Questions questions) {
        if (questions != null) {
            _txtQuestion.setText(questions.getQuestion());
            _txtOption1.setText(questions.getOption1());
            _txtOption2.setText(questions.getOption2());
            _txtOption3.setText(questions.getOption3());
            _txtOption4.setText(questions.getOption4());
            _updateQuestioId = questions.getId();
            int rightAnsIndex = 0;
            String rightAns = questions.getRightAnswer();

            if (rightAns.equals(questions.getOption1())) {
                rightAnsIndex = 1;
            } else if (rightAns.equals(questions.getOption2())) {
                rightAnsIndex = 2;
            }
            if (rightAns.equals(questions.getOption3())) {
                rightAnsIndex = 3;
            } else if (rightAns.equals(questions.getOption4())) {
                rightAnsIndex = 4;
            }

            _spinnerRightAnswer.setSelection(rightAnsIndex);
        }
    }

    public void updateQuestion(String id) {
        String ques = _txtQuestion.getText().toString();
        String option1 = _txtOption1.getText().toString();
        String option2 = _txtOption2.getText().toString();
        String option3 = _txtOption3.getText().toString();
        String option4 = _txtOption4.getText().toString();
        int rightAnsIndx = _spinnerRightAnswer.getSelectedItemPosition();
        String rightAns;
        if (rightAnsIndx == 1) {
            rightAns = option1;
        } else if (rightAnsIndx == 2) {
            rightAns = option2;
        } else if (rightAnsIndx == 3) {
            rightAns = option3;
        } else {
            rightAns = option4;
        }

        if (checkValidFields()) {
            Questions questions = new Questions(id, ques, option1, option2, option3, option4, rightAns);
            uploadOnFirebase(questions);
        }
    }

    private void uploadOnFirebase(final Questions questions) {
        firebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(questions.getId()).exists()) {
                    firebaseDatabaseReference.child(questions.getId()).setValue(questions);
                    Toast.makeText(getContext(), "Question Updated Successfully !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oh ! unable to Update !!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidFields() {
        boolean isValid = true;
        int errorMsg = -999;
        if (_txtQuestion.length() == 0) {
            isValid = false;
            errorMsg = R.string.enterQuestion;
        } else if (_txtOption1.length() == 0 || _txtOption2.length() == 0 || _txtOption3.length() == 0 || _txtOption4.length() == 0) {
            isValid = false;
            errorMsg = R.string.enterAllOptions;
        } else if (_spinnerRightAnswer.getSelectedItem().equals("----Correct answer----")) {
            isValid = false;
            errorMsg =  R.string.markCorrectAnswer;
        }
        if(errorMsg != -999){
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

}
