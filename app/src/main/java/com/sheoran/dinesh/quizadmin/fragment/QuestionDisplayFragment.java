package com.sheoran.dinesh.quizadmin.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.QuestionDisplayRecyclerAdapter;
import com.sheoran.dinesh.quizadmin.firebase.FirebaseHelper;
import com.sheoran.dinesh.quizadmin.listener.CustomRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Questions;
import com.sheoran.dinesh.quizadmin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends BaseFragment implements CustomRecyclerClickListener {
    public final static String QUESTIONS_KEY = "Questions Key";
    private RecyclerView recyclerView;
    private ArrayList<Questions> questionArrayList;
    private FirebaseHelper _firebaseHelper;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }

    private QuestionDisplayRecyclerAdapter _adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _firebaseHelper = new FirebaseHelper(getContext(), Constants.FIREBASE_QUESTION_REF);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_display, container, false);
        recyclerView = view.findViewById(R.id.questionDisplayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        questionArrayList = new ArrayList<>();
        initFirebase(getContext(), Constants.FIREBASE_QUESTION_REF);
        loadQuestion();
        _adapter = new QuestionDisplayRecyclerAdapter(getContext(), this, questionArrayList);
        recyclerView.setAdapter(_adapter);
        return view;
    }

    private void loadQuestion() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading Data");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                Questions questions;
                questionArrayList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    questions = dataShot.getValue(Questions.class);
                    questionArrayList.add(questions);
                }
                _adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("QuizAdminTag : ","Exception : "+databaseError.getMessage());
                Toast.makeText(getContext(), "Exception : "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


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
        Questions question = getQuestionFromList(id);
        bundle.putSerializable(QUESTIONS_KEY, question);
        updateFragment.setArguments(bundle);
        replaceFragment(updateFragment, R.id.home_fragment_container);
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
           // _adapter.notifyDataSetChanged();
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

    private Questions getQuestionFromList(final String id) {
        ListIterator<Questions> itr = questionArrayList.listIterator();
        while (itr.hasNext()) {
            Questions questions = itr.next();
            if (id.equals(questions.getId())) {
                return questions;
            }
        }
        return null;
    }


}
