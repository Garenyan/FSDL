package Bean.Lexical;

/**
 * Created by garen on 2019/2/22.
 */
public class OperateType {
    private String operateTypeStr;
    public OperateType(String str)
    {
        this.operateTypeStr = str;
    }

    public String getOperateTypeStr() {
        return operateTypeStr;
    }

    public void setOperateTypeStr(String operateTypeStr) {
        this.operateTypeStr = operateTypeStr;
    }

    public OperateType()
    {}

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    private Integer num;

}
