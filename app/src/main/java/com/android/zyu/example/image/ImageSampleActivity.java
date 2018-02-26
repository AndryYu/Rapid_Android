package com.android.zyu.example.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.zyu.R;
import com.andryyu.rapiddev.image.basic.BasicManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageSampleActivity extends AppCompatActivity {

    @BindView(R.id.iv_bitmap)
    ImageView ivBitmap;

    private String PICTURE_URL = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_sample);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
        BasicManager.getInstance(this).disPlay(ivBitmap, PICTURE_URL);
    }
}
