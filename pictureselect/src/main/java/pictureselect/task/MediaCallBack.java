package pictureselect.task;

import java.util.ArrayList;

import pictureselect.media.MediaFile;

/**
 * @ClassName MediaCallBack
 * @Description 获取数据接口
 * @Author ytf
 * CreateDate    2020/4/3 15:22
 */
public interface MediaCallBack {

    void resultArrayList(ArrayList<MediaFile> list);
}
