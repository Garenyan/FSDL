package Bean.Helper;

import Bean.MethodPairSimVector;

/**
 * Created by garen on 2019/3/27.
 */
public class CloneMethodPairHelper {
    private MethodPairSimVector methodPairSimVector;
    private String cloneType;

    public MethodPairSimVector getMethodPairSimVector() {
        return methodPairSimVector;
    }

    public String getCloneType() {
        return cloneType;
    }

    public void setCloneType(String cloneType) {
        this.cloneType = cloneType;
    }

    public void setMethodPairSimVector(MethodPairSimVector methodPairSimVector) {
        this.methodPairSimVector = methodPairSimVector;
    }
}
