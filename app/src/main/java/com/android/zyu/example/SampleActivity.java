package com.android.zyu.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.zyu.R;
import com.android.zyu.example.image.ImageSampleActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.rv_sample)
    RecyclerView rvSample;

    protected List<SampleModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

        fillData();
        initRecylerView();
    }

    /**
     * <p>fillData</p>
     * @description  填充数据
     */
    private void fillData(){
        data = new ArrayList<>();
        data.add(new SampleModel("Image处理", "图片相关处理操作"));
    }

    /**
     * <p>initRecylerView</p>
     * @description 初始化recyclerview
     */
    private void initRecylerView(){
        SampleAdapter adapter = new SampleAdapter(data) {
            @Override
            public void onItemClick(int position) {
                itemClick(position);
            }
        };

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvSample.setLayoutManager(new LinearLayoutManager(this));
        rvSample.setItemAnimator(new DefaultItemAnimator());
        rvSample.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvSample.setAdapter(adapter);
    }

    /**
     * <p>itemClick</p>
     * @param position
     */
    private void itemClick(int position){
        switch (position){
            case 0:
                startActivity(new Intent(SampleActivity.this, ImageSampleActivity.class));
                break;
        }
    }
}
