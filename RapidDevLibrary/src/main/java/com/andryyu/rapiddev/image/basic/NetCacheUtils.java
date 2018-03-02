package com.andryyu.rapiddev.image.basic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.andryyu.rapiddev.net.NetManager;
import com.andryyu.rapiddev.net.callback.HttpCallBack;

import java.io.OutputStream;

/**
 * 网络缓存
 * @author AndryYu
 * Created on 2018\2\26 0026.
 */
public class NetCacheUtils {

    private MemoryCacheUtils memoryUtils;
    private LocalCacheUtils localUtils;

    public NetCacheUtils(MemoryCacheUtils memoryUtils, LocalCacheUtils localUtils) {
        this.memoryUtils = memoryUtils;
        this.localUtils = localUtils;
    }

    /**
     * <p>getBitmapFromNet</p>
     * @description: 从网络下载图片
     * @param url   下载图片的网络地址
     */
    public void getBitmapFromNet(final String url, HttpCallBack httpCallBack) {
        NetManager.getInstance().doRequest(url, null, httpCallBack);
    }
}
