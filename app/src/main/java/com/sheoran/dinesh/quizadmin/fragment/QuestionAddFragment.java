package com.sheoran.dinesh.quizadmin.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.databinding.FragmentQuestionAddBinding;
import com.sheoran.dinesh.quizadmin.firebase.CategoryFirebaseHelper;
import com.sheoran.dinesh.quizadmin.firebase.QuestionFirebaseHelper;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;
import com.sheoran.dinesh.quizadmin.util.ProgressDialogUtil;

import java.util.ArrayList;
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
    private QuestionFirebaseHelper _questionFirebaseHelper;
    private CategoryFirebaseHelper _categoryFirebaseHelper;
    private ProgressDialog _progressDialog;

    public QuestionAddFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentQuestionAddBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_add, container, false);

        submitQuestion = binding.addQuestionBtn;
        question = binding.editTextQuestion;
        option1 = binding.option1;
        option2 = binding.option2;
        option3 = binding.option3;
        option4 = binding.option4;

        answerSpinner = binding.correctAnsrSpinner;
        categorySpinner = binding.spinnerQuestionCateg;

        init();

        submitQuestion.setOnClickListener((View v) -> {
            if (checkValidFields()) {
                saveQuestion();

                resetAllFields();
            }
        });

        return binding.getRoot();
    }


    private void init() {
        Log.d(Constants.LOG_TAG,"QuestionAddFragment init");
        _progressDialog = ProgressDialogUtil.getProgressDialog(getContext(), "Please wait", "Loading");
        _progressDialog.show();

        _questionFirebaseHelper = firebaseInstanceManager.getQuestionFirebaseHelper();
        _categoryFirebaseHelper = firebaseInstanceManager.getCategoryFirebaseHelper();

        _categoryFirebaseHelper.requestLoadCategory();

        _categoryFirebaseHelper.setDataNotifier((isSuccess,msg) -> {
            if (isSuccess) {
                addCategoryToSpinner();
            } else {
                Toast.makeText(QuestionAddFragment.this.getContext(), "Unable to load category", Toast.LENGTH_SHORT).show();
            }
            _progressDialog.dismiss();
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });


        _questionFirebaseHelper.incrementId();

        addQuestionOptionsToSpinner();
    }

    private void addCategoryToSpinner() {
        List<String> categoryNamesList = _categoryFirebaseHelper.getCategoryNames();

        ArrayAdapter<String> categoryDropdownAdapter;

        categoryDropdownAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryNamesList);
        categoryDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryDropdownAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _categoryFirebaseHelper.setDataNotifier(null);
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
            errorMsg = R.string.markCorrectAnswer;

        } else if (categorySpinner.getSelectedItemPosition() == 0) {
            isValid = false;
            errorMsg = R.string.enterCategory;

        }
        if (errorMsg != -999) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void saveQuestion() {
        String ques = question.getText().toString();
        String opt1 = option1.getText().toString();
        String opt2 = option2.getText().toString();
        String opt3 = option3.getText().toString();
        String opt4 = option4.getText().toString();
        int indx = answerSpinner.getSelectedItemPosition();
        String categoryName = (String) categorySpinner.getSelectedItem();

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

        Questions questions = new Questions(ques, opt1, opt2, opt3, opt4, ans, categoryName);
        _questionFirebaseHelper.addQuestion(questions);
    }

    private void addQuestionOptionsToSpinner() {
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
        categorySpinner.setSelection(0);
    }


}
