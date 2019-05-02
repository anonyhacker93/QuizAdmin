package com.sheoran.dinesh.quizadmin.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.databinding.RowQuestionDisplayBinding;
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
        RowQuestionDisplayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_question_display, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Questions questions = questionsArrayList.get(i);
        if (questions == null) return;

        holder.binding.setQuestionInstance(questions);

        holder.binding.cardViewContainer.setOnLongClickListener((View v) -> {
            _clickListener.onLongClickListener(questions);
            return true;
        });
        holder.binding.cardViewContainer.setOnClickListener((View v) -> {
            _clickListener.onSingleClickListener(questions);
        });
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowQuestionDisplayBinding binding;

        public MyViewHolder(@NonNull RowQuestionDisplayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
