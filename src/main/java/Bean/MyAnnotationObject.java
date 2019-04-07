package Bean;

/**
 * Created by garen on 2019/3/7.
 */
public class MyAnnotationObject {
    private String annotationText;
    private Integer startLineNum;
    private Integer endLineNum;
    private String annotationType;
    private String className;
    private String fileName;

    public String getAnnotationText() {
        return annotationText;
    }

    public Integer getEndLineNum() {
        return endLineNum;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setEndLineNum(Integer endLineNum) {
        this.endLineNum = endLineNum;
    }

    public void setStartLineNum(Integer startLineNum) {
        this.startLineNum = startLineNum;
    }

}
