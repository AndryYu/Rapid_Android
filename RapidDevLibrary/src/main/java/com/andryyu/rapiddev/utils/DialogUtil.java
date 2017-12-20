package com.andryyu.rapiddev.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.andryyu.rapiddev.RdApplicaiton;


/**
 * Created by yufei on 2017/9/17.
 */

public class DialogUtil {

    private static ProgressDialog mDialog;
    /**
     * <p>showInThread</p>
     * @param context
     * @param message
     * @Description:   线程中调用Toast
     */
    public static void showInThread(final Context context, final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    public static void showShort(String msg) {
        Toast.makeText(RdApplicaiton.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(RdApplicaiton.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showLoad(Context context,String msg){
        mDialog = new ProgressDialog(context);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage(msg);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public static void unShowLoad(){
        mDialog.dismiss();
    }

    public static void showDialog(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
