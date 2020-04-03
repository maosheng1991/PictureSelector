package pictureselect.media;

/**
 * @ClassName MediaFile
 * @Description 媒体类文件实体
 * @Author ytf
 * CreateDate    2020/4/1 9:44
 */
public class MediaFile implements Comparable<MediaFile> {

    private String path;//路径
    private String mime;//文件类型
    private Integer bucket_id;//文件夹id
    private String folderName;//文件夹名称
    private long duration;//视频文件的持续时间
    private long dateToken;//自1970年1月1日以来的毫秒数
    private boolean isCheck = false;//是否被点击

    public MediaFile() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Integer getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(Integer bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDateToken() {
        return dateToken;
    }

    public void setDateToken(long dateToken) {
        this.dateToken = dateToken;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public int compareTo(MediaFile o) {
        return (int) (o.getDateToken() - this.dateToken);
    }
}
