package com.sheoran.dinesh.quizadmin.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

// Created by Dinesh Kumar on 5/1/2019
public class ProgressDialogUtil {


    public static ProgressDialog getProgressDialog(Context context,String title, String msg){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }
}
