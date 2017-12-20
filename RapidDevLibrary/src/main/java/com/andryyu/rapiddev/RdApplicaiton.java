package com.andryyu.rapiddev;

import android.app.Application;
import android.content.Context;

import com.andryyu.rapiddev.utils.AppCrashHandler;

/**
 * Created by Administrator on 2017\11\27 0027.
 */

public class RdApplicaiton extends Application{

    private static Context context;
    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        AppCrashHandler.getInstance().setCrashHandler(this);
    }
}
