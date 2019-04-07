package Bean.Helper;

import java.util.List;

/**
 * Created by garen on 2019/3/26.
 */
public class TrainDataFileHelper {
    private String dirpath;
    private List<String> paths;

    public List<String> getPaths() {
        return paths;
    }

    public String getDirpath() {
        return dirpath;
    }

    public void setDirpath(String dirpath) {
        this.dirpath = dirpath;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
