package pictureselect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pictureselect.R;

import java.util.ArrayList;
import java.util.List;

import pictureselect.manage.CollectionManage;
import pictureselect.manage.ConfigManage;
import pictureselect.media.MediaFile;
import pictureselect.media.MediaType;
import pictureselect.util.MediaUtil;
import pictureselect.util.ToastUtil;
import pictureselect.view.SquareImageView;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        if (itemType == MediaType.ITEM_TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_image_item, viewGroup, false);
            return new ImageHolder(view);
        }
        if (itemType == MediaType.ITEM_TYPE_VIDEO) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_video_item, viewGroup, false);
            return new VideoHolder(view);
        }
        if (itemType == MediaType.ITEM_TYPE_CAMERA) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_camera_item, viewGroup, false);
            return new CameraHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemType = getItemViewType(i);
        final MediaHolder holder;

        switch (itemType) {
            case MediaType.ITEM_TYPE_IMAGE://图片类型
                holder = (ImageHolder) viewHolder;
                if (ConfigManage.getInstance().isShowCamera())
                    bindMediaData(holder, i - 1);
                else
                    bindMediaData(holder, i);
                break;
            case MediaType.ITEM_TYPE_VIDEO://视频类型
                holder = (VideoHolder) viewHolder;
                if (ConfigManage.getInstance().isShowCamera())
                    bindMediaData(holder, i - 1);
                else
                    bindMediaData(holder, i);
                break;
            case MediaType.ITEM_TYPE_CAMERA://拍照
                holder = (CameraHolder) viewHolder;
                ((CameraHolder) holder).ll_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickCameraLister != null) {
                            clickCameraLister.onCameraClick(i);
                        }
                    }
                });
                break;
        }
    }

    /**
     * 图片/视频时的数据绑定
     *
     * @param holder
     * @param i
     */
    private void bindMediaData(final MediaHolder holder, final int i) {
        if (!mediaFiles.get(i).isCheck())
            holder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_check));
        else
            holder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_checked));

        Glide.with(context).load(mediaFiles.get(i).getPath()).into(holder.iv_content);

        //点击
        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTouchEventListener != null) {
                    onTouchEventListener.onClick(i);
                }
            }
        });

        //选中
        holder.iv_item_check.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (!mediaFiles.get(i).isCheck()) {//未被选中

                            if (CollectionManage.getInstance().isCanChoose()) {
                                if (CollectionManage.getInstance().addFilePathToCollect(mediaFiles.get(i).getPath())) {
                                    holder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_checked));
                                    mediaFiles.get(i).setCheck(true);
//                                    notifyItemChanged(i);
                                }
                            } else {
                                ToastUtil.show(context, "最多可以选择" + ConfigManage.getInstance().getMaxCount() + "个文件！");
                            }
                        } else {
                            if (CollectionManage.getInstance().deletePathFromCollect(mediaFiles.get(i).getPath())) {
                                holder.iv_item_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_image_check));
                                mediaFiles.get(i).setCheck(false);
//                                notifyItemChanged(i);
                            }
                        }

                        if (onTouchEventListener != null) {
                            onTouchEventListener.onCheck(i);
                        }

                }
                return true;
            }
        });

        //如果是视频，显示时长
        if (holder instanceof VideoHolder)
            ((VideoHolder) holder).tv_video_time.setText(MediaUtil.getVideoDuration(mediaFiles.get(i).getDuration()));
    }

    /**
     * 获取对应类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (ConfigManage.getInstance().isShowCamera()) {
            if (position == 0)
                return MediaType.ITEM_TYPE_CAMERA;
            else
                return MediaUtil.getMediaType(mediaFiles.get(position - 1));
        }

        return MediaUtil.getMediaType(mediaFiles.get(position));
    }

    /**
     * 获取数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return ConfigManage.getInstance().isShowCamera() ? mediaFiles.size() + 1 : mediaFiles.size();
    }

    /**
     * \
     * 图片
     */
    class ImageHolder extends MediaHolder {
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    /**
     * 视频
     */
    class VideoHolder extends MediaHolder {

        TextView tv_video_time;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            tv_video_time = itemView.findViewById(R.id.tv_video_time);
        }
    }

    /**
     * 媒体类文件
     */
    class MediaHolder extends RecyclerView.ViewHolder {
        SquareImageView iv_content;
        ImageView iv_item_check;

        public MediaHolder(@NonNull View itemView) {
            super(itemView);
            iv_content = itemView.findViewById(R.id.iv_content);
            iv_item_check = itemView.findViewById(R.id.iv_item_check);
        }
    }

    /**
     * 拍照item
     */
    class CameraHolder extends MediaHolder {

        LinearLayout ll_camera;

        public CameraHolder(@NonNull View itemView) {
            super(itemView);
            ll_camera = itemView.findViewById(R.id.ll_camera);
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

    /**
     * 点击大图和选中的接口
     */
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

    /**
     * 点击拍照的接口监听
     */
    public interface onClickCameraLister {
        void onCameraClick(int position);
    }

    private onClickCameraLister clickCameraLister;

    public void setOnClickCameraLister(PictureSelectorAdapter.onClickCameraLister onClickCameraLister) {
        this.clickCameraLister = onClickCameraLister;
    }
}
