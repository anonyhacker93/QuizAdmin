package com.sheoran.dinesh.quizadmin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionAddFragment extends BaseFragment {

    private Button submitQuestion;
    private EditText question;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private EditText option4;
    private Spinner answerSpinner;
    private Spinner categorySpinner;
    private int idNo;

    public QuestionAddFragment() {
        idNo = Constants.INITIAL_ID;
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
        submitQuestion = view.findViewById(R.id.addQuestionBtn);
        question = view.findViewById(R.id.edit_text_question);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        answerSpinner = view.findViewById(R.id.correctAnsrSpinner);
        categorySpinner = view.findViewById(R.id.spinnerQuestionCateg);
        initFirebase(getContext(), Constants.FIREBASE_QUESTION_REF);
        initId();
        addOptionsToSpinner();
        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidFields()) {
                    saveQuestion();
                    resetAllFields();
                }

            }
        });

        return view;
    }

    private void initId() {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        Query lastchield =
                dbref.child(Constants.FIREBASE_QUESTION_REF).orderByKey().limitToLast(1);
        lastchield.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                Iterator<DataSnapshot> itr = ds.getChildren().iterator();
                String key = null;
                while (itr.hasNext()) {
                    DataSnapshot shot = itr.next();
                    key = shot.getKey();
                }
                if (key != null) {
                    idNo = Integer.parseInt(key);
                    idNo++;
                } else {
                    idNo = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Unable to access database !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidFields() {
        boolean isValid = true;
        int errorMsg = -999;
        if (question.length() == 0) {
            isValid = false;
            errorMsg = R.string.enterQuestion;
        } else if (option1.length() == 0 || option2.length() == 0 || option3.length() == 0 || option4.length() == 0) {
            isValid = false;
            errorMsg = R.string.enterAllOptions;
        } else if (answerSpinner.getSelectedItemPosition() == 0) {
            isValid = false;
            errorMsg =  R.string.markCorrectAnswer;
        } else  if (categorySpinner.getSelectedItemPosition() == 0){
            isValid = false;
            errorMsg =  R.string.enterCategory;
        }
        if(errorMsg != -999){
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void saveQuestion() {
        idNo++;
        String id = Integer.toString(idNo);
        String ques = question.getText().toString();
        String opt1 = option1.getText().toString();
        String opt2 = option2.getText().toString();
        String opt3 = option3.getText().toString();
        String opt4 = option4.getText().toString();
        int indx = answerSpinner.getSelectedItemPosition();
        String ans = null;

        if (indx == 1) {
            ans = opt1;
        } else if (indx == 2) {
            ans = opt2;
        } else if (indx == 3) {
            ans = opt3;
        } else if (indx == 4) {
            ans = opt4;
        }

        Questions questions = new Questions(id, ques, opt1, opt2, opt3, opt4, ans);
        uploadOnFirebase(questions);
    }

    private void addOptionsToSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("Answer");
        list.add("Option 1");
        list.add("Option 2");
        list.add("Option 3");
        list.add("Option 4");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answerSpinner.setAdapter(dataAdapter);

    }

    private void resetAllFields() {
        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        answerSpinner.setSelection(0);
    }

    private void uploadOnFirebase(final Questions questions) {
        firebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(questions.getId()).exists()) {
                    Toast.makeText(getContext(), "This question id is already existed!", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseDatabaseReference.child(questions.getId()).setValue(questions);
                    Toast.makeText(getContext(), "Question Added Successfully !!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
