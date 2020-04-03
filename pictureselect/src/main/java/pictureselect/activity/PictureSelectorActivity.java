package pictureselect.activity;

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
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pictureselect.R;

import java.util.ArrayList;
import java.util.List;

import pictureselect.PictureSelector;
import pictureselect.adapter.PictureSelectorAdapter;
import pictureselect.manage.CollectionManage;
import pictureselect.manage.ConfigManage;
import pictureselect.media.MediaFile;
import pictureselect.scanner.ImageScanner;
import pictureselect.task.LoadAllTask;
import pictureselect.task.LoadImageTask;
import pictureselect.task.LoadVideoTask;
import pictureselect.task.MediaCallBack;

/**
 * @ClassName PictureSelectorActivity
 * @Description 图片选择器Activity
 * @Author ytf
 * CreateDate    2020/3/31 14:52
 */
public class PictureSelectorActivity extends BaseActivity {

    /**
     * 界面UI
     */
    private ImageView iv_left;
    private TextView tv_title;
    private TextView tv_confirm;
    private RecyclerView rv_list;

    /**
     * 参数
     */
    private boolean showImage;
    private boolean showVideo;


    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
    PictureSelectorAdapter adapter = new PictureSelectorAdapter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        initView();
        checkPermission();
    }

    private void initView() {

        showImage = ConfigManage.getInstance().isShowImage();
        showVideo = ConfigManage.getInstance().isShowVideo();

        iv_left = findViewById(R.id.iv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_confirm = findViewById(R.id.tv_confirm);
        rv_list = findViewById(R.id.rv_list);

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                toBack();
            }
        });

    }

    /**
     * 获取数据
     */
    private void initData() {

        Runnable runnableTask = null;

        if (showImage && !showVideo)//只加载图片
            runnableTask = new LoadImageTask(this, new CallBackData());

        if (showVideo && !showImage)//只加载视频
            runnableTask = new LoadVideoTask(this, new CallBackData());

        if (runnableTask == null)//全部加载
            runnableTask = new LoadAllTask(this, new CallBackData());

        new Thread(runnableTask).start();
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
     * 返回数据的接口
     */
    class CallBackData implements MediaCallBack {

        @Override
        public void resultArrayList(final ArrayList<MediaFile> list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setMediaFiles(list);
                }
            });
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

    /**
     * 返回调用页面
     */
    private void toBack() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(PictureSelector.SELECT_ITEM, CollectionManage.getInstance().getArrayList());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CollectionManage.getInstance().cleanArrayList();
    }
}
