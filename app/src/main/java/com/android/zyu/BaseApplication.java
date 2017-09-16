package com.android.zyu;

import android.app.Application;
import android.content.Context;

import com.android.zyu.biz.components.DaggerNetComponent;
import com.android.zyu.biz.components.NetComponent;
import com.android.zyu.biz.modules.NetModule;
import com.android.zyu.util.AppCrashHandler;

/**
 * Created by yufei on 2017/9/12.
 */

public class BaseApplication extends Application {

    private static Context context;
    private NetComponent netComponent;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        AppCrashHandler.getInstance().setCrashHandler(this);
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
