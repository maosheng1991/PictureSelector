package com.example.pictureselector;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pictureselect.PictureSelector;

/**
 * @ClassName MainActivity
 * @Description 首页
 * @Author ytf
 * CreateDate    2020/4/2 17:48
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_picture_selector)
    TextView tv_picture_selector;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    ArrayList<String> arrayList = new ArrayList<>();

    public final int REQUEST_SELECTOR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_picture_selector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PictureSelector.getInstance().start(MainActivity.this, REQUEST_SELECTOR);
                return false;
            }
        });
    }


    /**
     * 选择完媒体信息后点击确定的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SELECTOR && resultCode == RESULT_OK) {
            arrayList = data.getStringArrayListExtra(PictureSelector.SELECT_ITEM);

            /*if (arrayList == null)
                arrayList = CollectionManage.getInstance().getArrayList();*/

            if (arrayList.size() != 0) {

            }


        }
    }
}
