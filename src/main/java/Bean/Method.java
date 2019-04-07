package Bean;

import Bean.Lexical.*;
import Bean.Struct.*;
import Features.FunctionFeature;
import Features.LexicalFeature;
import Features.StructureFeature;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

import java.util.List;

/**
 * Created by garen on 2019/2/18.
 */
public class Method {
    private SimpleName methodName;
    private SimpleName className;
    private List parameters;
    private Type returnType;
    private Integer methodIndex;
    private Boolean methodFlag = true;
    private Block body;
    private String filepath;
    private MethodDeclaration methodDeclaration;
    private Integer bodyLine;

    private List<VariableName> variableNames;
    private List<VariableType> variableTypes;
    private List<OperateType> operateTypes;
    private List<MyMethodInvocationName> myMethodInvocationNames;
    private List<MyMethodInvocationPara> myMethodInvocationParas;

    private List<DoWhileObject> doWhileObjects;
    private List<ForObject> forObjects;
    private List<WhileObject> whileObjects;
    private List<SwitchObject> switchObjects;
    private List<IfObject> ifObjects;
    private WholeStructObject wholeStructObject;


    private List<ParaObject> paraObjects;

    public SimpleName getMethodName() {
        return methodName;
    }

    public void setMethodName(SimpleName methodName) {
        this.methodName = methodName;
    }

    public SimpleName getClassName() {
        return className;
    }

    public void setClassName(SimpleName className) {
        this.className = className;
    }

    public List getParameters() {
        return parameters;
    }

    public void setParameters(List parameters) {
        this.parameters = parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public int getMethodIndex() {
        return methodIndex;
    }

    public void setMethodIndex(Integer methodIndex) {
        this.methodIndex = methodIndex;
    }

    public Boolean getMethodFlag() {
        return methodFlag;
    }

    public void setMethodFlag(Boolean methodFlag) {
        this.methodFlag = methodFlag;
    }

    public Block getBody() {
        return body;
    }

    public void setBody(Block body) {
        this.body = body;
    }

    public List<ParaObject> getParaObjects() {
        if (paraObjects == null) {
            FunctionFeature functionFeature = new FunctionFeature();
            List<ParaObject> p = functionFeature.getParaTypeObjects(methodDeclaration);
            setParaObjects(p);
        }
        return paraObjects;
    }

    public void setParaObjects(List<ParaObject> paraObjects) {
        this.paraObjects = paraObjects;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    public Integer getBodyLine() {
        if (bodyLine == null) {
            Block block = methodDeclaration.getBody();
            if (block == null) {
                return 0;
            } else {
                String content = block.toString();
                String[] lines = content.split("\n");
                int i = 0;
                for (String line : lines) {
                    if (line.equals("{") || line.equals("}") || line.equals(""))//去掉这一行为空行，{和}情况。
                    {
                        i++;
                    }
                }
                int trueRow = lines.length - i;
                setBodyLine(trueRow);
            }
        }
        return bodyLine;
    }

    public void setBodyLine(Integer bodyLine) {
        this.bodyLine = bodyLine;
    }

    public List<VariableName> getVariableNames() {
        if (variableNames == null)
        {
            LexicalFeature lexicalFeature =new LexicalFeature();
            List<VariableName> variableNames1 = lexicalFeature.getVaraialeNames(methodDeclaration);
            setVariableNames(variableNames1);
        }
        return variableNames;
    }

    public void setVariableNames(List<VariableName> variableNames) {
        this.variableNames = variableNames;
    }

    public List<VariableType> getVariableTypes() {
        if (variableTypes == null)
        {
            LexicalFeature lexicalFeature =new LexicalFeature();
            List<VariableType> variableTypes1 = lexicalFeature.getAllTypes(methodDeclaration);
            setVariableTypes(variableTypes1);
        }
        return variableTypes;
    }

    public void setVariableTypes(List<VariableType> variableTypes) {
        this.variableTypes = variableTypes;
    }

    public List<OperateType> getOperateTypes() {
        if (operateTypes == null)
        {
            LexicalFeature lexicalFeature =new LexicalFeature();
            List<OperateType> operateTypes1  = lexicalFeature.getOperateTypes(methodDeclaration);
            setOperateTypes(operateTypes1);
        }
        return operateTypes;
    }

    public void setOperateTypes(List<OperateType> operateTypes) {
        this.operateTypes = operateTypes;
    }

    public List<MyMethodInvocationName> getMyMethodInvocationNames() {
        if (myMethodInvocationNames == null)
        {
            LexicalFeature lexicalFeature =new LexicalFeature();
            List<MyMethodInvocationName> a  = lexicalFeature.getMethodInvokeNames(methodDeclaration);
            setMyMethodInvocationNames(a);
        }
        return myMethodInvocationNames;
    }

    public void setMyMethodInvocationNames(List<MyMethodInvocationName> myMethodInvocationNames) {
        this.myMethodInvocationNames = myMethodInvocationNames;
    }

    public List<MyMethodInvocationPara> getMyMethodInvocationParas() {
        if (myMethodInvocationParas == null)
        {
            LexicalFeature lexicalFeature =new LexicalFeature();
            List<MyMethodInvocationPara> a  = lexicalFeature.getMethodInvocationParas(methodDeclaration);
            setMyMethodInvocationParas(a);
        }
        return myMethodInvocationParas;
    }

    public void setMyMethodInvocationParas(List<MyMethodInvocationPara> myMethodInvocationParas) {
        this.myMethodInvocationParas = myMethodInvocationParas;
    }

    public List<DoWhileObject> getDoWhileObjects() {
        if (doWhileObjects == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            List<DoWhileObject> doWhileObjects1 = structureFeature.getDoWhileFeatures(methodDeclaration);
            setDoWhileObjects(doWhileObjects1);
        }
        return doWhileObjects;
    }

    public void setDoWhileObjects(List<DoWhileObject> doWhileObjects) {
        this.doWhileObjects = doWhileObjects;
    }

    public List<ForObject> getForObjects() {
        if (forObjects == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            List<ForObject> a = structureFeature.getForFeatures(methodDeclaration);
            setForObjects(a);
        }
        return forObjects;
    }

    public void setForObjects(List<ForObject> forObjects) {
        this.forObjects = forObjects;
    }

    public List<IfObject> getIfObjects() {
        if (ifObjects == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            List<IfObject> a = structureFeature.getIfFeatures(methodDeclaration);
            setIfObjects(a);
        }
        return ifObjects;
    }

    public void setIfObjects(List<IfObject> ifObjects) {
        this.ifObjects = ifObjects;
    }

    public List<SwitchObject> getSwitchObjects() {
        if (switchObjects == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            List<SwitchObject> a = structureFeature.getSwitchFeatures(methodDeclaration);
            setSwitchObjects(a);
        }
        return switchObjects;
    }

    public void setSwitchObjects(List<SwitchObject> switchObjects) {
        this.switchObjects = switchObjects;
    }

    public List<WhileObject> getWhileObjects() {
        if (whileObjects == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            List<WhileObject> a = structureFeature.getWhileFeatures(methodDeclaration);
            setWhileObjects(a);
        }
        return whileObjects;
    }

    public void setWhileObjects(List<WhileObject> whileObjects) {
        this.whileObjects = whileObjects;
    }

    public WholeStructObject getWholeStructObject() {
        if (wholeStructObject == null)
        {
            StructureFeature structureFeature = new StructureFeature();
            WholeStructObject wholeStructObject1 = structureFeature.getWhileStruct(methodDeclaration);
            setWholeStructObject(wholeStructObject1);
        }
        return wholeStructObject;
    }

    public void setWholeStructObject(WholeStructObject wholeStructObject) {
        this.wholeStructObject = wholeStructObject;
    }
}
