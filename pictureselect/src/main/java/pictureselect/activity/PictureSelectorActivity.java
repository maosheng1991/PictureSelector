package pictureselect.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pictureselect.R;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import pictureselect.PictureSelector;
import pictureselect.adapter.PictureSelectorAdapter;
import pictureselect.manage.CollectionManage;
import pictureselect.manage.ConfigManage;
import pictureselect.media.MediaFile;
import pictureselect.task.LoadAllTask;
import pictureselect.task.LoadImageTask;
import pictureselect.task.LoadVideoTask;
import pictureselect.task.MediaCallBack;
import pictureselect.util.MediaUtil;
import pictureselect.util.ToastUtil;

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
    private int maxCount;
    private String title;
    private int optionalCount;//本次可选的个数

    /**
     * 拍照相关
     */
    private String cameraFilePath;//拍照后存储的文件路径
    private int REQUEST_CODE_CAPTURE = 1;//拍照的回调


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
        maxCount = ConfigManage.getInstance().getMaxCount();
        title = ConfigManage.getInstance().getTitle();
        optionalCount = maxCount - CollectionManage.getInstance().getArrayList().size();

        iv_left = findViewById(R.id.iv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_confirm = findViewById(R.id.tv_confirm);
        rv_list = findViewById(R.id.rv_list);

        tv_confirm.setEnabled(false);//默认不可用

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(title))
            tv_title.setText(title);

        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(layoutManager);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小。
        rv_list.setHasFixedSize(true);
        //设置划出屏幕后的缓存数
        rv_list.setItemViewCacheSize(60);

        //设置图片点击和选中监听
        adapter.setOnTouchEventListener(new PictureSelectorAdapter.onTouchEventListener() {
            @Override
            public void onClick(int position) {
                PicturePreImgActivity.startPicturePreImgActivity(PictureSelectorActivity.this, position);
            }

            @Override
            public void onCheck(int position) {
                String str = "(" + CollectionManage.tempCount + "/" + optionalCount + ")";
                if (CollectionManage.tempCount != 0)
                    tv_confirm.setEnabled(true);
                else
                    tv_confirm.setEnabled(false);//本次未选择或者取消了全部选择

                tv_confirm.setText("确定" + (CollectionManage.tempCount != 0 ? str : ""));
            }
        });

        //点击拍照的监听
        adapter.setOnClickCameraLister(new PictureSelectorAdapter.onClickCameraLister() {
            @Override
            public void onCameraClick(int position) {
                if (!CollectionManage.getInstance().isCanChoose()) {
                    ToastUtil.show(PictureSelectorActivity.this, "最多可以选择" + ConfigManage.getInstance().getMaxCount() + "个文件！");
                    return;
                } else {
                    showCamera();
                }
                return;
            }
        });

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
    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        ) {
            //有权限
            initData();
        } else {
            //没有权限，进行请求
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                    , 1);
        }

    }

    /**
     * 打开相机
     */
    private void showCamera() {
        //获取存储路径

        String fileName = "img_" + System.currentTimeMillis() + ".jpg";
        cameraFilePath = MediaUtil.getFileProviderPath() + fileName;
        File file = new File(cameraFilePath);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_CAPTURE);
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
                    CollectionManage.getInstance().setAllList(list);
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
     * 关闭页面的回调,同时也是处理拍照完成的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAPTURE) {
                File file = new File(cameraFilePath);
                if (file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    //通知媒体库刷新
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    //加入到每次所选集合中
                    CollectionManage.getInstance().addFilePathToCollect(cameraFilePath);
                    toBack();
                }
            }
        }
    }

    /**
     * 返回调用页面
     */
    private void toBack() {
        CollectionManage.getInstance().addTempToList();
        Intent intent = new Intent();
        intent.putStringArrayListExtra(PictureSelector.SELECT_ITEM, CollectionManage.getInstance().getArrayList());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CollectionManage.tempCount = 0;
        CollectionManage.getInstance().cleanTempArrayList();
    }
}
