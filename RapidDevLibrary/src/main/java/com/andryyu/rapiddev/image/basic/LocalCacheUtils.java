package com.andryyu.rapiddev.image.basic;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.andryyu.rapiddev.image.ImageUtil;
import com.andryyu.rapiddev.image.basic.disk.DiskLruCache;
import com.andryyu.rapiddev.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 本地磁盘缓存
 *
 * @author AndryYu
 *         Created on 2018\2\26 0026.
 */
public class LocalCacheUtils {

    //磁盘缓存
    private DiskLruCache diskLruCache;
    private int MAX_SIZE = 1024 * 1024 * 20;// 20Mb

    public LocalCacheUtils(Context context) {
        if (diskLruCache == null || diskLruCache.isClosed()) {
            try {
                File cacheDir = new File(FileUtil.getFileDir("image/basic", context));
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                //初始化DiskLruCache
                diskLruCache = DiskLruCache.open(cacheDir, 1, 1, MAX_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>getMemoryBitmap</p>
     *
     * @param url
     * @return
     * @description 获取本地图片缓存
     */
    public Bitmap getLocalBitmap(String url) {
        Bitmap bitmap = null;
        String key = ImageUtil.hashKeyForDisk(url);
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * <p>setLocalBitmap</p>
     *
     * @param url
     * @param bitmap
     */
    public void setLocalBitmap(String url, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        String key = ImageUtil.hashKeyForDisk(url);
        try {
            editor = diskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                Bitmap2OutputStream(bitmap, outputStream);
                if (outputStream != null) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Bitmap2OutputStream</p>
     *
     * @param bm   Bitmap数据
     * @param baos outputstream
     * @return outputstream
     * @description Bitmap格式数据写入到outputstream中
     */
    private OutputStream Bitmap2OutputStream(Bitmap bm, OutputStream baos) {
        if (bm != null) {
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        return baos;
    }
}
