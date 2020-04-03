package pictureselect.manage;

/**
 * @ClassName ConfigManage
 * @Description 图片选择器参数配置类
 * @Author ytf
 * CreateDate    2020/3/30 17:08
 */
public class ConfigManage {

    private String title;//标题
    private boolean showImage = true;//是否显示图片，默认显示
    private boolean showVideo;//是否显示视频，默认不显示
    private boolean showCamera;//是否显示拍照item，默认不显示
    private boolean selectType;//选择模式是否支持同时选择图片视频，默认不支持。
    private int maxCount = 1;//最大选择数量，默认为一

    private static ConfigManage configManage;

    public ConfigManage() {
    }

    public static ConfigManage getInstance() {
        if (configManage == null) {
            synchronized (ConfigManage.class) {
                configManage = new ConfigManage();
            }
        }
        return configManage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowImage() {
        return showImage;
    }

    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
    }

    public boolean isShowVideo() {
        return showVideo;
    }

    public void setShowVideo(boolean showVideo) {
        this.showVideo = showVideo;
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public boolean getSelectType() {
        return selectType;
    }

    public void setSelectType(boolean selectType) {
        this.selectType = selectType;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
