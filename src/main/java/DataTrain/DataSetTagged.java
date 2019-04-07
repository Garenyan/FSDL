package DataTrain;

import Bean.Helper.CloneDataSetHelper;
import Bean.Helper.FeatureHelper;
import Bean.Helper.NonCloneDataSetHelper;
import Bean.Helper.TrainDataFileHelper;
import Bean.MethodPairSimVector;
import Features.FLDLFeatureRun;
import Features.MethodPairSimVectorHelper;
import MyTools.CSVUils;
import MyTools.FileUtils;
import StaticValue.MyXMLStaticValue;
import StaticValue.TrainDataFileStaticValue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/25.
 * 这个类的作用是进行人工数据集分类
 * 即在数据集训练之前，如果不存在明确的已标注的克隆对与非克隆对
 * 那么先通过该类进行人工形式上的数据集的分类构建，然后在进入分类器训练
 * .......
 * 相应地，如果存在明确的已标注、构建好的克隆对与非克隆对
 * 那么就不需要使用该类
 */
public class DataSetTagged {
    public static void main(String[] args) {
        String trainData_Path = "H:\\PostPaperMaterial\\IJaDataset_BCEvalVersion\\bcb_reduced\\4\\selected";
        List<String> paths = FileUtils.getJavaFileLists(trainData_Path);
        DataSetTagged dataSetTagged = new DataSetTagged();
        TrainDataFileHelper trainDataFileHelper = new TrainDataFileHelper();
        List<String> newPaths = new ArrayList<String>();
        //StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 20 * 6; i < 20 * 10; i++) {
            newPaths.add(paths.get(i));
        }
        trainDataFileHelper.setPaths(newPaths);
        CloneDataSetHelper cloneDataSetHelper = dataSetTagged.getCloneDataSet(trainDataFileHelper);
        //NonCloneDataSetHelper nonCloneDataSetHelper = dataSetTagged.getNonCloneDataSet(trainDataFileHelper);
        if (cloneDataSetHelper == null)
            System.out.println("Error!!!");
        else {
            System.out.println(cloneDataSetHelper.getMethodPairs());
            List<MethodPairSimVector> methodPairSimVectors = cloneDataSetHelper.getMethodPairSimVectors();
            MethodPairSimVectorHelper.convertToXMLFile(methodPairSimVectors);
        }


    }


    /**
     * 从原始混合的文件夹中得到克隆数据集
     *
     * @param trainDataFileHelper 原始混合文件夹路径
     * @return
     */
    public CloneDataSetHelper getCloneDataSet(TrainDataFileHelper trainDataFileHelper) {
        CloneDataSetHelper cloneDataSetHelper = new CloneDataSetHelper();
        List<MethodPairSimVector> methodPairSimVectors = new ArrayList<MethodPairSimVector>();
        if (trainDataFileHelper.getDirpath() != null) {
            String filePath = trainDataFileHelper.getDirpath();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(filePath, TrainDataFileStaticValue.CLONE);
        } else {
            List<String> paths = trainDataFileHelper.getPaths();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(paths, TrainDataFileStaticValue.CLONE);

        }
        List<MethodPairSimVector> cloneMethodPairSimVectors = new ArrayList<MethodPairSimVector>();
        if (methodPairSimVectors == null) {
            return null;
        }
        int methodPair_count = 0;
        //人工开始定义规则和划分
        for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
            if (isClone(methodPairSimVector)) {
                methodPair_count++;
                cloneMethodPairSimVectors.add(methodPairSimVector);
            }
        }
        cloneDataSetHelper.setMethodPairs(methodPair_count);
        cloneDataSetHelper.setMethodPairSimVectors(cloneMethodPairSimVectors);
        return cloneDataSetHelper;
    }

    public NonCloneDataSetHelper getNonCloneDataSet(TrainDataFileHelper trainDataFileHelper) {
        List<MethodPairSimVector> methodPairSimVectors = null;
        if (trainDataFileHelper.getDirpath() != null) {
            String filePath = trainDataFileHelper.getDirpath();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(filePath, TrainDataFileStaticValue.NONCLONE);
        } else {
            List<String> paths = trainDataFileHelper.getPaths();
            methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(paths, TrainDataFileStaticValue.NONCLONE);
        }
        List<MethodPairSimVector> nonCloneMethodPairSimVectors = new ArrayList<MethodPairSimVector>();
        if (methodPairSimVectors == null) {
            return null;
        }
        NonCloneDataSetHelper nonCloneDataSetHelper = new NonCloneDataSetHelper();
        int nonCloneMethodNum = 0;
        for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
            if (!isClone(methodPairSimVector)) {
                nonCloneMethodNum++;
                nonCloneMethodPairSimVectors.add(methodPairSimVector);
            }
        }
        nonCloneDataSetHelper.setMethodPairs(nonCloneMethodNum);
        nonCloneDataSetHelper.setMethodPairSimVectors(nonCloneMethodPairSimVectors);
        return nonCloneDataSetHelper;
        //CloneDataSetHelper cloneDataSetHelper = getCloneDataSet(filePath);

    }


    private Boolean checkSim(Double sim) {
        if (sim == 0.5 || sim >= 0.7) {
            return true;
        } else
            return false;
    }

    private Boolean checkSimSum(MethodPairSimVector methodPairSimVector) {
        Double[] sims = new Double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3()
                , methodPairSimVector.getLeSim4(), methodPairSimVector.getsSim5(), methodPairSimVector.getLeSim5(),
                methodPairSimVector.getsSim1(), methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(),
                methodPairSimVector.getsSim4(), methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
        Double sum = 0.0;
        int count = 0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                sum = sims[i] + sum;
                count++;
            }
        }
        if (sum >= count * 0.7) {
            return true;
        } else
            return false;
    }


    private Boolean isClone(MethodPairSimVector methodPairSimVector) {
        if (checkSim(methodPairSimVector.getLeSim1()) && checkSim(methodPairSimVector.getLeSim2()) && checkSim(methodPairSimVector.getLeSim3())
                && checkSim(methodPairSimVector.getLeSim4()) && checkSim(methodPairSimVector.getLeSim5())
                && checkSim(methodPairSimVector.getsSim1()) && checkSim(methodPairSimVector.getsSim2())
                && checkSim(methodPairSimVector.getsSim3()) && checkSim(methodPairSimVector.getsSim4())
                && checkSim(methodPairSimVector.getsSim5()) && checkSim(methodPairSimVector.getsSim6())
                && checkSim(methodPairSimVector.getFunSim())) {
            return true;
        } else if (checkSimSum(methodPairSimVector)) {
            return true;
        } else
            return false;
    }


    public void deleteFeaturesTonewFile(FeatureHelper featureHelper, String sourceTrainDataSetPath,String targetPath) throws IOException {
        List<String[]> records = CSVUils.readCSVFileToStringArrayList(sourceTrainDataSetPath);
        List<String[]> newList = new ArrayList<String[]>();
        switch (featureHelper) {
            case VARIABLENAME:
                newList = deleteArray(1, records);
                break;
            case TYPE:
                newList = deleteArray(2, records);
                break;
            case OPERATETYPE:
                newList = deleteArray(3, records);
                break;
            case METHODINVOKENAME:
                newList = deleteArray(4,records);
                break;
            case METHODINVOKEPARA:
                newList = deleteArray(5,records);
                break;
            case FOR:
                newList = deleteArray(6,records);
                break;
            case WHILE:
                newList = deleteArray(7,records);
                break;
            case SWITCH:
                newList = deleteArray(8,records);
                break;
            case DOWHILE:
                newList = deleteArray(9,records);
                break;
            case IF:
                newList = deleteArray(10,records);
                break;
            case WHOLESTRUCTURE:
                newList = deleteArray(11,records);
                break;
            case FUNCTION:
                newList = deleteArray(12,records);
                break;
            default:
                break;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i=0;i<newList.size();i++){
            String[] arr = newList.get(i);
            for (int j=0;j<arr.length;j++){
                if (j != arr.length-1) {
                    stringBuilder.append(arr[j]);
                    stringBuilder.append(MyXMLStaticValue.XMLSPILT);
                }else {
                    stringBuilder.append(arr[j]);
                }
            }
            if (i != newList.size()-1){
                stringBuilder.append("\n");
            }
        }
        FileUtils.writeToFiles(stringBuilder.toString(),targetPath);
    }

    private List<String[]> deleteArray(int index, List<String[]> list) {
        List<String[]> newList = new ArrayList<String[]>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++){
                String[] strings = new String[list.get(i).length-1];
                String[] arrs = list.get(i);
                for (int j=0;j<arrs.length;j++){
                    if (j != index){
                        strings[j]=arrs[j];
                    }
                }
                newList.add(strings);
            }
        }
        return newList;
    }


}
