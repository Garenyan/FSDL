package Bean.Lexical;

import Bean.Method;

/**
 * Created by garen on 2019/2/20.
 */
public class VariableName {
    public String getName() {
        return name;
    }

    private String name;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    private Integer num;

    private Boolean flag = true;
    public VariableName(String name)
    {
        this.name = name;
    }

    public VariableName(){}

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    private Method method;


}
