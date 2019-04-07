package Features;

import Bean.*;
import Bean.Helper.FeatureHelper;
import Bean.Helper.TrainDataFileHelper;
import Bean.Lexical.*;
import Bean.Struct.*;
import CodePreProcess.MethodExtract;
import CodePreProcess.MethodNodeVisitor;
import CodePreProcess.NodeVisitor;
import DataTrain.ConvertToFiles;
import Features.*;
import MyTools.ASTTool;
import MyTools.FileUtils;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.apache.poi.ss.formula.functions.T;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/2/22.
 */
public class FLDLFeatureRun {
    public static void main(String args[])
    {
        String Data_path = "C:\\Users\\15757\\Desktop\\hdu_yjz";
        List<MethodPairSimVector> methodPairSimVectors = getVectorFromFiles(Data_path,"test");
        MethodPairSimVectorHelper.convertToXMLFile(methodPairSimVectors);
    }


    public static List<MethodPairSimVector> getVectorFromFiles(String filepath, String lable){
        List<MethodPairSimVector> methodPairSimVectors = getVectorFromDirFile(filepath);
        List<MethodPairSimVector> results = new ArrayList<MethodPairSimVector>();
        for (MethodPairSimVector methodPairSimVector:methodPairSimVectors){
            if (methodPairSimVector.getLable() == null){
                methodPairSimVector.setLable(lable);
                results.add(methodPairSimVector);
            }
        }
        return results;
    }

    public static List<MethodPairSimVector> getVectorFromFiles(List<String> filepaths, String lable){
        List<MethodPairSimVector> methodPairSimVectors = getVectorFromFiles(filepaths);
        List<MethodPairSimVector> results = new ArrayList<MethodPairSimVector>();
        for (MethodPairSimVector methodPairSimVector:methodPairSimVectors){
            if (methodPairSimVector.getLable() == null){
                methodPairSimVector.setLable(lable);
                results.add(methodPairSimVector);
            }
        }
        return results;
    }

    public static List<MethodPairSimVector> getVectorFromDirFile(String path){
        List<String> paths = FileUtils.getJavaFileLists(path);
        if (paths == null)
        {
            System.out.println("无效测试文件夹");
            return null;
        }
        List<MethodPairSimVector> methodPairSimVectors = getVectorFromFilesPrivtae(paths);
        return methodPairSimVectors;

    }

    public static List<MethodPairSimVector> getVectorFromFiles(List<String> paths){
        List<MethodPairSimVector> methodPairSimVectors = getVectorFromFilesPrivtae(paths);
        return methodPairSimVectors;
    }


    private static List<MethodPairSimVector> getVectorFromFilesPrivtae(List<String> paths) {
//        if (paths.size()<3800) {
        List<Method> methods = new ArrayList<Method>();
        MethodExtract methodExtract = new MethodExtract();
        for (int i = 0; i < paths.size(); i++) //先循环文件夹
        {
            CompilationUnit ASTResult = ASTTool.createAST(paths.get(i));
            if (ASTTool.getOut()) {
                continue;
            }
            methods.addAll(methodExtract.getAllMethodsToMethodBeans(ASTResult, paths.get(i)));

        }//到此，已经提取了文件夹下所有的方法
        List<MethodPairSimVector> methodPairSimVectors = new ArrayList<MethodPairSimVector>();
        //String lable = "test";
        FeatureSimCa featureSimCa = new FeatureSimCa();
        for (int i=0;i<methods.size();i++) {
            Method s = methods.get(i);
            if (!checkMethodLineNum(s)) {
                continue;
            }
            for (int j=i+1;j<methods.size();j++) {
                Method t = methods.get(j);
                if (!checkMethodLineNum(t) || t.equals(s)) {
                    continue;
                }
                /**计算所有相似度**/
                Double leSim1 = featureSimCa.getLeSim1(s.getVariableNames(), t.getVariableNames());
                Double leSim2 = featureSimCa.getLeSim2(s.getVariableTypes(), t.getVariableTypes());
                Double leSim3 = featureSimCa.getLeSim3(s.getOperateTypes(), t.getOperateTypes());
                Double leSim4 = featureSimCa.getLeSim4(s.getMyMethodInvocationNames(), t.getMyMethodInvocationNames());
                Double leSim5 = featureSimCa.getLeSim5(s.getMyMethodInvocationParas(), t.getMyMethodInvocationParas());

                Double sSim1 = featureSimCa.getStructSim1(s.getForObjects(), t.getForObjects());
                Double sSim2 = featureSimCa.getStructSim2(s.getWhileObjects(), t.getWhileObjects());
                Double sSim3 = featureSimCa.getStructSim3(s.getSwitchObjects(), t.getSwitchObjects());
                Double sSim4 = featureSimCa.getStructSim4(s.getDoWhileObjects(), t.getDoWhileObjects());
                Double sSim5 = featureSimCa.getStructSim5(s.getIfObjects(), t.getIfObjects());
                Double sSim6 = featureSimCa.getWholeStructSim(s.getWholeStructObject(), t.getWholeStructObject());

                Double fSim = featureSimCa.getFunSim(s.getParaObjects(), t.getParaObjects());

                MethodPairSimVector methodPairSimVector = MethodPairSimVectorHelper.createMethodPairSimVectorObject(s, t, leSim1, leSim2, leSim3, leSim4, leSim5, sSim1, sSim2, sSim3, sSim4, sSim5, sSim6, fSim);
                methodPairSimVectors.add(methodPairSimVector);
            }
        }
        return methodPairSimVectors;
    }

    private static Boolean checkMethodLineNum(Method method)
    {
        if (method.getBodyLine()>6 )
            return true;
        else
            return false;
    }

    private static Boolean checkMethodLineNum(MethodDeclaration methodDeclaration){
        Block block = methodDeclaration.getBody();
        if (block == null) {
            return false;
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
           if (trueRow<=6){
               return false;
           }else
               return true;
        }
    }


    private static Boolean checkFile(String path){
        List<String> lines  = FileUtils.readFile(path);
       if (lines == null){
           return false;
       }else if (lines.size() > 20000){
           return false;
       }else
           return true;
    }

//    public static List<MethodPairSimVector> getMethodPairSimVectorByNonFeature(List<MethodPairSimVector> methodPairSimVectors, FeatureHelper featureHelper){
//        switch(featureHelper){
//            case VARIABLENAME:
//
//        }
//
//    }







}
