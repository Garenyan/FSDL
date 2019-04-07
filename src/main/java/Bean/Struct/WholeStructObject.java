package Bean.Struct;

/**
 * Created by garen on 2019/3/12.
 */
public class WholeStructObject {
    private Integer maxDepth;
    private Integer leafNum;
    private Integer secondLevelNum;
    private Integer allNodesNum;

    public Integer getAllNodesNum() {
        return allNodesNum;
    }

    public void setAllNodesNum(Integer allNodesNum) {
        this.allNodesNum = allNodesNum;
    }

    public Integer getLeafNum() {
        return leafNum;
    }

    public Integer getMaxDepth() {
        return maxDepth;
    }

    public Integer getSecondLevelNum() {
        return secondLevelNum;
    }

    public void setLeafNum(Integer leafNum) {
        this.leafNum = leafNum;
    }

    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setSecondLevelNum(Integer secondLevelNum) {
        this.secondLevelNum = secondLevelNum;
    }
}
