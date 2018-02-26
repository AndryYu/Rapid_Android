package com.andryyu.rapiddev.image.basic;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 * @author AndryYu
 * Created on 2018\2\26 0026.
 */
public class MemoryCacheUtils {

    //1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
    // private HashMap<String,Bitmap> mMemoryCache=new HashMap<>();
    //2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();
     private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        long number = Runtime.getRuntime().maxMemory();
        if(mMemoryCache == null) {
            this.mMemoryCache = new LruCache<String, Bitmap>((int) number) {
                //用于计算每个条目的大小
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    int byteCount = value.getByteCount();
                    return byteCount;
                }
            };
        }
    }

    /**
     * <p>getMemoryBitmap</p>
     * @param url
     * @return
     */
    public  Bitmap getMemoryBitmap(String url) {
        return mMemoryCache.get(url);
    }

    /**
     * <p>setMemoryBitmap</p>
     * @param url
     * @param bitmap
     */
    public void setMemoryBitmap(String url, Bitmap bitmap) {
        this.mMemoryCache.put(url, bitmap);
    }
}
