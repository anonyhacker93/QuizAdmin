package com.sheoran.dinesh.quizadmin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.Questions;
import com.sheoran.dinesh.quizadmin.R;

import java.util.ArrayList;

public class QuestionDisplayRecyclerAdapter extends RecyclerView.Adapter<QuestionDisplayRecyclerAdapter.MyViewHolder> {
    ArrayList<Questions> questionsArrayList;

    public QuestionDisplayRecyclerAdapter(ArrayList<Questions> questions) {
        this.questionsArrayList = questions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_question_display, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Questions questions = questionsArrayList.get(i);
        final String id = questions.getId();
        String ques = questions.getQuestion();
        holder.txtId.setText(id);
        holder.txtQuestion.setText(ques);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Do u want to delete/edit "+id, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtQuestion;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            linearLayout = itemView.findViewById(R.id.display_linear_layout);
        }
    }
}
