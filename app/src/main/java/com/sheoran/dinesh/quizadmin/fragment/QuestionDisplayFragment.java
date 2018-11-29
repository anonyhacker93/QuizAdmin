package com.sheoran.dinesh.quizadmin.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheoran.dinesh.quizadmin.Questions;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.QuestionDisplayRecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Questions> questionArrayList;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_display, container, false);
        recyclerView = view.findViewById(R.id.questionDisplayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadQuestion();
        QuestionDisplayRecyclerAdapter adapter = new QuestionDisplayRecyclerAdapter(questionArrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadQuestion(){
        Questions questions = new Questions();
        questions.setId("300");
        questions.setQuestion("What is ur name");

        Questions questions1 = new Questions();
        questions.setId("301");
        questions.setQuestion("What is ur mom name");

        Questions questions2 = new Questions();
        questions.setId("302");
        questions.setQuestion("What is ur father name");

        Questions questions3 = new Questions();
        questions.setId("303");
        questions.setQuestion("What is ur gf name");

        questionArrayList.add(questions);
        questionArrayList.add(questions1);
        questionArrayList.add(questions2);
        questionArrayList.add(questions3);
    }

}
