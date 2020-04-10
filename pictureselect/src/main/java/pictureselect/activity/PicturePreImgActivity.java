package pictureselect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pictureselect.R;

import java.util.ArrayList;

import pictureselect.adapter.PicturePreImageAdapter;
import pictureselect.manage.CollectionManage;

/**
 * @ClassName PicturePreImgActivity
 * @Description 大图预览
 * @Author ytf
 * CreateDate    2020/4/9 9:20
 */
public class PicturePreImgActivity extends BaseActivity {

    private ImageView iv_left;
    private TextView tv_left;
    private TextView tv_title;
    private TextView tv_confirm;
    private ViewPager rv_img_page;
    PicturePreImageAdapter adapter = new PicturePreImageAdapter(this);
    private ArrayList<String> list = new ArrayList<>();

    public static void startPicturePreImgActivity(Activity context, int position) {
        Intent intent = new Intent(context, PicturePreImgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_image);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        iv_left = findViewById(R.id.iv_left);
        tv_left = findViewById(R.id.tv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_confirm = findViewById(R.id.tv_confirm);
        rv_img_page = findViewById(R.id.rv_img_page);

        tv_title.setVisibility(View.GONE);
        tv_confirm.setText("确定");
        tv_confirm.setEnabled(false);
    }


    private void initListener() {
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_img_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //滑动页面时调用，有三个参数 position当前页面、v偏移比例、il滑动像素
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            //进入一个新的页面调用这个方法，position当前位于哪个页面。
            @Override
            public void onPageSelected(int position) {
                updateTvLeftTitle(position);
            }

            //滑动状态更改时调用
            //position：有三个状态，1按下时调用，抬起时如果发生了滑动值会变为2（不发生滑动不会有2），滑动结束时变为0
            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    private void initData() {
        list = CollectionManage.getInstance().getAllListPath();
        if (list.size() == 0)
            finish();

        int position = getIntent().getIntExtra("position", 0);
        rv_img_page.setAdapter(adapter);
        rv_img_page.setCurrentItem(position);
        updateTvLeftTitle(position);
    }

    /**
     * 修改左上角标题
     */
    private void updateTvLeftTitle(int position) {
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setText(String.format("%d/%d", position + 1, list.size()));
    }
}
