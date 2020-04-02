package com.example.pictureselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pictureselector.R;
import com.example.pictureselector.manage.CollectionManage;
import com.example.pictureselector.media.MediaFile;
import com.example.pictureselector.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName PictureSelectorAdapter
 * @Description 图片列表适配器
 * @Author ytf
 * CreateDate    2020/4/1 11:36
 */
public class PictureSelectorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MediaFile> mediaFiles = new ArrayList<>();

    public PictureSelectorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_item, viewGroup, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ImageHolder imageHolder = (ImageHolder) viewHolder;

        if (!mediaFiles.get(i).isCheck())
            imageHolder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_check));
        else
            imageHolder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_checked));

        Glide.with(context).load(mediaFiles.get(i).getPath()).into(imageHolder.iv_content);

        imageHolder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTouchEventListener != null) {
                    onTouchEventListener.onClick(i);
                }
            }
        });

        imageHolder.iv_item_check.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (!mediaFiles.get(i).isCheck()) {//未被选中
                            if (CollectionManage.getInstance().addFilePathToCollect(mediaFiles.get(i).getPath())) {
                                imageHolder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_checked));
                                mediaFiles.get(i).setCheck(true);
                                notifyItemChanged(i);
                            }
                        } else {
                            if (CollectionManage.getInstance().deletePathFromCollect(mediaFiles.get(i).getPath())) {
                                imageHolder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_check));
                                mediaFiles.get(i).setCheck(false);
                                notifyItemChanged(i);
                            }
                        }

                        if (onTouchEventListener != null) {
                            onTouchEventListener.onCheck(i);
                        }
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaFiles.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_content)
        SquareImageView iv_content;
        @BindView(R.id.iv_item_check)
        ImageView iv_item_check;


        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 设置列表list并刷新
     *
     * @param mediaFiles
     */
    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
        notifyDataSetChanged();
    }

    public interface onTouchEventListener {
        //点击
        void onClick(int position);

        //选择
        void onCheck(int position);
    }

    private onTouchEventListener onTouchEventListener;

    public void setOnTouchEventListener(PictureSelectorAdapter.onTouchEventListener onTouchEventListener) {
        this.onTouchEventListener = onTouchEventListener;
    }
}
