package pictureselect.task;

import android.content.Context;

import java.util.ArrayList;

import pictureselect.media.MediaFile;
import pictureselect.scanner.VideoScanner;

/**
 * @ClassName LoadVideoTask
 * @Description 加载视频
 * @Author ytf
 * CreateDate    2020/4/3 15:34
 */
public class LoadVideoTask implements Runnable {

    private Context context;
    private MediaCallBack callBack;

    public LoadVideoTask(Context context, MediaCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        VideoScanner videoScanner = new VideoScanner(context);

        ArrayList<MediaFile> list = videoScanner.scanner();

        if (callBack != null)
            callBack.resultArrayList(list);
    }
}
