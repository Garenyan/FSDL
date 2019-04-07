package CodePreProcess;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/20.
 */
public class MethodNodeVisitor extends ASTVisitor{
    List<MethodDeclaration> methodNodeList = new ArrayList<MethodDeclaration>();


    public List<MethodDeclaration> getMethodDecs() {
        return methodNodeList;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        methodNodeList.add(node);
        //System.out.println(node.getName().toString());
        return true;
    }

}
