package pictureselect.application;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * @ClassName MyApplication
 * @Description
 * @Author ytf
 * CreateDate    2020/4/15 21:23
 */
public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    /**
     * 获取共享文件路径
     *
     * @return
     */
    public String getProviderPath() {
        String path;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {//sd卡可用
            path = Environment.getExternalStorageDirectory().getPath()
                    + File.separator
                    + "Camera_Picture"
                    + File.separator;
        } else {
            path = getCacheDir().getPath() + File.separator + "Camera_Picture" + File.separator;
        }
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }
}
