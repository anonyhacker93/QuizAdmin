package com.sheoran.dinesh.quizadmin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.Questions;
import com.sheoran.dinesh.quizadmin.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionAddFragment extends Fragment {
    private final static String BASE_CHILD_NAME = "Question";
    private Button submitQuestion;
    private EditText question;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private EditText option4;
    private Spinner answerSpinner;
    private DatabaseReference usersReference;
    private int idNo = 200;

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
        initFirebase();
        initId();
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


    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Question");


    }

    private void initId() {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        Query lastchield =
                dbref.child(BASE_CHILD_NAME).orderByKey().limitToLast(1);
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
                }
                {
                    idNo = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        answerSpinner.setSelection(0);
    }

    private void uploadOnFirebase(final Questions questions) {
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(questions.getId()).exists()) {
                    Toast.makeText(getContext(), "This question id is already existed!", Toast.LENGTH_SHORT).show();
                } else {
                    usersReference.child(questions.getId()).setValue(questions);
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
