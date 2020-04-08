package pictureselect.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pictureselect.R;

/**
 * @ClassName ToastUtil
 * @Description 弹窗功能类
 * @Author ytf
 * CreateDate    2020/4/7 11:14
 */
public class ToastUtil {

    /**
     * 弹出内容
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param text
     * @param duration
     */
    private static void show(Context context, String text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView view = (TextView) View.inflate(context, R.layout.layout_toast, null);
        view.setText(" " + text + " ");
        toast.setView(view);
        toast.show();
    }
}
