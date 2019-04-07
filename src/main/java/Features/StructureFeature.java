package Features;

import Bean.*;
import Bean.Struct.*;
import StaticValue.StructureStaticValue;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2019/2/28.
 */
public class StructureFeature {


    private int ID = 0;
   // private int high = 1;

    private void getDirectChildren(ASTNode node,int lable,Map<Integer, MyASTNode> Nodes,int high)
    {
       // int high = 1;
       // int x=1;
        MyASTNode myNode = new MyASTNode();
        myNode.highy = high;
        //myNodeList.add(myNode);
        Nodes.put(lable,myNode);
        myNode.astNode = node; //根结点
        myNode.lable= lable;
        myNode.nodeType = node.getClass().toString();
        List listProperty = node.structuralPropertiesForType();
        boolean hasChildren = false;
        for(int i = 0; i < listProperty.size(); i++){
            StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) listProperty.get(i);
            if(propertyDescriptor instanceof ChildListPropertyDescriptor){//ASTNode列表
                ChildListPropertyDescriptor childListPropertyDescriptor = (ChildListPropertyDescriptor)propertyDescriptor;
                Object children = node.getStructuralProperty(childListPropertyDescriptor);
                List<ASTNode> childrenNodes = (List<ASTNode>)children;
                for(ASTNode childNode: childrenNodes){
                    if(childNode == null)
                        continue;
                    hasChildren = true;
                    myNode.childrenNodes.add(childNode);
                    myNode.childrenLables.add((++ID));
                    getDirectChildren(childNode, ID, Nodes,high+1);//继续递归
                }

            }else if(propertyDescriptor instanceof ChildPropertyDescriptor){//一个ASTNode
                ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor)propertyDescriptor;
                Object child = node.getStructuralProperty(childPropertyDescriptor);
                ASTNode childNode = (ASTNode)child;
                if(childNode == null)
                    continue;
                hasChildren = true;
                myNode.childrenNodes.add(childNode);
                myNode.childrenLables.add((++ID));
                getDirectChildren(childNode, ID, Nodes,high+1);//继续递归

            }
        }
        if(hasChildren){
            myNode.isLeaf = false;
        }
        else{
            myNode.isLeaf = true;
        }
    }
    public List<IfObject> getIfFeatures(MethodDeclaration methodDeclaration)
    {
        List<Object> Objects = getSpecficStatment(methodDeclaration,StructureStaticValue.IFSTATEMENT);
        List<IfObject> ifObjects = new ArrayList<IfObject>();
        for (Object o :Objects)
        {
            IfObject ifObject = (IfObject)o;
            ifObjects.add(ifObject);
        }
        return ifObjects;
    }

    public List<ForObject> getForFeatures(MethodDeclaration methodDeclaration)
    {
        List<Object> Objects = getSpecficStatment(methodDeclaration,StructureStaticValue.FORSTATMENT);
        List<ForObject> forObjects = new ArrayList<ForObject>();
        for (Object o :Objects)
        {
            ForObject forObject = (ForObject) o;
            forObjects.add(forObject);
        }
        return forObjects;
    }

    public List<DoWhileObject> getDoWhileFeatures(MethodDeclaration methodDeclaration)
    {
        List<Object> Objects = getSpecficStatment(methodDeclaration,StructureStaticValue.DOWHILESTATEMENT);
        List<DoWhileObject> doWhileObjects = new ArrayList<DoWhileObject>();
        for (Object o :Objects)
        {
            DoWhileObject doWhileObject = (DoWhileObject) o;
            doWhileObjects.add(doWhileObject);
        }
        return doWhileObjects;
    }

    public List<SwitchObject> getSwitchFeatures(MethodDeclaration methodDeclaration)
    {
        List<Object> Objects = getSpecficStatment(methodDeclaration,StructureStaticValue.SWITCHSTATEMENT);
        List<SwitchObject> switchObjects = new ArrayList<SwitchObject>();
        for (Object o :Objects)
        {
            SwitchObject switchObject = (SwitchObject)o;
            switchObjects.add(switchObject);
        }
        return switchObjects;
    }


    public List<WhileObject> getWhileFeatures(MethodDeclaration methodDeclaration)
    {
        List<WhileObject> whileObjects = new ArrayList<WhileObject>();
        Map<Integer,MyASTNode> myASTNodeMap = new HashMap<Integer, MyASTNode>();
        getDirectChildren(methodDeclaration,0,myASTNodeMap,1);
        for (Integer i :myASTNodeMap.keySet())
        {
           //System.out.println(myASTNodeMap.get(i));
           if (myASTNodeMap.get(i).nodeType.contains(StructureStaticValue.WHILESTATEMENT))
           {
               int x=1;
               WhileObject whileObject = new WhileObject();
               whileObject.setY(myASTNodeMap.get(i).highy);
               ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
               int parentHigh = myASTNodeMap.get(i).highy-1;
               Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
               int z = map.get(StructureStaticValue.ZINDEX);
               x = map.get(StructureStaticValue.XINDEX);
               int num = getStatementNum(myASTNodeMap.get(i).astNode);
               whileObject.setNum(num);
               whileObject.setZ(z);
               whileObject.setX(x);
               whileObjects.add(whileObject);
           }
       }
       return whileObjects;
    }

    private Map<String,Integer> getXAndZIndex(Map<Integer,MyASTNode> myASTNodeMap, int i, int parentHigh, ASTNode parentNode)
    {
        int x=1;
        int z=0;
        Map<String,Integer> map = new HashMap<String, Integer>();
        //System.out.println(myASTNodeMap.size());
        for (Integer j:myASTNodeMap.keySet())
        {
            if (j != i)
            {
                if ((myASTNodeMap.get(j).highy == parentHigh) && (!myASTNodeMap.get(i).astNode.equals(parentNode)))
                {
                    x = x + myASTNodeMap.get(j).childrenNodes.size();
                    z++;
                }
               if (myASTNodeMap.get(j).astNode.equals(parentNode))
                {
                    List<ASTNode> nodes = myASTNodeMap.get(j).childrenNodes;
                    for (int m=0;m<nodes.size();m++)
                    {
                        if (nodes.get(m).equals(myASTNodeMap.get(i).astNode))
                        {
                            x = x+m;
                            break;
                        }
                    }
                    break;
                }
            }

        }
        map.put(StructureStaticValue.XINDEX,x);
        map.put(StructureStaticValue.ZINDEX,z);

        return map;
    }

    private List<Object> getSpecficStatment(MethodDeclaration methodDeclaration,String statementStr)
    {
        List<Object> objects = new ArrayList<Object>();
        Map<Integer,MyASTNode> myASTNodeMap = new HashMap<Integer, MyASTNode>();
        getDirectChildren(methodDeclaration,0,myASTNodeMap,1);
        for (Integer i :myASTNodeMap.keySet())
        {
            if (myASTNodeMap.get(i).nodeType.contains(statementStr))
            {
                int x=1;
                if (statementStr.toLowerCase().contains("while")) {
                    WhileObject whileObject = new WhileObject();
                    whileObject.setY(myASTNodeMap.get(i).highy);
                    ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
                    int parentHigh = myASTNodeMap.get(i).highy - 1;
                    Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
                    int z = map.get(StructureStaticValue.ZINDEX);
                    x = map.get(StructureStaticValue.XINDEX);
                    whileObject.setX(x);
                    whileObject.setZ(z);
                    int num = getStatementNum(myASTNodeMap.get(i).astNode);
                    whileObject.setNum(num);
                    objects.add(whileObject);
                }
                else if (statementStr.toLowerCase().contains("do"))
                {
                    DoWhileObject doWhileObject = new DoWhileObject();
                    doWhileObject.setY(myASTNodeMap.get(i).highy);
                    ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
                    int parentHigh = myASTNodeMap.get(i).highy - 1;
                    Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
                    int z = map.get(StructureStaticValue.ZINDEX);
                    x = map.get(StructureStaticValue.XINDEX);
                    int num = getStatementNum(myASTNodeMap.get(i).astNode);
                    doWhileObject.setX(x);
                    doWhileObject.setNum(num);
                    doWhileObject.setZ(z);
                    objects.add(doWhileObject);

                }
                else if (statementStr.toLowerCase().contains("switch"))
                {
                    SwitchObject switchObject = new SwitchObject();
                    switchObject.setY(myASTNodeMap.get(i).highy);
                    ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
                    int parentHigh = myASTNodeMap.get(i).highy - 1;
                    Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
                    int z = map.get(StructureStaticValue.ZINDEX);
                    x = map.get(StructureStaticValue.XINDEX);
                    int num = getStatementNum(myASTNodeMap.get(i).astNode);
                    switchObject.setX(x);
                    switchObject.setNum(num);
                    switchObject.setZ(z);
                    objects.add(switchObject);

                }
                else if (statementStr.toLowerCase().contains("if"))
                {
                    IfObject ifObject = new IfObject();
                    ifObject.setY(myASTNodeMap.get(i).highy);
                    ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
                    int parentHigh = myASTNodeMap.get(i).highy - 1;
                    Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
                    int z = map.get(StructureStaticValue.ZINDEX);
                    x = map.get(StructureStaticValue.XINDEX);
                    int num = getStatementNum(myASTNodeMap.get(i).astNode);
                    ifObject.setX(x);
                    ifObject.setNum(num);
                    ifObject.setZ(z);
                    objects.add(ifObject);
                ;
                }
                else if (statementStr.toLowerCase().contains("for"))
                {
                    ForObject forObject = new ForObject();
                    forObject.setY(myASTNodeMap.get(i).highy);
                    ASTNode parentNode = myASTNodeMap.get(i).astNode.getParent();
                    int parentHigh = myASTNodeMap.get(i).highy - 1;
                    Map<String,Integer> map  = getXAndZIndex(myASTNodeMap,i,parentHigh,parentNode);
                    int z = map.get(StructureStaticValue.ZINDEX);
                    x = map.get(StructureStaticValue.XINDEX);
                    int num = getStatementNum(myASTNodeMap.get(i).astNode);
                    forObject.setX(x);
                    forObject.setZ(z);
                    forObject.setNum(num);
                    objects.add(forObject);
                }
            }
        }
        return objects;

    }

    private Integer getStatementNum(ASTNode node)
    {
        StructVistior structVistior = new StructVistior();
        node.accept(structVistior);
        int num = structVistior.getBreakStatements().size()+structVistior.getContinueStatements().size()+structVistior.getDoStatements().size()
                +structVistior.getExpressionStatements().size()+structVistior.getForStatements().size()+structVistior.getReturnStatements().size()
                +structVistior.getVariableDeclarationStatements().size()+structVistior.getSwitchStatements().size()+structVistior.getIfStatements().size()
                +structVistior.getWhileStatements().size();
        return num;
    }

    public WholeStructObject getWhileStruct(MethodDeclaration methodDeclaration)
    {
        WholeStructObject wholeStructObject = new WholeStructObject();
        Map<Integer,MyASTNode> myASTNodeMap = new HashMap<Integer, MyASTNode>();
        getDirectChildren(methodDeclaration,0,myASTNodeMap,1);
        wholeStructObject.setLeafNum(getLeafNum(myASTNodeMap));
        wholeStructObject.setMaxDepth(getMaxDepth(myASTNodeMap));
        wholeStructObject.setAllNodesNum(getAllNodesNum(myASTNodeMap));
        return wholeStructObject;
    }

    private Integer getMaxDepth(Map<Integer,MyASTNode> myASTNodeMap)
    {
        List<Integer> highs = new ArrayList<Integer>();
        for (Integer i :myASTNodeMap.keySet())
        {
            highs.add(myASTNodeMap.get(i).highy);
        }
        int maxDepth = getMax(highs);
        return maxDepth;
    }

    private Integer getLeafNum(Map<Integer,MyASTNode> myASTNodeMap)
    {
        int num=0;
        for (Integer i:myASTNodeMap.keySet())
        {
            if (myASTNodeMap.get(i).isLeaf)
                num++;
        }
        return num;
    }

    private Integer getSecondLevelNodeNum(Map<Integer,MyASTNode> myASTNodeMap)
    {
        int num=0;
        for (Integer i:myASTNodeMap.keySet())
        {
            if (myASTNodeMap.get(i).highy == 2)
            {
                num++;
            }
        }
        return num;
    }

    private Integer getMax(List<Integer> list)
    {
        int temp=0;
        for (int i=0;i<list.size();i++)
        {
            if (list.get(i)>temp)
            {
                temp = list.get(i);
            }
        }
        return temp;
    }

    private Integer getAllNodesNum(Map<Integer,MyASTNode> myASTNodeMap){
        if (myASTNodeMap != null){
            return myASTNodeMap.size();
        }
        else
            return 0;
    }





}
