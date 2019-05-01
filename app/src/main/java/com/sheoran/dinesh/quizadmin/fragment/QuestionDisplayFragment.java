package com.sheoran.dinesh.quizadmin.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.QuestionDisplayRecyclerAdapter;
import com.sheoran.dinesh.quizadmin.firebase.QuestionFirebaseHelper;
import com.sheoran.dinesh.quizadmin.listener.QuestionRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;
import com.sheoran.dinesh.quizadmin.util.ProgressDialogUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends BaseFragment implements QuestionRecyclerClickListener {
    public final static String QUESTIONS_KEY = "Questions Key";
    private RecyclerView recyclerView;
    private ArrayList<Questions> questionArrayList = new ArrayList<>();
    private QuestionFirebaseHelper _questionFirebaseHelper;
    ProgressDialog _loadDataProgressDialog;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }

    private QuestionDisplayRecyclerAdapter _adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_display, container, false);
        Log.d(Constants.LOG_TAG, "QuestionDisplayFragment : onCreateView ");
        recyclerView = view.findViewById(R.id.questionDisplayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        questionArrayList = new ArrayList<>();

        Category category = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            category = (Category) bundle.getSerializable(CategoryDisplayFragment.CATEGORY_KEY);
        }

        init();
        if (category != null) {
            loadQuestion(category.getCategoryName());
        }
        return view;
    }

    private void init() {
        Log.d(Constants.LOG_TAG, "QuestionDisplayFragment : init ");

        _loadDataProgressDialog = ProgressDialogUtil.getProgressDialog(getContext(), "Please wait", "Loading...");
        _questionFirebaseHelper = firebaseInstanceManager.getQuestionFirebaseHelper();

        setRecyclerViewAdapter();

        _questionFirebaseHelper.setDataNotifier((isSuccess,msg) -> {
            if (isSuccess) {
                ArrayList<Questions> questionsList = _questionFirebaseHelper.getAllQuestionsList();
                questionArrayList.clear();
                questionArrayList.addAll(questionsList);
                _adapter.notifyDataSetChanged();
                _loadDataProgressDialog.dismiss();
                Log.d(Constants.LOG_TAG, "QuestionDisplayFragment init questions loaded");
            } else {
                Log.d(Constants.LOG_TAG, "QuestionDisplayFragment init unable to load questions");
            }
            _loadDataProgressDialog.dismiss();
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _questionFirebaseHelper.setDataNotifier(null);
    }

    private void setRecyclerViewAdapter() {
        _adapter = new QuestionDisplayRecyclerAdapter(getContext(), this, questionArrayList);
        recyclerView.setAdapter(_adapter);
    }

    private void loadQuestion(String categName) {
        Log.d(Constants.LOG_TAG, "QuestionDisplayFragment : loadQuestion ");

        _loadDataProgressDialog.show();
        _questionFirebaseHelper.requestQuestionByCategoryName(categName);
    }

    //Recycler Click Listener
    @Override
    public void onSingleClickListener(Questions questions) {
        updateQuestion(questions);
    }

    @Override
    public void onLongClickListener(Questions id) {
        showDeleteConfirmationDialog(id);
    }

    private void updateQuestion(Questions questions) {
        Log.d(Constants.LOG_TAG, "QuestionDisplayFragment : updateQuestion ");

        QuestionUpdateFragment updateFragment = new QuestionUpdateFragment();

        Bundle bundle = new Bundle();
        Questions question = _questionFirebaseHelper.getQuestionById(questions.getId());
        bundle.putSerializable(QUESTIONS_KEY, question);
        updateFragment.setArguments(bundle);

        replaceFragment(updateFragment, R.id.home_fragment_container);
    }

    private void deleteQuestion(final Questions questions) {
        boolean isDeleted = _questionFirebaseHelper.deleteQuestion(questions);
        Log.d(Constants.LOG_TAG, " QuestionDisplayFragment : deleteQuestion : Question Deleted " + isDeleted);
    }

    private void showDeleteConfirmationDialog(final Questions questions) {
        Log.d(Constants.LOG_TAG, "QuestionDisplayFragment : showDeleteConfirmationDialog ");

        AlertDialog.Builder deleteQuestionDialog = new AlertDialog.Builder(getContext());
        deleteQuestionDialog.setMessage(getResources().getString(R.string.deleteSelectedQuestion));
        deleteQuestionDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteQuestion(questions);
            }
        });

        deleteQuestionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        deleteQuestionDialog.show();
    }


}
