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
        return list.add(path);
    }

    /**
     * 从集合中删除指定的文件路径
     *
     * @param path
     * @return
     */
    public boolean deletePathFromCollect(String path) {
        return list.remove(path);
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
}
