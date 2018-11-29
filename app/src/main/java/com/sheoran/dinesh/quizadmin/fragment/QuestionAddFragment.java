package com.sheoran.dinesh.quizadmin.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;

import java.util.ArrayList;
import java.util.List;

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
        addOptionsToSpinner();
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidFields();
                if (question.length() > 0 && option1.length() > 0 && option2.length() > 0 && option3.length() > 0 && option4.length() > 0
                        && !answerSpinner.getSelectedItem().equals("----Correct answer----")) {
                    saveQuestion();
                    resetAllFields();
                }
            }
        });
        return view;
    }

    private void checkValidFields() {
        boolean isQuestionBlank = false;
        boolean isOptionsBlank = false;
        boolean isAnswerNotSelected = false;
        if (question.length() == 0) {
            isQuestionBlank = true;
        } else if (option1.length() == 0 || option2.length() == 0 || option3.length() == 0 || option4.length() == 0) {
            isOptionsBlank = true;
        } else if (answerSpinner.getSelectedItem().equals("----Correct answer----")) {
            isAnswerNotSelected = true;
        }
        if (isQuestionBlank) {
            Toast.makeText(getContext(), R.string.enterQuestion, Toast.LENGTH_LONG).show();
        } else if (isOptionsBlank) {
            Toast.makeText(getContext(), R.string.enterAllOptions, Toast.LENGTH_LONG).show();
        } else if (isAnswerNotSelected) {
            Toast.makeText(getContext(), R.string.markCorrectAnswer, Toast.LENGTH_LONG).show();
        }
//        if (question.length() == 0) {
//            Toast.makeText(getContext(), "Please enter your question", Toast.LENGTH_LONG).show();
//        } else if (option1.length() == 0 || option2.length() == 0 || option3.length() == 0 || option4.length() == 0) {
//            Toast.makeText(getContext(), "Please enter all the options", Toast.LENGTH_LONG).show();
//        } else if (answerSpinner.getSelectedItem().equals("Correct answer")) {
//            Toast.makeText(getContext(), "Enter a valid answer", Toast.LENGTH_LONG).show();
//        }
    }

    private void saveQuestion() {
    }

    private void addOptionsToSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("----Correct answer----");
        list.add("Option 1");
        list.add("Option 2");
        list.add("Option 3");
        list.add("Option 4");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answerSpinner.setAdapter(dataAdapter);

//
//        String answerString = answerSpinner.getSelectedItem().toString();
//        int pos = answerSpinner.getSelectedItemPosition();
//        if(pos!=0) {
//            answer = answerSpinner.getSelectedItem().toString();
//        }
//        else{
//            Toast.makeText(getActivity(),"Please choose an answer !!", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if(!answerString.equals("Choose policy")) {
//            answer = answerSpinner.getSelectedItem().toString();
//        }
//        else{
//            Toast.makeText(getActivity(),"Please choose an answer !!", Toast.LENGTH_LONG).show();
//            return;
//        }
    }

    private void resetAllFields() {
        question.setText(" ");
        option1.setText(" ");
        option2.setText(" ");
        option3.setText(" ");
        option4.setText(" ");
        answerSpinner.setSelection(0);
    }


}
