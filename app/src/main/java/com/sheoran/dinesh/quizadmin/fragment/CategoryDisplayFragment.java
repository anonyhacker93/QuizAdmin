package com.sheoran.dinesh.quizadmin.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sheoran.dinesh.quizadmin.FirebaseInstanceManager;
import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.adapter.CategoryDisplayRecyclerAdapter;
import com.sheoran.dinesh.quizadmin.databinding.FragmentCategoryDisplayBinding;
import com.sheoran.dinesh.quizadmin.firebase.CategoryFirebaseHelper;
import com.sheoran.dinesh.quizadmin.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.quizadmin.model.Category;
import com.sheoran.dinesh.quizadmin.util.Constants;
import com.sheoran.dinesh.quizadmin.util.ProgressDialogUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDisplayFragment extends BaseFragment implements CategoryRecyclerClickListener {
    public static final String CATEGORY_KEY = "com.sheoran.dinesh.quizadmin.fragment.CategoryDisplayFragment";
    private RecyclerView _recyclerView;
    private CategoryDisplayRecyclerAdapter _recyclerAdapter;
    private ArrayList<Category> _categoryArrayList;
    private CategoryFirebaseHelper _categoryFirebaseHelper;

    private ProgressDialog _progressDialog;

    public CategoryDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCategoryDisplayBinding fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_display, container, false);
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: onCreateView ");
        _recyclerView = fragmentBinding.categoryDisplayRecycler;

        init();

        _recyclerAdapter = new CategoryDisplayRecyclerAdapter(getContext(), this, _categoryArrayList);
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        _recyclerView.setAdapter(_recyclerAdapter);

        return fragmentBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _categoryFirebaseHelper.setDataNotifier(null);
    }

    private void init() {
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: init ");
        _categoryArrayList = new ArrayList<>();
        _progressDialog = ProgressDialogUtil.getProgressDialog(getContext(), "Loading", "Please wait");
        _categoryFirebaseHelper = firebaseInstanceManager.getCategoryFirebaseHelper();

        _categoryFirebaseHelper.requestLoadCategory();
        _categoryFirebaseHelper.setDataNotifier((isSuccess,msg) -> {
            if (isSuccess) {
            Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: init : setDataNotifier ");
                loadCategory();

            } else {
               // Toast.makeText(getContext(), "Unable to load categories", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(CategoryDisplayFragment.this.getContext(),""+msg, Toast.LENGTH_SHORT).show();

            _progressDialog.dismiss();

        });

    }

    @Override
    public void onSingleClickListener(String str) {
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: onSingleClickListener ");
        Bundle bundle = new Bundle();
        bundle.putSerializable(CATEGORY_KEY, new Category(str));
        QuestionDisplayFragment questionDisplayFragment = new QuestionDisplayFragment();
        questionDisplayFragment.setArguments(bundle);
        replaceFragment(questionDisplayFragment, R.id.home_fragment_container);
    }

    //testing git
    @Override
    public void onLongClickListener(String str) {
        deleteCategoryDialog(new Category(str));
    }

    private void deleteCategoryDialog(final Category category) {
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: deleteCategoryDialog ");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(getResources().getString(R.string.deleteSelectedCategory) + " " + category.getCategoryName() + "?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCategory(category);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void deleteCategory(final Category category) {
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: deleteCategory ");
        _categoryFirebaseHelper.deleteCategory(category);


       /* if (isDeleted) {
            String id = deleteCategory.getCategoryName();
            ListIterator<Category> itr = _categoryArrayList.listIterator();
            while (itr.hasNext()) {
                Category category = itr.next();
                if (id.equals(category.getCategoryName())) {
                    itr.remove();
                }
            }
        }*/

    }


    private void loadCategory() {
        Log.d(Constants.LOG_TAG,"CategoryDisplayFragment :: loadCategory ");
        ArrayList<Category> categoryArrayList = _categoryFirebaseHelper.getCategoryList();
        _categoryArrayList.clear();
        _categoryArrayList.addAll(categoryArrayList);
        _recyclerAdapter.notifyDataSetChanged();
    }
}
