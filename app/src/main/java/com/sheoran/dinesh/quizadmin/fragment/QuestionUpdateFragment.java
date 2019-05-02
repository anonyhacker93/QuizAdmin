package com.sheoran.dinesh.quizadmin.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.databinding.FragmentQuestionUpdateBinding;
import com.sheoran.dinesh.quizadmin.firebase.QuestionFirebaseHelper;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

public class QuestionUpdateFragment extends BaseFragment {
    public final static String QUESTIONS_KEY = "Questions Key";
    public QuestionFirebaseHelper questionFirebaseHelper;

    private Spinner _spinnerRightAnswer;


    public QuestionUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionFirebaseHelper = firebaseInstanceManager.getQuestionFirebaseHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentQuestionUpdateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_update, container, false);

        _spinnerRightAnswer = binding.correctAnsrSpinner;

        questionFirebaseHelper.setDataNotifier((isSuccess, msg) -> {
                    if (isSuccess) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        if (getFragmentManager().getBackStackEntryCount() > 0) {
                            getFragmentManager().popBackStackImmediate();
                        }
                    }

                }

        );
        Bundle bundle = getArguments();
        if (bundle != null) {
            Questions questions = (Questions) bundle.getSerializable(QUESTIONS_KEY);
            if (questions != null) {
                binding.setQuestionInstance(questions);
                setAnswerSpinner(questions);

                binding.updateQuestionBtn.setOnClickListener((v -> {
                    updateQuestion(binding);
                }));
            }
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        questionFirebaseHelper.setDataNotifier(null);
    }

    private void setAnswerSpinner(Questions questions) {
        if (questions != null) {
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

    public void updateQuestion(FragmentQuestionUpdateBinding binding) {
        Log.d(Constants.LOG_TAG, "QuestionUpdateFragment : updateQuestion ");

        Questions question = binding.getQuestionInstance();

        int rightAnsIndx = _spinnerRightAnswer.getSelectedItemPosition();


        if(!fieldsValidator(binding)){
            return;
        }
        question.setId(question.getId());
        question.setQuestion(binding.editTextQuestion.getText().toString());
        question.setOption1(binding.option1.getText().toString());
        question.setOption2(binding.option2.getText().toString());
        question.setOption3(binding.option3.getText().toString());
        question.setOption4(binding.option4.getText().toString());

        String rightAns;
        if (rightAnsIndx == 1) {
            rightAns = question.getOption1();

        } else if (rightAnsIndx == 2) {
            rightAns = question.getOption2();

        } else if (rightAnsIndx == 3) {
            rightAns = question.getOption3();

        } else {
            rightAns = question.getOption4();

        }
        question.setRightAnswer(rightAns);

        questionFirebaseHelper.updateQuestion(question);
        //  }
    }

    private boolean fieldsValidator(FragmentQuestionUpdateBinding binding) {
        if (binding.editTextQuestion.getText().toString().isEmpty() || binding.option1.getText().toString().isEmpty()
                || binding.option2.getText().toString().isEmpty() || binding.option3.getText().toString().isEmpty()
                || binding.option4.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.correctAnsrSpinner.getSelectedItemPosition() == 0){
            Toast.makeText(getContext(), "Please select answer", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
