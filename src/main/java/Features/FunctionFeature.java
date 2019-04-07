package Features;

import Bean.Method;
import Bean.Lexical.ParaObject;
import StaticValue.ParaTypeStaticValue;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2019/3/7.
 */
public class FunctionFeature {
    public Method getMethodFunFeatures(MethodDeclaration methodDeclaration) {
        SimpleName name = methodDeclaration.getName();
        List para = methodDeclaration.parameters();

        //System.out.println(para.toString());

        Type returnType = methodDeclaration.getReturnType2();
        Method method = new Method();
        method.setParameters(para);
        method.setMethodName(name);
        method.setReturnType(returnType);
        List<ParaObject> paraObjects = getParaTypeObjects(methodDeclaration);
        method.setParaObjects(paraObjects);
        return method;
    }


    public List<ParaObject> getParaTypeObjects(MethodDeclaration methodDeclaration) {
        List<ParaObject> paraObjects = new ArrayList<ParaObject>();
        //Method method = getMethodFeatures(methodDeclaration);
        List types = methodDeclaration.typeParameters();
        if (types != null) {
            FunVisitor funVisitor = new FunVisitor();
            methodDeclaration.accept(funVisitor);
            List<SingleVariableDeclaration> singleVariableDeclarations = funVisitor.getSingleVariableDeclarations();
            for (int i = 0; i < singleVariableDeclarations.size(); i++) {
                ParaObject paraObject = new ParaObject();
                paraObject.setParameterType(singleVariableDeclarations.get(i).getType());
                //System.out.println(singleVariableDeclarations.get(i).getType().toString());
                paraObject.setParameterTypeName(singleVariableDeclarations.get(i).getName().toString());
                paraObject.setTypeIndex(getParaTypeIndex(paraObject.getParameterType()));
                paraObject.setScore(getParaScore(paraObject.getTypeIndex()));
                paraObjects.add(paraObject);
            }
        } else {
            ParaObject paraObject = new ParaObject();
            paraObject.setParameterTypeName("null");
            paraObject.setTypeIndex(0);
        }
        Type outputType = methodDeclaration.getReturnType2();
        ParaObject outputParaObject = new ParaObject();
        if (outputType != null) {
            // System.out.println(outputType.toString());
            outputParaObject.setParameterTypeName(outputType.toString());
            outputParaObject.setParameterType(outputType);
            outputParaObject.setTypeIndex(getParaTypeIndex(outputType));
            outputParaObject.setScore(outputParaObject.getTypeIndex());
        }
        else
        {
            outputParaObject.setParameterTypeName("void");
           outputParaObject.setTypeIndex(0);
           outputParaObject.setScore(1);
        }
            paraObjects.add(outputParaObject);
        return paraObjects;
    }

    private Integer getParaTypeIndex(Type type) {
        List<String> firstTypeList = ParaTypeStaticValue.PARAFIRSTINDEXNAME;
        Map<String, Integer> typeIndexMap = ParaTypeStaticValue.PARAMETERTYPE_INDEX;
        int index = 0;
        if (type.isSimpleType()) {
            String typeStr = type.toString();
            if (typeIndexMap.get(typeStr) != null) {
                index = typeIndexMap.get(typeStr); //index=1,16,17
            } else {
                index = 4; //包含枚举对象
            }
        } else if (type.isPrimitiveType()) {
            String typeStr = type.toString();
            if (typeIndexMap.get(typeStr) != null) {
                index = typeIndexMap.get(typeStr); //index = 1,2,3
            } else
                index = 18;
        } else if (type.isArrayType()) {
            ArrayType arrayType = (ArrayType) type;
            int demension = arrayType.getDimensions();
            if (demension < 3) {
                index = getIndex(type,demension); //index=5--12
            } else
                index = 18;
        } else if (type.isParameterizedType()) {
            String str = type.toString();
            if (typeIndexMap.get(str) != null) {
                index = typeIndexMap.get(str); //index = 14,15
            } else if (str.split("<").length > 2) //说明这个集合嵌套超过一层
            {
                index = 18;
            } else if (str.contains(",")) {  //说明这个是Map及其相关类
                index = 17;
            } else //不是map且属于list，set，queue等
            {
                for (int i = 0; i < firstTypeList.size(); i++) {
                    if (str.contains("<" + firstTypeList.get(i) + ">")) {
                        index = 13;
                        break;
                    }
                }
                if (index == 0) { //说明这个是自定义对象
                    index = 16;
                }

            }
        } else {
            index = 18;
        }
        return index;

    }

    private int getIndex(Type type,int de) {
        int index = 0;
        String str = type.toString();
        for (String s : ParaTypeStaticValue.PARAFIRSTINDEXNAME) {
            if (str.contains(s+"[")) {
                index = 5 + (de-1)*4;
            }
            break;
        }
        if (index == 0) {
            if (str.contains("String") || str.contains("char") || str.contains("Character")) {
                index = 6+(de-1)*4;
            } else if (str.contains("boolean") || str.contains("Boolean")) {
                index = 7+(de-1)*4;
            } else
                index = 8+(de-1)*4;
        }
        return index;
    }

    public Integer getParaScore(Integer paraIndex)
    {
        int score =0;
        if (paraIndex == 0 || paraIndex==1 || paraIndex==2 || paraIndex == 3)
        {
            score =1;
        }
        else if (paraIndex == 4)
        {
            score = 3;
        }else if (paraIndex == 8 || paraIndex == 12 || paraIndex == 16)
        {
            score = 4;
        }else if (paraIndex == 18)
        {
            score = 5;
        }else if (paraIndex <0 || paraIndex >18)
        {
            score = 0;
        }else
        {
            score = 2;
        }
        return score;
    }


}
