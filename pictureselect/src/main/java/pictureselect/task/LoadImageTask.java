package pictureselect.task;

import android.content.Context;

import java.util.ArrayList;

import pictureselect.media.MediaFile;
import pictureselect.scanner.ImageScanner;

/**
 * @ClassName LoadImageTask
 * @Description 加载图片
 * @Author ytf
 * CreateDate    2020/4/3 15:13
 */
public class LoadImageTask implements Runnable {

    private Context context;
    private MediaCallBack callBack;

    public LoadImageTask(Context context, MediaCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        ImageScanner scanner = new ImageScanner(context);

        ArrayList<MediaFile> arrayList = scanner.scanner();

        if (callBack != null)
            callBack.resultArrayList(arrayList);
    }
}
