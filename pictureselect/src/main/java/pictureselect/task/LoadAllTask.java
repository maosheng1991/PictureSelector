package pictureselect.task;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import pictureselect.media.MediaFile;
import pictureselect.scanner.ImageScanner;
import pictureselect.scanner.VideoScanner;

/**
 * @ClassName LoadAllTask
 * @Description 同时加载图片和视频
 * @Author ytf
 * CreateDate    2020/4/3 15:44
 */
public class LoadAllTask implements Runnable {

    private Context context;
    private MediaCallBack callBack;

    public LoadAllTask(Context context, MediaCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void run() {

        ImageScanner imageScanner = new ImageScanner(context);
        VideoScanner videoScanner = new VideoScanner(context);

        ArrayList<MediaFile> list = new ArrayList<>();

        ArrayList<MediaFile> arrayList;
        if ((arrayList = imageScanner.scanner()).size() != 0)
            list.addAll(arrayList);

        if ((arrayList = videoScanner.scanner()).size() != 0)
            list.addAll(arrayList);

        Collections.sort(list);//按所有文件时间戳进行排序
        if (callBack != null)
            callBack.resultArrayList(list);
    }
}
