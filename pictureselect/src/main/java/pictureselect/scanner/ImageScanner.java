package pictureselect.scanner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import pictureselect.media.MediaFile;

/**
 * @ClassName ImageScanner
 * @Description 媒体类扫描器（图片）
 * @Author ytf
 * CreateDate    2020/3/31 20:34
 */
public class ImageScanner extends AbsMediaScanner {

    public ImageScanner(Context context) {
        super(context);
    }

    @Override
    protected Uri getUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getColumns() {
        return new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };
    }

    @Override
    protected String getSelection() {
        return MediaStore.Images.Media.MIME_TYPE + "=?" + " or " +
                MediaStore.Images.Media.MIME_TYPE + "=?"
                ;
    }

    @Override
    protected String[] getSelectionValues() {
        return new String[]{
                "image/jpeg"/*, "image/png"*/
        };
    }


    @Override
    protected String getOrder() {
        return MediaStore.Images.Media.DATE_TAKEN + " desc";
    }


    @Override
    protected MediaFile parse(Cursor cursor) {
        MediaFile mediaFile = new MediaFile();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
        Integer floderId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
        String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        long dataToken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

        mediaFile.setPath(path);
        mediaFile.setMime(type);
        mediaFile.setBucket_id(floderId);
        mediaFile.setFolderName(folderName);
        mediaFile.setDateToken(dataToken);

        return mediaFile;
    }


}
