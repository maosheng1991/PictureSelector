package com.example.pictureselector.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pictureselector.R;
import com.example.pictureselector.adapter.PictureSelectorAdapter;
import com.example.pictureselector.manage.ConfigManage;
import com.example.pictureselector.media.MediaFile;
import com.example.pictureselector.scanner.ImageScanner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName PictureSelectorActivity
 * @Description 图片选择器Activity
 * @Author ytf
 * CreateDate    2020/3/31 14:52
 */
public class PictureSelectorActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
    PictureSelectorAdapter adapter = new PictureSelectorAdapter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        ButterKnife.bind(this);
        initView();
        checkPermission();
    }

    private void initView() {
        iv_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });
        String title = "";
        if (!TextUtils.isEmpty(title = ConfigManage.getInstance().getTitle()))
            tv_title.setText(title);

        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(layoutManager);

        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小。
        rv_list.setHasFixedSize(true);
        //设置划出屏幕后的缓存数
        rv_list.setItemViewCacheSize(60);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });

    }

    private void initData() {
        List<MediaFile> list = new ImageScanner(this).scanner();
        adapter.setMediaFiles(list);
    }

    /**
     * 检测权限
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //无权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //有权限
            initData();
        }

    }

    /**
     * 请求权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
                    initData();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
