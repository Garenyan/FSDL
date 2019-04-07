package CodePreProcess;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/20.
 */
public class NodeVisitor extends ASTVisitor{
    public List<ASTNode> nodeList = new ArrayList<ASTNode>();

    @Override
    public void preVisit(ASTNode astNode)
    {
        nodeList.add(astNode);
    }
    public List<ASTNode> getNodeList(){
        return nodeList;
    }

}
