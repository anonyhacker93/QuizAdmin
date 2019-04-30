package com.sheoran.dinesh.quizadmin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.sheoran.dinesh.quizadmin.R;
import com.sheoran.dinesh.quizadmin.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        //Initial default Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.add(R.id.home_fragment_container, homeFragment, "HomeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
        if (homeFragment != null && !homeFragment.isVisible())
            super.onBackPressed();
        else
            finish();
    }

}
