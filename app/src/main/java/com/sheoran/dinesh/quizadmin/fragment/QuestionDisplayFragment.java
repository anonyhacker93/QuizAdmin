package com.sheoran.dinesh.quizadmin.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.QuestionDisplayRecyclerAdapter;
import com.sheoran.dinesh.quizadmin.firebase.FirebaseHelper;
import com.sheoran.dinesh.quizadmin.listener.CustomRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Questions;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment implements CustomRecyclerClickListener {
    public final static String QUESTIONS_KEY = "Questions Key";
    private RecyclerView recyclerView;
    private ArrayList<Questions> questionArrayList;
    private FirebaseHelper _firebaseHelper;
    public final static String FIREBASE_REF = "Question";

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }

    private QuestionDisplayRecyclerAdapter _adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionArrayList = new ArrayList<>();
        _firebaseHelper = new FirebaseHelper(getContext(), FIREBASE_REF);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_display, container, false);
        recyclerView = view.findViewById(R.id.questionDisplayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadQuestion();
        _adapter = new QuestionDisplayRecyclerAdapter(getContext(), this, questionArrayList);
        recyclerView.setAdapter(_adapter);
        return view;
    }

    private void loadQuestion() {
        Questions questions = new Questions();
        questions.setId("300");
        questions.setQuestion("What is ur name");

        Questions questions1 = new Questions();
        questions1.setId("301");
        questions1.setQuestion("What is ur mom name");

        Questions questions2 = new Questions();
        questions2.setId("302");
        questions2.setQuestion("What is ur father name What is ur mom name What is ur mom name What is ur mom name");

        Questions questions3 = new Questions();
        questions3.setId("303");
        questions3.setQuestion("What is ur gf name");

        questionArrayList.add(questions);
        questionArrayList.add(questions1);
        questionArrayList.add(questions2);
        questionArrayList.add(questions3);
    }

    //Recycler Click Listener
    @Override
    public void onSingleClickListener(String id) {
        updateQuestion(id);
    }

    @Override
    public void onLongClickListener(String id) {
        showDeleteConfirmationDialog(id);
    }

    /**
     * It will open QuestionUpdateFragment to update the question content
     *
     * @param id indicating question id
     */
    private void updateQuestion(String id) {
        Toast.makeText(getContext(), "Question clicked for update " + id, Toast.LENGTH_SHORT).show();
        QuestionUpdateFragment updateFragment = new QuestionUpdateFragment();
        Bundle bundle = new Bundle();
        Questions question = new Questions();
        bundle.putSerializable(QUESTIONS_KEY, question);
        updateFragment.setArguments(bundle);

    }

    /**
     * It will delete the question from Firebase and Questionlist
     *
     * @param id indicating question id
     */
    private void deleteQuestion(String id) {

        boolean isDeleted = _firebaseHelper.deleteNode(getContext(), id); //Delete item from firebase
        if (isDeleted) {
            Toast.makeText(getContext(), "Question clicked for delete " + id, Toast.LENGTH_SHORT).show();
            ListIterator<Questions> itr = questionArrayList.listIterator();
            while (itr.hasNext()) {
                Questions questions = itr.next();
                if (id.equals(questions.getId())) {
                    itr.remove();
                }
            }
            _adapter.notifyDataSetChanged();
        }

    }

    private void showDeleteConfirmationDialog(final String id) {
        AlertDialog.Builder deleteQuestion = new AlertDialog.Builder(getContext());
        deleteQuestion.setMessage("Do you want to delete this question?");
        deleteQuestion.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteQuestion(id);

            }
        });
        deleteQuestion.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteQuestion.show();
    }
}