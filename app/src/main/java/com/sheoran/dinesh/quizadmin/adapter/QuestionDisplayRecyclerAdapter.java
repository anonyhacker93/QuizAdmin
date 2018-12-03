package com.sheoran.dinesh.quizadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.listener.QuestionRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Questions;

import java.util.ArrayList;

public class QuestionDisplayRecyclerAdapter extends RecyclerView.Adapter<QuestionDisplayRecyclerAdapter.MyViewHolder> {
    private ArrayList<Questions> questionsArrayList;
    private Context _context;
    private QuestionRecyclerClickListener _clickListener;

    public QuestionDisplayRecyclerAdapter(Context context, QuestionRecyclerClickListener clickListener, ArrayList<Questions> questions) {
        this._context = context;
        this._clickListener = clickListener;
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
        final Questions questions = questionsArrayList.get(i);
        if(questions == null) return;

        final String id = questions.getId();
        String ques = questions.getQuestion();
        holder.txtId.setText(id);
        holder.txtQuestion.setText(ques);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                _clickListener.onLongClickListener(questions);
                return true;
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clickListener.onSingleClickListener(questions);
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
