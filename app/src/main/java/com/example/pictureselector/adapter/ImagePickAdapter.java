package com.example.pictureselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pictureselector.R;
import com.example.pictureselector.media.MediaFile;
import com.example.pictureselector.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName ImagePickAdapter
 * @Description 图片列表适配器
 * @Author ytf
 * CreateDate    2020/4/1 11:36
 */
public class ImagePickAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MediaFile> mediaFiles = new ArrayList<>();

    public ImagePickAdapter(Context context) {
        this.context = context;
        this.mediaFiles = mediaFiles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_item, viewGroup, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImageHolder imageHolder = (ImageHolder) viewHolder;
        imageHolder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_check));
        Glide.with(context).load(mediaFiles.get(i).getPath()).into(imageHolder.iv_content);
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
}
