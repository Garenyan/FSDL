package Test;

import Bean.Struct.ForObject;
import Bean.Struct.IfObject;
import Bean.Method;
import CodePreProcess.MethodExtract;
import MyTools.ASTTool;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.List;

/**
 * Created by garen on 2019/3/20.
 */
public class StructFeaTest {
    public static void main(String[] args) {
        String path = "C:\\Users\\15757\\Desktop\\Test2.java";
        CompilationUnit compilationUnit  = ASTTool.createAST(path);
        MethodExtract methodExtract = new MethodExtract();
        List<Method> methods = methodExtract.getAllMethodsToMethodBeans(compilationUnit,path);
//        List<Map<String,Integer>> s = new ArrayList<Map<String, Integer>>();
//        List<Map<String,Integer>> t = new ArrayList<Map<String, Integer>>();
        for (Method method:methods){
            List<ForObject> forObjects = method.getForObjects();
            List<IfObject> ifObjects = method.getIfObjects();
            //List<DoWhileObject> doWhileObjects = method.getDoWhileObjects();
            for (ForObject forObject:forObjects){
                System.out.println(forObject.getX()+","+forObject.getY()+","+forObject.getZ());
                System.out.println(forObject.getNum());
            }
            for (IfObject ifObject:ifObjects){
                System.out.println(ifObject.getX()+","+ifObject.getY()+","+ifObject.getZ());
                System.out.println(ifObject.getNum());
            }

//            for (int i=0;i<method.getForObjects().size();i++) {
//                Map<String, Integer> smap = new HashMap<String, Integer>();
//                smap.put(StructureStaticValue.XINDEX, forObjects.get(i).getxIndex());
//                smap.put(StructureStaticValue.YINDEX, forObjects.get(i).getyIndex());
//                smap.put(StructureStaticValue.ZINDEX, forObjects.get(i).getzIndex());
//                s.add(smap);
//            }


//            for (IfObject ifObject:ifObjects)
//            {
//                System.out.println(ifObject.getxIndex()+","+ifObject.getyIndex()+","+ifObject.getzIndex());
//            }

        }
       // Double dis1 =
    }
}
