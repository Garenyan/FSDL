package CodePreProcess;

import Bean.Method;
import Bean.MyMethodNode;
import Bean.MyASTNode;
import StaticValue.MethodStaticValue;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.util.*;

/**
 * Created by garen on 2018/12/17.
 * 此类用来提取文件里面的方法，包括构造方法和一般方法（抽象方法和含有内部类还未测试，但是这是属于ASTParaser里面，
 * 换言之这一块不用考虑也可以，不属于个人代码）
 * 个人代码已于2019-02-18测试通过
 *
 */
public class MethodExtract {
    /**
     * 这个方法是用来提取一个Java文件里所有方法，包括方法名称，方法参数，方法返回类型以及方法体信息。
     * @param compilationUnit
     * @param path
     * @return 返回的是这个文件里所有方法的信息！
     */
    public List<Map<String,Object>> getMethodsToListMap(CompilationUnit compilationUnit, String path){
        List types = compilationUnit.types();
        List<Map<String,Object>> methodlists = new ArrayList<Map<String, Object>>();
        if (types.size() == 0) {
            File file = new File(path);
            if (file.exists()) {
                String filepath = file.getAbsolutePath();
                String fileName = file.getName();
                System.out.println("There are no classes in your Java file! Please check your" + fileName + "in" + filepath);
            }
            else
            {
                System.out.println("There is no file in" + path);
            }
        }else if(types.size() == 1) //一个java文件里只有一个class
        {
            TypeDeclaration typeDeclaration = (TypeDeclaration) types.get(0);
//            typeDeclaration.getName();
//            compilationUnit.getPackage().toString();
            List<Map<String,Object>> methodlist = getMethodInfoAndBody(typeDeclaration);
            methodlists = methodlist;
        }
        else  //一个java文件里有多个class
        {
            int size = types.size();
            for (int i=0;i<size;i++)
            {
                TypeDeclaration typeDeclaration = (TypeDeclaration)types.get(i);
                List<Map<String,Object>> methodlist = getMethodInfoAndBody(typeDeclaration);
                int size1 = methodlist.size();
                if (size1 != 0)
                {
                    for (int j=0;j<size1;j++)
                    {
                        methodlists.add(methodlist.get(j));
                    }
                }
            }
        }
        return methodlists;
    }

    private List<Map<String,Object>> getMethodInfoAndBody(TypeDeclaration typeDeclaration) {
        List<Map<String,Object>> methodLists= new ArrayList<Map<String, Object>>();
        MethodDeclaration methodDeclaration[] = typeDeclaration.getMethods();
        SimpleName className = typeDeclaration.getName();
        int length = methodDeclaration.length;
        if (length != 0)
        {
            for (int i=0;i<length;i++)
            {
                Boolean methodflag = true;
                SimpleName methodName = methodDeclaration[i].getName();//得到方法名称
                List param = methodDeclaration[i].parameters();//得到方法参数
                Type returnType = methodDeclaration[i].getReturnType2();//得到方法的返回类型
                Block methodbody = methodDeclaration[i].getBody();
                Map<String,Object> methodHashMap = new HashMap<String, Object>();
                methodHashMap.put(MethodStaticValue.CLASS_NAME,className);
                methodHashMap.put(MethodStaticValue.METHOD_NAME,methodName);
                methodHashMap.put(MethodStaticValue.METHOD_PARA,param);
                methodHashMap.put(MethodStaticValue.METHOD_RETURN,returnType);
                methodHashMap.put(MethodStaticValue.METHOD_BODY,methodbody);
                methodHashMap.put(MethodStaticValue.METHOD_INDEX,i+1);
                methodHashMap.put(MethodStaticValue.METHOD_FLAG,methodflag);
                methodLists.add(methodHashMap);
            }
        }
        return methodLists;
    }

    /**
     * 这个方法仅仅是将map存储方式变成对象存储方法，其余的没有任何变化，
     * 因此无论使用最上面的的方法，还是使用这个方法都是一样的，最终得到的结果是一样的；
     * @param compilationUnit
     * @param path
     * @return
     */
    public List<Method> getMethods(CompilationUnit compilationUnit,String path){
        List<Map<String,Object>> list = getMethodsToListMap(compilationUnit,path);
        List<Method> methodList = new ArrayList<Method>();
        int size = list.size();
        if (size != 0)
        {
            for (int i=0;i<size;i++)
            {
                Map<String,Object> map = list.get(i);
                Method method = new Method();
                method.setMethodName((SimpleName) map.get(MethodStaticValue.METHOD_NAME));
                method.setClassName((SimpleName)map.get(MethodStaticValue.CLASS_NAME));
                method.setParameters((List)map.get(MethodStaticValue.METHOD_PARA));
                method.setReturnType((Type)map.get(MethodStaticValue.METHOD_RETURN));
                method.setMethodFlag((Boolean)map.get(MethodStaticValue.METHOD_FLAG));
                method.setMethodIndex((Integer)map.get(MethodStaticValue.METHOD_INDEX));
                method.setBody((Block)map.get(MethodStaticValue.METHOD_BODY));
                methodList.add(method);
            }
        }
        return methodList;
    }

    private List<String> methodpaths = new ArrayList<String>();

    public List<String> getMethodpaths() {
        return methodpaths;
    }

    /**
     * 下面是通过遍历树的方式，返回方法结点
     *
     */
    public List<MethodDeclaration> getMethodNodes(CompilationUnit compilationUnit, MethodNodeVisitor methodNodeVisitor, String path)
    {
        compilationUnit.accept(methodNodeVisitor);
        List<MethodDeclaration> methodDeclarations = methodNodeVisitor.getMethodDecs();
        methodpaths.add(path);
        return methodDeclarations;
    }

    /**返回方法里面所有相关结点**/
    private List<MyMethodNode> getAllNodesInMethod(CompilationUnit compilationUnit,MethodDeclaration methodDeclaration,String path,Boolean hasRoot){
        List<MyMethodNode> methodNodeList = new ArrayList<MyMethodNode>();
        MyMethodNode myMethodNode = new MyMethodNode();
        myMethodNode.methodNode = methodDeclaration;
        NodeVisitor nodeVisitor = new NodeVisitor();//真正的结点遍历器
        methodDeclaration.accept(nodeVisitor);
        List<ASTNode> astNodes = nodeVisitor.getNodeList();
        for (ASTNode node : astNodes)
        {
            MyASTNode myASTNode = new MyASTNode();
            myASTNode.lineNum = compilationUnit.getLineNumber(node.getStartPosition());
            myMethodNode.nodeList.add(myASTNode);
            if (!hasRoot) {
                if (node.equals(methodDeclaration)) //把根节点排除掉
                {
                    continue;
                }
            }
            //后面代码是主要用来增加映射，作用在于生成.dot文件，如果不需要dot文件，忽视下面几行代码以及map属性；
            int pHashcode = node.getParent().hashCode();
            int hashcode = node.hashCode();
            int[] link = { pHashcode, hashcode };
            myMethodNode.mapping.add(link);
        }
        methodNodeList.add(myMethodNode);
        return methodNodeList;
    }

    /**
     * 默认不需要根结点的方法里面结点遍历
     * @param compilationUnit
     * @param methodDeclaration
     * @param path
     * @return
     */
    public List<MyMethodNode> getAllNodesInMethod(CompilationUnit compilationUnit,MethodDeclaration methodDeclaration,String path)
    {
        List<MyMethodNode> methodNodeList = new ArrayList<MyMethodNode>();
        methodNodeList = getAllNodesInMethod(compilationUnit,methodDeclaration,path,false);
        return methodNodeList;
    }

    /**
     * 这个是最终的方法段提取返回
     * @param compilationUnit
     * @param filepath
     * @return
     */
    public List<Method> getAllMethodsToMethodBeans(CompilationUnit compilationUnit,String filepath)
    {
        MethodNodeVisitor methodNodeVisitor = new MethodNodeVisitor();
        List<MethodDeclaration> methodDeclarations = getMethodNodes(compilationUnit,methodNodeVisitor,filepath);
        List<Method> methods = new LinkedList<Method>();
        for (MethodDeclaration methodDeclaration:methodDeclarations)
        {
            Method method = new Method();
            method.setMethodDeclaration(methodDeclaration);
            method.setFilepath(filepath);
            method.setMethodName(methodDeclaration.getName());
            methods.add(method);

        }
        return methods;
    }


    //public List<MethodDeclaration> getAll

}
