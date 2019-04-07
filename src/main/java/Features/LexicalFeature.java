package Features;

import Bean.Lexical.*;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2019/2/18.
 */
public class LexicalFeature {
    /**
     * 得到所有变量名称
     * @param methodDeclaration
     * @return
     */
    public List<VariableName> getVaraialeNames(MethodDeclaration methodDeclaration)
    {
        LexicalVisitor visitor = new LexicalVisitor();
        methodDeclaration.accept(visitor);
        List<VariableName> variableNameList = visitor.getVariableNames();
        List<VariableName> results = new ArrayList<VariableName>();
       Map<String,Integer> map = new HashMap<String, Integer>();
       for (VariableName variableName:variableNameList)
       {
           String v = variableName.getName();
           if (map.containsKey(v))
           {
               int num = map.get(v);
               num++;
               map.put(v,num);
           }
           else
           {
               map.put(v,1);
           }
       }
       for (String str:map.keySet())
       {
           VariableName variableName1 = new VariableName(str);
           variableName1.setNum(map.get(str));
           results.add(variableName1);
       }
        return results;
    }

    /**
     * 得到所有类型
     * @param methodDeclaration
     * @return
     */
    public List<VariableType> getAllTypes(MethodDeclaration methodDeclaration)
    {
        LexicalVisitor visitor = new LexicalVisitor();
        methodDeclaration.accept(visitor);
        List<VariableType> variableTypes = visitor.getVariableTypes();
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (VariableType variableType : variableTypes)
        {
            String typeStr = variableType.getTypeName();
            if (map.containsKey(typeStr))
            {
                int value = map.get(typeStr)+1;
                map.put(typeStr,value);
            }else
            {
                map.put(typeStr,1);
            }
        }
        List<VariableType> results = new ArrayList<VariableType>();
        for (String typeStr:map.keySet())
        {
            int value = map.get(typeStr);
            VariableType variableType = new VariableType(typeStr);
            variableType.setNum(value);
            results.add(variableType);
        }
        return results;
    }

    /**
     * 调用方法的方法名
     * @param methodDeclaration
     * @return
     */
    public List<MyMethodInvocationName> getMethodInvokeNames(MethodDeclaration methodDeclaration)
    {
        LexicalVisitor visitor = new LexicalVisitor();
        methodDeclaration.accept(visitor);
        List<MyMethodInvocationName> myMethodInvocationNames = visitor.getMyMethodInvocationNames();
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (MyMethodInvocationName myMethodInvocationName : myMethodInvocationNames)
        {
            String methodName = myMethodInvocationName.getMethodname();
            if (map.containsKey(methodName))
            {
                int value = map.get(methodName)+1;
                map.put(methodName,value);
            }else
            {
                map.put(methodName,1);
            }
        }
        List<MyMethodInvocationName> results = new ArrayList<MyMethodInvocationName>();
        for (String methodName:map.keySet())
        {
            int value = map.get(methodName);
            MyMethodInvocationName myMethodInvocationName = new MyMethodInvocationName();
            myMethodInvocationName.setMethodname(methodName);
            myMethodInvocationName.setNameNum(value);
            results.add(myMethodInvocationName);
        }
        return results;
    }


    /**
     * 得到所有运算符
     * @param methodDeclaration
     * @return
     */
    public List<OperateType> getOperateTypes(MethodDeclaration methodDeclaration)
    {
        LexicalVisitor visitor = new LexicalVisitor();
        methodDeclaration.accept(visitor);
        List<OperateType> operateTypes = visitor.getOperateTypes();//这个还没统计个数和去重...
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (OperateType operateType : operateTypes)
        {
            String operStr = operateType.getOperateTypeStr();
            if (map.containsKey(operStr))
            {
                int value = map.get(operStr)+1;
                map.put(operStr,value);
            }else
            {
                map.put(operStr,1);
            }
        }
        List<OperateType> results = new ArrayList<OperateType>();
        for (String operStr:map.keySet())
        {
            int value = map.get(operStr);
            OperateType operateType = new OperateType();
            operateType.setOperateTypeStr(operStr);
            operateType.setNum(value);
            results.add(operateType);
        }
        return results; //这个已经统计完个数和去完重
    }

    /**
     * 得到所有调用方法的参数
     * @param methodDeclaration
     * @return
     */
    public List<MyMethodInvocationPara> getMethodInvocationParas(MethodDeclaration methodDeclaration)
    {
        LexicalVisitor lexicalVisitor = new LexicalVisitor();
        methodDeclaration.accept(lexicalVisitor);
        List<MyMethodInvocationPara> myMethodInvocationParas = lexicalVisitor.getMyMethodInvocationParas();
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (MyMethodInvocationPara operateType : myMethodInvocationParas)
        {
            String paraName = operateType.getParaName();
            if (map.containsKey(paraName))
            {
                int value = map.get(paraName)+1;
                map.put(paraName,value);
            }else
            {
                map.put(paraName,1);
            }
        }
        List<MyMethodInvocationPara> results = new ArrayList<MyMethodInvocationPara>();
        for (String paraName:map.keySet())
        {
            int value = map.get(paraName);
            MyMethodInvocationPara myMethodInvocationPara = new MyMethodInvocationPara();
            myMethodInvocationPara.setParaName(paraName);
            myMethodInvocationPara.setNum(value);
            results.add(myMethodInvocationPara);
        }
        return results;
    }


}
