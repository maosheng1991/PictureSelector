package pictureselect.manage;

import java.util.ArrayList;
import java.util.List;

import pictureselect.media.MediaFile;

/**
 * @ClassName CollectionManage
 * @Description 集合管理类
 * @Author ytf
 * CreateDate    2020/3/31 16:28
 */
public class CollectionManage {

    private ArrayList<MediaFile> allList = new ArrayList<>();//所有扫描出的图片
    public List<String> tempList = new ArrayList<>();//记录每次选择的图片集合，退出时重置
    private List<String> list = new ArrayList<>();//所有已选择的

    private static CollectionManage collectionManage;
    public static int tempCount = 0;//单独记录每次选择的数量，退出时重置


    public CollectionManage() {

    }


    public static CollectionManage getInstance() {
        if (collectionManage == null) {
            synchronized (CollectionManage.class) {
                collectionManage = new CollectionManage();
            }
        }
        return collectionManage;
    }

    /**
     * 决定能否继续选择
     *
     * @return
     */
    public boolean isCanChoose() {
        return (list.size() + tempCount) < ConfigManage.getInstance().getMaxCount();
    }

    /**
     * 添加文件路径到记录每次选择图片的集合
     *
     * @param path
     * @return
     */
    public boolean addFilePathToCollect(String path) {
        tempCount++;
        return tempList.add(path);
    }

    /**
     * 从记录每次选择集合中删除指定的文件路径
     *
     * @param path
     * @return
     */
    public boolean deletePathFromCollect(String path) {
        tempCount--;
        return tempList.remove(path);
    }


    /**
     * 将记录每次的选择的集合添加到所有已选集合中
     */
    public void addTempToList() {
        for (String path : tempList)
            list.add(path);
    }

    /**
     * 将所有已选择图片集合转换为ArrayList并返回
     *
     * @return
     */
    public ArrayList<String> getArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : list)
            arrayList.add(s);
        return arrayList;
    }

    /**
     * 返回所有扫描出的图片集合Arraylist<Path>
     *
     * @return
     */
    public ArrayList<String> getAllListPath() {
        ArrayList<String> list = new ArrayList<>();
        for (MediaFile file : allList)
            list.add(file.getPath());
        return list;
    }

    /**
     * 保存所有已扫描出图片集合
     *
     * @param allList
     */
    public void setAllList(ArrayList<MediaFile> allList) {
        this.allList = allList;
    }

    /**
     * 清除临时list
     */
    public void cleanTempArrayList() {
        tempList.clear();
    }

    /**
     * 清除已选择集合
     */
    public void cleanArrayList() {
        list.clear();
    }

    /**
     * 清除所有扫描出图片集合
     */
    public void cleanList() {
        allList.clear();
    }

}
