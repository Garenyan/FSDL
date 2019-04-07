package Test;

import Bean.Lexical.ParaObject;
import CodePreProcess.MethodExtract;
import CodePreProcess.MethodNodeVisitor;
import Features.FunctionFeature;
import MyTools.ASTTool;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

/**
 * Created by garen on 2019/3/2.
 */
public class FunTest {
    public static void main(String[] args)
    {
        //List<String> paths =  FileUtils.getJavaFileLists("C:\\Users\\15757\\Desktop\\wangjie\\method\\org\\apache\\tools");
        //String path = paths.get(0);
        String path="C:\\Users\\15757\\Desktop\\BusinessPackageController.java";
        CompilationUnit compilationUnit = ASTTool.createAST(path);
        //TypeDeclaration typeDeclaration =
        MethodExtract methodExtract = new MethodExtract();
        System.out.println(path);
        MethodNodeVisitor methodNodeVisitor = new MethodNodeVisitor();
        List<MethodDeclaration> methodDeclarations = methodExtract.getMethodNodes(compilationUnit,methodNodeVisitor,path);
        /*System.out.println(methodDeclarations.size());
        System.out.println(methodDeclarations.get(2).getJavadoc().toString());
        for (int i=0;i<compilationUnit.getCommentList().size();i++)
        {
            System.out.println(compilationUnit.getCommentList().get(i).toString());
        }*/
       // System.out.println(compilationUnit.getCommentList());
       MethodDeclaration methodDeclaration =  methodDeclarations.get(0);
//        Block body = methodDeclaration.getBody();
//        String methodStr = body.toString();//这个字符串中不含有注释
//        if (methodStr.split("\n").length<=6)
//        {
//            System.out.println("false");
//        }
//        else
//            System.out.println("true");
        //System.out.println(methodDeclaration.getLength());
        FunctionFeature functionFeature = new FunctionFeature();
        //functionFeature.getMethodFeatures(methodDeclaration);
       List<ParaObject> paraObjects =  functionFeature.getParaTypeObjects(methodDeclaration);
       for (int i=0;i<paraObjects.size();i++)
       {
           System.out.println(paraObjects.get(i).getParameterType().toString());
           System.out.println(paraObjects.get(i).getTypeIndex());
           System.out.println(paraObjects.get(i).getScore());
       }
        //ASTTool[][] astTools = new ASTTool[][];




    }
}
