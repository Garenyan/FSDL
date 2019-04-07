package Bean.Helper;

import Bean.MethodPairSimVector;

import java.util.List;

/**
 * Created by garen on 2019/3/25.
 */
public class DataSetInfoHelper {
    private Integer fileNum;
    private Integer methodPairs;
    private List<MethodPairSimVector> methodPairSimVectors;

    public Integer getFileNum() {
        return fileNum;
    }
    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
    }
    public Integer getMethodPairs() {
        return methodPairs;
    }

    public void setMethodPairs(Integer methodPairs) {
        this.methodPairs = methodPairs;
    }

    public List<MethodPairSimVector> getMethodPairSimVectors() {
        return methodPairSimVectors;
    }

    public void setMethodPairSimVectors(List<MethodPairSimVector> methodPairSimVectors) {
        this.methodPairSimVectors = methodPairSimVectors;
    }
}
