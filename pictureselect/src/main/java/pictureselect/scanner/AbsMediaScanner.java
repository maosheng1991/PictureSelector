package pictureselect.scanner;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import pictureselect.media.MediaFile;

/**
 * @ClassName AbsMediaScanner
 * @Description 媒体扫描类基类
 * @Author ytf
 * CreateDate    2020/3/31 20:36
 */
public abstract class AbsMediaScanner {

    private Context context;

    public AbsMediaScanner(Context context) {
        this.context = context;
    }

    /**
     * 获取URI
     *
     * @return
     */
    protected abstract Uri getUri();

    /**
     * 查询列名
     *
     * @return
     */
    public abstract String[] getColumns();

    /**
     * 查询条件
     *
     * @return
     */
    protected abstract String getSelection();

    /**
     * 获取排序规则
     *
     * @return
     */
    protected abstract String getOrder();

    /**
     * 查询条件值
     *
     * @return
     */
    protected abstract String[] getSelectionValues();

    /**
     * 对外暴露游标
     *
     * @param cursor
     * @return
     */
    protected abstract MediaFile parse(Cursor cursor);

    /**
     * 开始扫描
     *
     * @return
     */
    public ArrayList<MediaFile> scanner() {
        ArrayList<MediaFile> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(getUri(), getColumns(), getSelection(), getSelectionValues(), getOrder());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MediaFile mediaFile = parse(cursor);
                list.add(mediaFile);
            }
        }
        return list;
    }
}
