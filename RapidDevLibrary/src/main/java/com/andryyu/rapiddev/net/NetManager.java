package com.andryyu.rapiddev.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.andryyu.rapiddev.net.callback.HttpCallBack;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * Created by yufei on 2017/12/28.
 */

public class NetManager {

    private static NetManager netManager;
    private static OkHttpClient okHttpClient;
    private final static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private NetManager() {

    }

    /**
     * <p>getInstance</p>
     *
     * @懒汉单例模式
     */
    public static NetManager getInstance() {
        if (netManager == null) {
            netManager = new NetManager();
        }
        return netManager;
    }

    /**
     * <p>initHttpClient</p>
     * @return
     * @description cookie自动管理
     */
    private static  OkHttpClient initHttpClient(){
        if(okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                            cookieStore.put(httpUrl.host(), list);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                            List<Cookie> cookies = cookieStore.get(httpUrl.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

    /**
     * <p>doRequest</p>
     *
     * @param url
     * @param callBack
     * @description get请求
     */
    public void doRequest(String url, Map<String, String> header, final HttpCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet())
                builder.header(entry.getKey(), entry.getValue());
        }
        Request request = builder.url(url).build();
        OkHttpClient mHttpClient = initHttpClient();
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

    /**
     * <p>doPost</p>
     *
     * @param url
     * @param form
     * @param callBack
     * @description post请求
     */
    public void doPost(String url, Map<String, String> form, final HttpCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        if (form != null) {
            for (Map.Entry<String, String> entry : form.entrySet())
                builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient mHttpClient = initHttpClient();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
