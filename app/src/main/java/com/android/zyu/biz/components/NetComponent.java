package com.android.zyu.biz.components;

import com.android.zyu.biz.modules.NetModule;
import com.android.zyu.net.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by yufei on 2017/9/12.
 */

@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {

    ApiService getApiService();

    OkHttpClient getOkHttp();

    Retrofit getRetrofit();
}
