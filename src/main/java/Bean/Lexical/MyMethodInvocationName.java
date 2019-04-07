package Bean.Lexical;

import org.eclipse.jdt.core.dom.MethodInvocation;

public class MyMethodInvocationName {
    private String Methodname;
    private Integer nameNum;
    //private MethodInvocation methodInvocation;

    public String getMethodname() {
        return Methodname;
    }

    public void setMethodname(String methodname) {
        Methodname = methodname;
    }

    public Integer getNameNum() {
        return nameNum;
    }

    public void setNameNum(Integer nameNum) {
        this.nameNum = nameNum;
    }

   /* public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }*/
}

