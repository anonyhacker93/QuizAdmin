package com.sheoran.dinesh.quizadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import com.sheoran.dinesh.quizadmin.R;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category_display, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Category category = categoryArrayList.get(i);
        if(category == null) return;

        final String categName = category.getCategoryName();
        holder.txtCateg.setText(categName);
        holder.cardViewContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                _clickListener.onLongClickListener(categName);
                return true;
            }
        });
        holder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clickListener.onSingleClickListener(categName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCateg;
        public CardView cardViewContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCateg = itemView.findViewById(R.id.txt_category);
            cardViewContainer = itemView.findViewById(R.id.cardViewContainer);
        }
    }
}
