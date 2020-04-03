package pictureselect.media;

import java.util.ArrayList;

/**
 * @ClassName MediaFolder
 * @Description 文件夹
 * @Author ytf
 * CreateDate    2020/4/1 10:28
 */
public class MediaFolder {

    private int folderId;
    private String folderName;
    ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    public MediaFolder(int folderId, String folderName, ArrayList<MediaFile> mediaFiles) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.mediaFiles = mediaFiles;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(ArrayList<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }
}
