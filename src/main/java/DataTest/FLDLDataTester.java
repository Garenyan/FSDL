package DataTest;

import Bean.Helper.FeatureHelper;
import Bean.Helper.ResultHelper;
import Bean.MethodPairSimVector;
import DataTrain.ConvertToFiles;
import DataTrain.ParameterStaticValue;
import Features.FLDLFeatureRun;
import MyTools.FileUtils;
import MyTools.MyThread;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/25.
 */
public class FLDLDataTester {
    public static void main(String[] args) throws IOException {
        String test_dirpath = "H:\\PostPaperMaterial\\IJaDataset_BCEvalVersion\\bcb_reduced\\2\\default";//测试文件夹路径，如果不同目录，不同测试路径，需修改！
        //基准文件路基，基准文件产生步骤：基准代码（人工标记或者数据库标记）--FLDLFeatureRun提取特征并计算相似度值
        //--MethodPairSimVectorHelper类（位于Features包下）产生方法对XML文件(Main方法可能要自己写一下)
        String benchmark_xml_path = "H:\\workarea\\LSFrame\\benchmarkXMLFiles\\0.3folder2TestBenchmark_Clone.xml";
        String model_FileName ="model104";//已训练好的神经网络模型文件
        String model_File = "H:\\workarea\\LSFrame\\TrainDataFiles\\TrainDataSetOutput\\"+model_FileName+".mdl"; //训练模型可不断调节，路径注意修改
         FLDLDataTester fldlDataTester = new FLDLDataTester();
         fldlDataTester.run(test_dirpath,benchmark_xml_path,model_File,FeatureHelper.NULLFEATURE);//运行测试，输出结果，FeatureHelper.NullFEATURE这个表示完整的特征测试，如果要做缺少特征的对比实验，注意修改
    }

    public void run(String test_path,String benchmark_xml_path,String modelFile,FeatureHelper featureHelper) throws IOException {
        File file = new File(modelFile);
        String model_FileName = file.getName();
        System.out.println("----------欢迎使用DCCDL框架检测代码克隆------");
        MyThread.waitForTime(2000);
        //FeatureHelper featureHelper = FeatureHelper.NULLFEATURE;
        System.out.println("-----------正在解析测试数据集，请稍后......---------------");
        MyThread.waitForTime(2000);
        List<String> paths = FileUtils.getJavaFileLists(test_path);
        List<String> newPaths = new ArrayList<String>();
        /**选择其中的200个文件进行测试，只有基准对应，且你的JVM内存能够支撑，那可以去掉以下代码，并进行完整测试。**/
        for (int i = 0; i < 200; i++) {
            newPaths.add(paths.get(i));
        }
        MyThread.waitForTime(2000);
        System.out.println("--------测试数据集解析完毕------------");
        /**得到所有要测试（检测）的代码克隆的相似度向量**/
        System.out.println("-------------开始提取特征，请稍后...---------------");
        long start1 = System.currentTimeMillis();
        List<MethodPairSimVector> methodPairSimVectors = FLDLFeatureRun.getVectorFromFiles(newPaths);
        long end1 = System.currentTimeMillis();
        System.out.println("----------特征提取完成------------------");
        System.out.println("------特征提取花费时间：" + (end1-start1)+"ms-----------");
        MyThread.waitForTime(2000);
        /**加载训练模型，最后测试**/
        System.out.println("-------------正在加载模型，模型名为:"+model_FileName+"--------------------");
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        MyThread.waitForTime(2000);
        int trueNum = 0;
        List<MethodPairSimVector> trueMethodResults = new ArrayList<MethodPairSimVector>();
        System.out.println("-------------模型加载完毕，开始预测和分类数据---------------------");
        long start2 = System.currentTimeMillis();
        trueMethodResults = getTrueMethodPairSimVectors(featureHelper,methodPairSimVectors,model);
        long end2 = System.currentTimeMillis();
        System.out.println("---------------测试数据测试分类完毕!!!-----------------------");
        System.out.println("----------数据预测和分类时间为："+(end2-start2)+"ms");
        MyThread.waitForTime(2000);
        trueNum = trueMethodResults.size();
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setNonFeature(featureHelper);
        resultHelper.setTestMethodPairSimVectors(trueMethodResults);
        resultHelper.setModelName(model_FileName);
        resultHelper.setBenchmarkFileXMLpath(benchmark_xml_path);
        System.out.println("----------正在处理结果，即将输出精确度、召回率和F-measure值指标---------------");
        MyThread.waitForTime(2000);
        System.out.println("---基于DCCDL框架检测出的代码克隆对数量为："+trueNum);
        ResultFactory.getResult(resultHelper);
        System.out.println("----------结果输出完毕，欢迎继续使用DCCDL框架检测代码克隆！--------------");
    }


    private static void writeToCSVFileWithoutLabel(List<MethodPairSimVector> methodPairSimVectors) {
        StringBuilder stringBuilder = new StringBuilder("");
        ConvertToFiles convertToFiles = new ConvertToFiles();
        convertToFiles.getDataFileWithoutLabel(methodPairSimVectors, "csv");
    }

    private static void writeToCSVFileWithLabel(List<MethodPairSimVector> methodPairSimVectors) {
        StringBuilder stringBuilder = new StringBuilder("");
        ConvertToFiles convertToFiles = new ConvertToFiles();
        convertToFiles.getDataFileWithLabel(methodPairSimVectors, "csv");
    }


    private static List<MethodPairSimVector> getTrueMethodPairSimVectors(FeatureHelper nonFeature, List<MethodPairSimVector> methodPairSimVectors, MultiLayerNetwork model) {
        ParameterStaticValue parameterStaticValue = new ParameterStaticValue();
        List<MethodPairSimVector> list = new ArrayList<MethodPairSimVector>();
        switch (nonFeature) {
            case VARIABLENAME:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case TYPE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case OPERATETYPE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case METHODINVOKENAME:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case METHODINVOKEPARA:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case FOR:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case WHILE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case SWITCH:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case DOWHILE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case IF:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case WHOLESTRUCTURE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case FUNCTION:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
            case NULLFEATURE:
                for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                    double[] matrix = new double[]{methodPairSimVector.getLeSim1(), methodPairSimVector.getLeSim2(), methodPairSimVector.getLeSim3(),
                            methodPairSimVector.getLeSim4(), methodPairSimVector.getLeSim5(), methodPairSimVector.getsSim1(),
                            methodPairSimVector.getsSim2(), methodPairSimVector.getsSim3(), methodPairSimVector.getsSim4(), methodPairSimVector.getsSim5(),
                            methodPairSimVector.getsSim6(), methodPairSimVector.getFunSim()};
                    INDArray sim_Matrix = Nd4j.create(matrix);
                    INDArray predicted = model.output(sim_Matrix);
                    if (checkCloneThreshold(predicted,parameterStaticValue.Clone_Threshold)) {
                        list.add(methodPairSimVector);
                    }
                }
                break;
        }
        return list;
    }

    private static Boolean checkCloneThreshold(INDArray indArray,Double CloneThreshold) {
        if (indArray.getDouble(1) >= CloneThreshold) {
            return true;
        } else
            return false;
    }


}
