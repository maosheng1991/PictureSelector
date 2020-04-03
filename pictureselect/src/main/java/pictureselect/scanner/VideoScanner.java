package pictureselect.scanner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import pictureselect.media.MediaFile;

/**
 * @ClassName VideoScanner
 * @Description 媒体类文件扫描器（视频）
 * @Author ytf
 * CreateDate    2020/4/3 14:44
 */
public class VideoScanner extends AbsMediaScanner {

    public VideoScanner(Context context) {
        super(context);
    }

    @Override
    protected Uri getUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getColumns() {
        return new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_TAKEN
        };
    }

    @Override
    protected String getSelection() {
        return null;
    }

    @Override
    protected String getOrder() {
        return MediaStore.Video.Media.DATE_TAKEN + " desc";
    }

    @Override
    protected String[] getSelectionValues() {
        return null;
    }

    @Override
    protected MediaFile parse(Cursor cursor) {

        MediaFile file = new MediaFile();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));//路径
        String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));//文件类型
        int bucket_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));//文件夹id
        String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));//文件夹名称
        int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));//视频文件的持续时间
        int dateToken = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));//自1970年1月1日以来的毫秒数

        file.setPath(path);
        file.setMime(mime);
        file.setBucket_id(bucket_id);
        file.setFolderName(folderName);
        file.setDuration(duration);
        file.setDateToken(dateToken);

        return file;
    }
}
