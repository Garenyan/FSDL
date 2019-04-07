package Features;

import Bean.*;
import Bean.Lexical.*;
import StaticValue.StructureStaticValue;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/20.
 */
public class LexicalVisitor extends ASTVisitor{

    private List<VariableName> variableNames = new ArrayList<VariableName>();
    private List<VariableType> variableTypes = new ArrayList<VariableType>();
    private List<QualifiedName> qualifiedNames = new ArrayList<QualifiedName>();
    private List<MyMethodInvocationName> myMethodInvocationNames = new ArrayList<MyMethodInvocationName>();
    private List<MyMethodInvocationPara> myMethodInvocationParas = new ArrayList<MyMethodInvocationPara>();
    private List<OperateType> operateTypes = new ArrayList<OperateType>();
    private List<MyLoop> myLoops = new ArrayList<MyLoop>();


    private List<String> methodInvokeNames  =  new ArrayList<String>();
    private List<String> excludeMethodInfos = new ArrayList<String>();



    public List<String> getMethodInvokeNames() {
        return methodInvokeNames;
    }



    /**提取所有SimpleName，包括qualifiedName、MethodInvocation等等**/
    @Override
    public boolean visit(SimpleName simpleName)
    {
            String namestr = simpleName.toString();
            VariableName variableName = new VariableName(namestr);
            variableNames.add(variableName); //但是这里并不是完全正确的变量名，里面还含有很多的其他信息..
            return true;
    }

    /**用以去除无关的SimpleName**/
    @Override
    public boolean visit(QualifiedName qualifiedName) //这个要不要去，实验结果再决定，理论上绝对要去
    {
        qualifiedNames.add(qualifiedName);
        return true;
    }

    /**得到所有的方法调用信息**/
    @Override
    public boolean visit(MethodInvocation methodInvocation)
    {
        if (methodInvocation.getExpression()!= null) {
            String excludeMethodName = methodInvocation.getExpression().toString();
            excludeMethodInfos.add(excludeMethodName);
        }
        String methodName = methodInvocation.getName().toString();
        List methodpara = methodInvocation.arguments();
        for (Object name : methodpara)
        {
            MyMethodInvocationPara myMethodInvocationPara = new MyMethodInvocationPara();
            myMethodInvocationPara.setParaName(name.toString());
            myMethodInvocationParas.add(myMethodInvocationPara);
        }
        MyMethodInvocationName myMethodInvocationName = new MyMethodInvocationName();
        myMethodInvocationName.setMethodname(methodName);
        myMethodInvocationNames.add(myMethodInvocationName);
        return true;
    }






/**Add Type into List**/
    /**提取变量类型**/
    @Override
    public boolean visit(SimpleType node)
    {
        String typeName = node.getName().toString();
        VariableType variableType =new VariableType(typeName);
        variableTypes.add(variableType);
        return true;

    }

    @Override
    public boolean visit(QualifiedType node)
    {
        String typeName = node.getName().toString();
        VariableType variableType =new VariableType(typeName);
        variableTypes.add(variableType);
        return true;
    }

    @Override
    public boolean visit(PrimitiveType node)
    {
        String typeName = node.toString();
        VariableType variableType =new VariableType(typeName);
        variableTypes.add(variableType);
        return true;
    }

    /**
     * 提取运算符
     * @param infixExpression
     * @return
     */
    @Override
    public boolean visit(InfixExpression infixExpression)
    {
        OperateType operateType = new OperateType();
        if (infixExpression.getOperator() != null) {
             operateType.setOperateTypeStr(infixExpression.getOperator().toString());
        }
        //infixExpression.getOperator().toString();
        operateTypes.add(operateType);

        return true;
    }

    @Override
    public boolean visit(PostfixExpression postfixExpression)
    {
        OperateType operateType = new OperateType();
        if (postfixExpression.getOperator() != null)
        {
            operateType.setOperateTypeStr(postfixExpression.getOperator().toString());
        }

        operateTypes.add(operateType);
        return true;
    }
    @Override
    public boolean visit(Assignment assignment)
    {
        OperateType operateType = new OperateType();
        if (assignment.getOperator() != null)
        {
            operateType.setOperateTypeStr(assignment.getOperator().toString());
        }
        operateTypes.add(operateType);
        //System.out.println("正在遍历assignment结点");
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationFragment declarationFragment)
    {
        OperateType operateType =new OperateType();
        operateType.setOperateTypeStr("=");
        operateTypes.add(operateType);
        return true;
    }

    /**提取循环语句**/
    @Override
    public boolean visit(ForStatement forStatement)
    {
        MyLoop myLoop = new MyLoop(StructureStaticValue.FOR);
        myLoops.add(myLoop);
        return true;
    }

    @Override
    public boolean visit(WhileStatement whileStatement)
    {
        //int x= whileStatement.get
        MyLoop myLoop =new MyLoop(StructureStaticValue.WHILE);
        myLoops.add(myLoop);
        return true;
    }

    public List<VariableType> getVariableTypes() {
        return variableTypes;
    }

    public List<OperateType> getOperateTypes() {
        return operateTypes;
    }

    public List<MyMethodInvocationName> getMyMethodInvocationNames() {
        return myMethodInvocationNames;
    }

    public List<MyMethodInvocationPara> getMyMethodInvocationParas() {
        return myMethodInvocationParas;
    }

    /**这里注意，为了简化实验，我们将变量名以及类型转换的信息放在了一起！但是实验效果其实是一样的**/
    public List<VariableName> getVariableNames() {
        for (VariableType type:variableTypes) //除去所有的变量类型名称
        {
            for (int i=0;i<variableNames.size();i++) {
                if (type.getTypeName().equals(variableNames.get(i).getName()))
                {
                    variableNames.get(i).setFlag(false);
                    break;
                }
            }
        }
        for (int i=0;i<excludeMethodInfos.size();i++)
        {
            for (int j=0;j<variableNames.size();j++)
            {
                if (variableNames.get(j).getFlag())
                {
                    if (excludeMethodInfos.get(i).equals(variableNames.get(j).getName()))
                    {
                        variableNames.get(j).setFlag(false);
                        break;
                    }
                }
            }
        }
        for (int i=0;i<myMethodInvocationNames.size();i++)
        {
            for (int j=0;j<variableNames.size();j++)
            {
                if (variableNames.get(j).getFlag())
                {
                    if (myMethodInvocationNames.get(i).equals(variableNames.get(j).getName()))
                    {
                        variableNames.get(j).setFlag(false);
                        break;
                    }
                }
            }
        }

        for (int i=0;i<myMethodInvocationParas.size();i++)
        {
            for (int j=0;j<variableNames.size();j++)
            {
                if (variableNames.get(j).getFlag())
                {
                    if (myMethodInvocationParas.get(i).equals(variableNames.get(j).getName())){
                        variableNames.get(j).setFlag(false);
                        break;
                    }
                }
            }
        }


        List<VariableName> newVariableNames = new ArrayList<VariableName>();

        for (int i=0;i<variableNames.size();i++)
        {
            if (variableNames.get(i).getFlag()){
                newVariableNames.add(variableNames.get(i));
            }
        }
        return newVariableNames;
    }


}
