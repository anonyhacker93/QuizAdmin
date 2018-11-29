package com.sheoran.dinesh.quizadmin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;

public class QuestionAddFragment extends Fragment {
    private Button submitQuestion;
    private EditText question;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private EditText option4;
    private Spinner answerSpinner;

    public QuestionAddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_add, container, false);
        submitQuestion = (Button) view.findViewById(R.id.addQuestionBtn);
        question = (EditText) view.findViewById(R.id.edit_text_question);
        option1 = (EditText) view.findViewById(R.id.option1);
        option2 = (EditText) view.findViewById(R.id.option2);
        option3 = (EditText) view.findViewById(R.id.option3);
        option4 = (EditText) view.findViewById(R.id.option4);
        answerSpinner = (Spinner) view.findViewById(R.id.correctAnsrSpinner);
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.length() == 0) {
                    Toast.makeText(getContext(), "Please enter your question", Toast.LENGTH_LONG).show();
                } else if (option1.length() == 0 || option2.length() == 0 || option3.length() == 0 || option4.length() == 0) {
                    Toast.makeText(getContext(), "Please enter all the options", Toast.LENGTH_LONG).show();
                } else if (answerSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Enter a valid answer", Toast.LENGTH_LONG).show();
                }
                resetAllFields();
            }
        });
        return view;
    }

    private void resetAllFields() {
        question.setText(" ");
        option1.setText(" ");
        option2.setText(" ");
        option3.setText(" ");
        option4.setText(" ");
    }


}
