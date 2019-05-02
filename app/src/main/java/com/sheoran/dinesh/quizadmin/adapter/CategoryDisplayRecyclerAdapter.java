package com.sheoran.dinesh.quizadmin.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.databinding.RowCategoryDisplayBinding;
import com.sheoran.dinesh.quizadmin.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Category;

import java.util.ArrayList;


public class CategoryDisplayRecyclerAdapter extends RecyclerView.Adapter<CategoryDisplayRecyclerAdapter.MyViewHolder> {
    private ArrayList<Category> categoryArrayList;
    private Context _context;
    private CategoryRecyclerClickListener _clickListener;

    public CategoryDisplayRecyclerAdapter(Context context, CategoryRecyclerClickListener clickListener, ArrayList<Category> categories) {
        this._context = context;
        this._clickListener = clickListener;
        this.categoryArrayList = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowCategoryDisplayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_category_display, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Category category = categoryArrayList.get(i);
        if (category == null) return;

        holder.binding.setCategoryInstance(category);

        holder.binding.cardViewContainer.setOnLongClickListener((v) -> {
            _clickListener.onLongClickListener(holder.binding.txtCategory.getText().toString());
            return true;
        });

        holder.binding.cardViewContainer.setOnClickListener((v) -> {
            _clickListener.onSingleClickListener(holder.binding.txtCategory.getText().toString());
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowCategoryDisplayBinding binding;

        public MyViewHolder(@NonNull RowCategoryDisplayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
