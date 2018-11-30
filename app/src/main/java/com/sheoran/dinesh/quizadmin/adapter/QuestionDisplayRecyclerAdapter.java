package com.sheoran.dinesh.quizadmin.adapter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.model.Questions;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        Questions questions = questionsArrayList.get(i);
        final String id = questions.getId();
        String ques = questions.getQuestion();
        holder.txtId.setText(id);
        holder.txtQuestion.setText(ques);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder deleteQuestion = new AlertDialog.Builder(v.getContext());
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
                return true;
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(id);
            }
        });
    }

    private void updateQuestion(String id) {
    }

    private void deleteQuestion(String id){

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
