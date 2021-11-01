package com.uc.moviedb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.Window;

public class LoadingD {

    private Activity activity;
    private Dialog dialog;

    public LoadingD(Activity myActivity){
        this.activity = myActivity;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void stopProgress(){
        dialog.dismiss();
    }
}
