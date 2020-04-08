package pictureselect.manage;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CollectionManage
 * @Description 集合管理类
 * @Author ytf
 * CreateDate    2020/3/31 16:28
 */
public class CollectionManage {

    private List<String> list = new ArrayList<>();
    private static CollectionManage collectionManage;
    public static int tempCount = 0;//单独记录每次选择的数量，退出时重置
    public List<String> tempList = new ArrayList<>();//记录每次选择的图片集合，退出时重置

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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    /**
     * 添加文件路径到集合
     *
     * @param path
     * @return
     */
    public boolean addFilePathToCollect(String path) {
        tempCount++;
        return tempList.add(path);
    }

    /**
     * 从集合中删除指定的文件路径
     *
     * @param path
     * @return
     */
    public boolean deletePathFromCollect(String path) {
        tempCount--;
        return tempList.remove(path);
    }

    /**
     * 转换为ArrayList
     *
     * @return
     */
    public ArrayList<String> getArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : list) {
            arrayList.add(s);
        }
        return arrayList;
    }

    /**
     * 将记录每次的选择的集合添加到主集合中
     */
    public void addTempToList() {
        for (String path : tempList)
            list.add(path);
    }

    /**
     * 清除临时list
     */
    public void cleanTempArrayList() {
        tempList.clear();
    }

    /**
     * 清除整个list
     */
    public void cleanArrayList() {
        list.clear();
    }
}
