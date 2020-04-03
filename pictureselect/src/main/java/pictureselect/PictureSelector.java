package pictureselect;

import android.app.Activity;
import android.content.Intent;

import pictureselect.activity.PictureSelectorActivity;
import pictureselect.manage.ConfigManage;

/**
 * @ClassName PictureSelector
 * @Description 统一调用入口
 * @Author ytf
 * CreateDate    2020/3/30 17:03
 */
public class PictureSelector {


    private static PictureSelector pictureSelector;
    public static final String SELECT_ITEM = "select_item";

    public PictureSelector() {

    }

    public static PictureSelector getInstance() {
        if (pictureSelector == null) {
            synchronized (PictureSelector.class) {
                pictureSelector = new PictureSelector();
            }
        }
        return pictureSelector;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public PictureSelector setTitle(String title) {
        ConfigManage.getInstance().setTitle(title);
        return pictureSelector;
    }

    /**
     * 是否展示图片
     *
     * @param showImage
     * @return
     */
    public PictureSelector setShowImage(boolean showImage) {
        ConfigManage.getInstance().setShowImage(showImage);
        return pictureSelector;
    }

    /**
     * 是否展示视频
     *
     * @param showVideo
     * @return
     */
    public PictureSelector setShowVideo(boolean showVideo) {
        ConfigManage.getInstance().setShowVideo(showVideo);
        return pictureSelector;
    }

    /**
     * 设置最大选择数量
     *
     * @param count
     * @return
     */
    public PictureSelector setMaxCount(int count) {
        ConfigManage.getInstance().setMaxCount(count);
        return pictureSelector;
    }

    /**
     * 设置是否显示拍照item
     *
     * @param showCamera
     * @return
     */
    public PictureSelector setShowCamera(boolean showCamera) {
        ConfigManage.getInstance().setShowCamera(showCamera);
        return pictureSelector;
    }

    /**
     * 设置是否支持同时选择图片和视频，默认不支持
     *
     * @param type
     * @return
     */
    public PictureSelector setSelectType(boolean type) {
        ConfigManage.getInstance().setSelectType(type);
        return pictureSelector;
    }

    /**
     * 开始、跳转到图片选择器
     */
    public void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PictureSelectorActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
