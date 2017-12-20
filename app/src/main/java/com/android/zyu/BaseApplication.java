package com.android.zyu;

import android.app.Application;
import android.content.Context;

import com.android.zyu.biz.components.DaggerNetComponent;
import com.android.zyu.biz.components.NetComponent;
import com.android.zyu.biz.modules.NetModule;
import com.andryyu.rapiddev.RdApplicaiton;
import com.andryyu.rapiddev.utils.AppCrashHandler;

/**
 * Created by yufei on 2017/9/12.
 */

public class BaseApplication extends RdApplicaiton {

    private NetComponent netComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        initNet();
    }

    /**
     * <p>initNet</p>
     * @Description 初始化网络请求
     */
    private void initNet(){
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public NetComponent getNetComponent(){
        return netComponent;
    }
}
