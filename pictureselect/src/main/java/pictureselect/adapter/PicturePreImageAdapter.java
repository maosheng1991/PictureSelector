package pictureselect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pictureselect.manage.CollectionManage;
import pictureselect.view.CustomImageView;

/**
 * @ClassName PicturePreImageAdapter
 * @Description 图片预览的适配
 * @Author ytf
 * CreateDate    2020/4/9 11:21
 */
public class PicturePreImageAdapter extends PagerAdapter {

    ArrayList<String> list;
    Context context;

    public PicturePreImageAdapter(Context context) {
        this.context = context;
        list = CollectionManage.getInstance().getAllListPath();
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        CustomImageView imageView = new CustomImageView(context);
        Glide.with(context).load(list.get(position)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CustomImageView) object);
    }

}
