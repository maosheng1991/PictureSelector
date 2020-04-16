package pictureselect.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import pictureselect.application.MyApplication;
import pictureselect.media.MediaFile;
import pictureselect.media.MediaType;

/**
 * @ClassName MediaUtil
 * @Description 图片/视频操作相关工具类
 * @Author ytf
 * CreateDate    2020/4/15 11:33
 */
public class MediaUtil {

    /**
     * 获取视频时长
     *
     * @param duration
     * @return
     */
    public static String getVideoDuration(long duration) {
        if (duration <= 1000) {
            return "00:01";
        }
        Date date = new Date(duration);
        SimpleDateFormat simpleDateFormat;
        if (duration >= 3600 * 1000)
            simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        else
            simpleDateFormat = new SimpleDateFormat("mm:ss");

        return simpleDateFormat.format(date);
    }

    /**
     * 获取文件类型
     *
     * @return
     */
    public static int getMediaType(MediaFile file) {
        if (file.getDuration() > 0)
            return MediaType.ITEM_TYPE_VIDEO;
        else
            return MediaType.ITEM_TYPE_IMAGE;
    }

    /**
     * 获取共享文件路径
     *
     * @return
     */
    public static String getFileProviderPath() {
        return MyApplication.getApplication().getProviderPath();
    }
}
