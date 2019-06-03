package DataTest;

import Bean.Helper.CloneDataSetHelper;
import Bean.Helper.NonCloneDataSetHelper;
import Bean.Helper.TrainDataFileHelper;
import Bean.MethodPairSimVector;
import DataTrain.DataSetTagged;
import Features.FLDLFeatureRun;
import Features.MethodPairSimVectorHelper;
import MyTools.FileUtils;
import StaticValue.TrainDataFileStaticValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/27.
 * 人工标记数据集
 */
public class TestDataTagged {
    public static void  main(String[] args){
        //DataSetTagged dataSetTagged = new DataSetTagged();
        TrainDataFileHelper dataFileHelper = new TrainDataFileHelper();
        String testDataFile ="H:\\PostPaperMaterial\\IJaDataset_BCEvalVersion\\bcb_reduced\\2\\default";
        List<String> paths = FileUtils.getJavaFileLists(testDataFile);
        //System.out.println(paths.size());
        List<String> newPaths = new ArrayList<String>();
        for (int i=0;i<200;i++){
            newPaths.add(paths.get(i));
        }
        dataFileHelper.setPaths(newPaths);
        CloneDataSetHelper cloneDataSetHelper = getTestBenchMark(dataFileHelper);
        //NonCloneDataSetHelper nonCloneDataSetHelper = dataSetTagged.getNonCloneDataSet(dataFileHelper);
        System.out.println(cloneDataSetHelper.getMethodPairs());
        List<MethodPairSimVector> methodPairSimVectors = cloneDataSetHelper.getMethodPairSimVectors();
        MethodPairSimVectorHelper.convertToXMLFile(methodPairSimVectors);
    }

    private static CloneDataSetHelper getTestBenchMark(TrainDataFileHelper trainDataFileHelper){
        CloneDataSetHelper cloneDataSetHelper = new CloneDataSetHelper();
        List<MethodPairSimVector> methodPairSimVectors = new ArrayList<MethodPairSimVector>();
        if (trainDataFileHelper.getDirpath() != null) {
            String filePath = trainDataFileHelper.getDirpath();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(filePath, TrainDataFileStaticValue.CLONE);
        }else
        {
            List<String> paths = trainDataFileHelper.getPaths();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(paths,TrainDataFileStaticValue.CLONE);

        }
        List<MethodPairSimVector> cloneMethodPairSimVectors = new ArrayList<MethodPairSimVector>();
        if (methodPairSimVectors == null){
            return null;
        }
        int methodPair_count =0;
        for (MethodPairSimVector methodPairSimVector:methodPairSimVectors){
            if (isBenchMarkClone(methodPairSimVector)){
                methodPair_count++;
                cloneMethodPairSimVectors.add(methodPairSimVector);
            }
        }
        cloneDataSetHelper.setMethodPairs(methodPair_count);
        cloneDataSetHelper.setMethodPairSimVectors(cloneMethodPairSimVectors);
        return cloneDataSetHelper;
    }

    private static Boolean isBenchMarkClone(MethodPairSimVector methodPairSimVector){
        Double[] sims = new Double[]{methodPairSimVector.getLeSim1(),methodPairSimVector.getLeSim2(),methodPairSimVector.getLeSim3()
                ,methodPairSimVector.getLeSim4(),methodPairSimVector.getsSim5(),methodPairSimVector.getLeSim5(),
                methodPairSimVector.getsSim1(),methodPairSimVector.getsSim2(),methodPairSimVector.getsSim3(),
                methodPairSimVector.getsSim4(),methodPairSimVector.getsSim6(),methodPairSimVector.getFunSim()};
        int num=0;
        int syn_num =0;
        Double sum =0.0;
        for (int i=0;i<sims.length;i++){
            if (sims[i] != 0.5){
                num++;
                sum = sum + sims[i];
                if (i!=sims.length-1){
                    syn_num++;
                }
            }
        }
        if (sum>=0.7*num){
            return true;
        }else{
            //标记部分少量MT-3
            if (sum>=0.68*num && methodPairSimVector.getFunSim() >= 0.98){
                return true;
            }
            if (sum<0.68*num && methodPairSimVector.getFunSim()>= 0.98){//标记极少量的type-4 当然也许不存在
               return true;
            }
            return false;
        }
    }
}
