package Bean;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/20.
 * 这个类如果不考虑转换成dot的问题，那么这个类其实就是一个List-ASTnode类
 */
public class MyMethodNode {
    public MethodDeclaration methodNode = null;
    public List<MyASTNode> nodeList = null;

    public List<int[]> mapping = null;

    public MyMethodNode() {
        this.methodNode = null;
        this.nodeList = new ArrayList<MyASTNode>();
        this.mapping = new ArrayList<int[]>();
    }

}
