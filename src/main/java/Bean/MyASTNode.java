package Bean;

import org.eclipse.jdt.core.dom.ASTNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/20.
 * 这个类其实就是在原来的ASTNode的基础上增加了一个行数的属性而已..
 * 你就当成ASTnode
 */
public class MyASTNode {
    public ASTNode astNode =null;
    public int lineNum = -1;

    public int lable; //编号
    public int highy;
   // public int weightx;
    public List<Integer> childrenLables = new ArrayList<Integer>(); //直接的子节点的编号
    public List<ASTNode> childrenNodes = new ArrayList<ASTNode>(); //直接的子节点
    public boolean isLeaf = false; //是否是叶子节点
    public String nodeType = "unknow";
}
