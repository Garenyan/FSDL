package Bean.Lexical;

/**
 * Created by garen on 2019/2/20.
 */
public class VariableType {
    public String getTypeName() {
        return typeName;
    }

    private String typeName;
    //private Integer typeNum; //各个类型出现的频次
    public VariableType(String typeName)
    {
        this.typeName = typeName;
    }

    private Integer num;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
