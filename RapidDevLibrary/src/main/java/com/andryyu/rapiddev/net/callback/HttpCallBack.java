package com.andryyu.rapiddev.net.callback;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018\2\26 0026.
 */

public interface HttpCallBack {

    void SuccessCallBack(Bitmap bmp);

    void FailureCallBack(String code,String error);
}
