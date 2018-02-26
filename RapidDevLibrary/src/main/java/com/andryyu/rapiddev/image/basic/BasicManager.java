package com.andryyu.rapiddev.image.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.andryyu.rapiddev.net.callback.HttpCallBack;



/**
 * 图片三级缓存
 * @author AndryYu
 * Created on 2018\2\26 0026.
 */
public class BasicManager {

    private MemoryCacheUtils memoryUtils;
    private LocalCacheUtils localUtils;
    private NetCacheUtils netUtils;

    private static BasicManager instance;

    private BasicManager(Context context) {
        this.memoryUtils = new MemoryCacheUtils();
        this.localUtils = new LocalCacheUtils(context);
        this.netUtils = new NetCacheUtils(memoryUtils, localUtils);
    }

    public static synchronized BasicManager getInstance(Context context){
        if(instance==null){
            instance=new BasicManager(context);
        }
        return instance;
    }

    /**
     * <p>disPlay</p>
     * @param ivPic
     * @param url
     */
    public void disPlay(final ImageView ivPic,final String url) {
        Bitmap bitmap;
        //内存缓存
        bitmap=memoryUtils.getMemoryBitmap(url);
        if (bitmap!=null){
            ivPic.setImageBitmap(bitmap);
            System.out.println("从内存获取图片啦.....");
            return;
        }

        //本地缓存
        bitmap = localUtils.getLocalBitmap(url);
        if(bitmap !=null){
            ivPic.setImageBitmap(bitmap);
            System.out.println("从本地获取图片啦.....");
            //从本地获取图片后,保存至内存中
            memoryUtils.setMemoryBitmap(url,bitmap);
            return;
        }
        //网络缓存
        netUtils.getBitmapFromNet(url, new HttpCallBack() {
            @Override
            public void SuccessCallBack( final Bitmap bitmap) {
                ivPic.post(new Runnable() {
                    @Override
                    public void run() {
                        ivPic.setImageBitmap(bitmap);
                    }
                });
                //从网络获取图片后,保存至本地缓存
                localUtils.setLocalBitmap(url, bitmap);
                //保存至内存中
                memoryUtils.setMemoryBitmap(url, bitmap);
            }

            @Override
            public void FailureCallBack(String code, String error) {

            }
        });
    }
}
