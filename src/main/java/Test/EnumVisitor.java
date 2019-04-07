package Test;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/19.
 */
public class EnumVisitor extends ASTVisitor{
    public List<EnumDeclaration> enumDeclarations = new ArrayList<EnumDeclaration>();
    @Override
    public boolean visit(EnumDeclaration enumDeclaration)
    {
        enumDeclarations.add(enumDeclaration);
        return true;
    }
}
