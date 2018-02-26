package com.andryyu.rapiddev.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.andryyu.rapiddev.net.callback.HttpCallBack;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * Created by yufei on 2017/12/28.
 */

public class NetManager {

    private static NetManager netManager;
    private Platform mPlatform;

    private NetManager() {

    }

    /**
     * <p>getInstance</p>
     * @懒汉单例模式
     */
    public static NetManager getInstance() {
        if (netManager == null) {
            netManager = new NetManager();
        }
        return netManager;
    }

    public void doRequest(String url, final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient mHttpClient = new OkHttpClient();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              //callBack.FailureCallBack();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取流
                InputStream in = response.body().byteStream();
                //转化为bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                callBack.SuccessCallBack(bitmap);
            }
        });
    }
}
