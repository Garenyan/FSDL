package Features;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/8.
 */
public class FunVisitor extends ASTVisitor{
    private List<SingleVariableDeclaration> singleVariableDeclarations = new ArrayList<SingleVariableDeclaration>();
    @Override
    public boolean visit(SingleVariableDeclaration singleVariableDeclaration)
    {
        singleVariableDeclarations.add(singleVariableDeclaration);
        return true;
    }

    public List<SingleVariableDeclaration> getSingleVariableDeclarations() {
        return singleVariableDeclarations;
    }


}
