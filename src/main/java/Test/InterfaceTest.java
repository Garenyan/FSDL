package Test;

import Bean.Method;
import CodePreProcess.MethodExtract;
import MyTools.ASTTool;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.List;

/**
 * Created by garen on 2019/3/19.
 */
public class InterfaceTest {
    public static void main(String[] args){
        String path = "C:\\Users\\15757\\Desktop\\BusinessPackageController.java";
        CompilationUnit compilationUnit  = ASTTool.createAST(path);
        MethodExtract methodExtract = new MethodExtract();
        List<Method> methods = methodExtract.getAllMethodsToMethodBeans(compilationUnit,path);
        System.out.println(methods.size());
    }
}
