package DataTest;

import Bean.Helper.FeatureHelper;
import Bean.Helper.ResultHelper;
import Bean.MethodPairSimVector;
import DataTrain.ParameterStaticValue;
import MyTools.FileUtils;
import MyTools.TimeUtils;
import StaticValue.MeasureFileStaticValue;
import StaticValue.MyXMLStaticValue;
import StaticValue.StructureStaticValue;
import fj.P;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by garen on 2019/3/23.
 */
public class ResultFactory {
    private static int type1_num = 0;
    private static int type2_num = 0;
    private static int typeST3_num = 0;
    private static int typeMT3_num = 0;
    private static int type4_num = 0;

    private static int test_type1_num = 0;
    private static int test_type2_num = 0;
    private static int test_typeST3_num = 0;
    private static int test_typeMT3_num = 0;
    private static int test_type4_num = 0;

    private static int benchmark_num = 0;
    private static int test_num = 0;
    private static int test_true_num = 0;

    private static double precisipn = 0.0;
    private static double recall = 0.0;
    private static double f=0.0;

    private static void getPrecision() {
        precisipn = test_true_num / (test_num * 1.0);
        System.out.println("总精确度为：" + precisipn);
    }

    private static void getRecall() {
        recall = test_true_num / (benchmark_num * 1.0);
        System.out.println("总召回率为：" + recall);
    }

    private static void getFValue() {
        f = 2 * (precisipn * recall) / (precisipn + recall);
        System.out.println("总F-measure值为：" + f);
    }


    public static void getResult(ResultHelper resultHelper) {
        //解析基准文件，并得到具体类型划分
        String xmlPath = resultHelper.getBenchmarkFileXMLpath();
        SAXReader saxReader = new SAXReader();
        FeatureHelper featureHelper = resultHelper.getNonFeature();
        try {
            Document document = saxReader.read(new File(xmlPath));
            Element rootElement = document.getRootElement();
            //System.out.println(rootElement.getName());
            List<Element> methodPairElements = rootElement.elements(MyXMLStaticValue.METHODPAIRELEMENT);
            //System.out.println(methodPairElements.size());
            benchmark_num = methodPairElements.size();
            for (int i = 0; i < benchmark_num; i++) {
                Element element = methodPairElements.get(i).element(MyXMLStaticValue.SIMVECTOR);
                String text = element.getText();
                String[] simStrs = text.split(MyXMLStaticValue.XMLSPILT);
                featureHelper = FeatureHelper.NULLFEATURE;
                Double[] doubles = transformToDoubles(simStrs);
                Double funSim=doubles[11];
                if (checkType_1(doubles)) {
                    type1_num++;
                } else if (checkType_2(doubles)) {
                    type2_num++;
                } else if (checkTypeST3(doubles)) {
                    typeST3_num++;
                } else if (checkTypeMT3(doubles,featureHelper,funSim)) {
                    typeMT3_num++;
                } else {
                    type4_num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MethodPairSimVector> testMethodPairSimVectors = resultHelper.getTestMethodPairSimVectors();
        if (testMethodPairSimVectors == null) {
            getPrecision();
            getRecall();
            getFValue();
        } else {
            test_num = testMethodPairSimVectors.size();
            getTestTrueNum(testMethodPairSimVectors,featureHelper);
            getPrecision();
            getRecall();
            getFValue();
            Boolean flag = true;
            if (type1_num == 0) {
                flag = false;
                System.out.println("type1/2召回率：" + test_type2_num / (type2_num * 1.0));
            } else {
                System.out.println("type1召回率：" + test_type1_num / (type1_num * 1.0));
                System.out.println("type1个数：" + test_type1_num);
                System.out.println("type1总个数："+type1_num);
            }
            if (flag) {
                if (type2_num == 0) {
                    System.out.println("type2召回率：--");
                } else {
                    System.out.println("type2召回率：" + test_type2_num / (type2_num * 1.0));
                    System.out.println("type2个数：" + test_type2_num);
                    System.out.println("type2总个数："+type2_num);
                }
            }
            if (typeST3_num == 0) {
                System.out.println("typeST3召回率：--");
            } else {
                System.out.println("typeST3召回率：" + test_typeST3_num / (typeST3_num * 1.0));
                System.out.println("typeST3个数：" + test_typeST3_num);
                System.out.println("typeST3总个数："+typeST3_num);
            }
            if (typeMT3_num == 0) {
                System.out.println("typeMT3召回率：--");
            } else {
                System.out.println("typeMT3召回率：" + test_typeMT3_num / (typeMT3_num * 1.0));
                System.out.println("typeMT3个数：" + test_typeMT3_num);
                System.out.println("typeMT3总个数："+typeMT3_num);
            }
            if (type4_num == 0) {
                System.out.println("type4召回率：--");
            } else {
                System.out.println("type4召回率：" + test_type4_num / (type4_num * 1.0));
                System.out.println("type4个数：" + test_type4_num);
                System.out.println("type4总个数："+type4_num);
            }
        }
        createMeasureResultFiles();
    }

    private static Boolean checkType_1(Double[] sims) {
        int count = 0;
        double sum = 0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                count++;
                sum = sum + sims[i];
            }
        }
        if (sum >= 0.9998 * count) {
            return true;
        } else {
            return false;
        }

    }

    private static Boolean checkType_2(Double[] sims) {
        int num = 0;
        Double sum = 0.0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                num++;
                sum = sum + sims[i];
            }
        }
        if (sum < 0.9998 * num && sum >= 0.98 * num) {
            return true;
        } else {
            return false;
        }
    }

    private static Boolean checkTypeST3(Double[] sims) {
        int num = 0;
        Double sum = 0.0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                num++;
                sum = sum + sims[i];
            }
        }
        if (sum < 0.98 * num && sum >= 0.7 * num) {
            return true;
        } else {
            return false;
        }
    }

    private static Boolean checkTypeMT3(Double[] sims, FeatureHelper featureHelper,Double funSim) {
        int num = 0;
        Double sum = 0.0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                num++;
                sum = sum + sims[i];
            }
        }
        if (featureHelper == FeatureHelper.FUNCTION){
            if (sum < 0.7 * num && sum >= 0.68 * num && funSim >= 0.98) {
                return true;
            } else {
                return false;
            }
        }else if (featureHelper == FeatureHelper.NULLFEATURE) {
            if (sum < 0.7 * num && sum >= 0.68 * num && sims[11] >= 0.98) {
                return true;
            } else {
                return false;
            }
        }else {
            if (sum < 0.7 * num && sum >= 0.68 * num && sims[10] >= 0.98) {
                return true;
            } else {
                return false;
            }

        }

    }

    private static void getTestTrueNum(List<MethodPairSimVector> tests,FeatureHelper featureHelper) {
        if (tests == null) {
            test_true_num = 0;
        } else {
            for (int i = 0; i < tests.size(); i++) {
                Double[] sims = new Double[]{tests.get(i).getLeSim1(), tests.get(i).getLeSim2(), tests.get(i).getLeSim3(),
                        tests.get(i).getLeSim4(), tests.get(i).getLeSim5(), tests.get(i).getsSim1(), tests.get(i).getsSim2(),
                        tests.get(i).getsSim3(), tests.get(i).getsSim4(), tests.get(i).getsSim5(), tests.get(i).getsSim6(),
                        tests.get(i).getFunSim()};
                Double[] finalsims = getSimDoubles(featureHelper,sims);
                Double funSim = sims[11];
                if (isClone(sims,featureHelper,funSim)) {
                    test_true_num++;
                    if (checkType_1(finalsims)) {
                        test_type1_num++;
                    } else if (checkType_2(finalsims)) {
                        test_type2_num++;
                    } else if (checkTypeST3(finalsims)) {
                        test_typeST3_num++;
                    } else if (checkTypeMT3(finalsims,featureHelper,funSim)) {
                        test_typeMT3_num++;
                    } else {
                        test_type4_num++;
                    }
                }
            }
        }
    }

    /**
     * 人工判断检测结果里是否是克隆，如果能够读取相应的基准文件，进行文件对比，那是最标准的
     * 但是这种判断方式虽然存在不精准，但是也大致准确！
     *
     * @param sims
     * @return
     */
    private static Boolean isClone(Double[] sims,FeatureHelper featureHelper,Double funSim) {
        int num = 0;
        Double sum = 0.0;
        Double[] doubles = getSimDoubles(featureHelper,sims);
//       if (sims[11] < 0.5 || sims[10] < 0.7) {
//            return false;
//        } else {
            for (int i = 0; i < doubles.length; i++) {
                if (doubles[i] != 0.5) {
                    num++;
                    sum = sum + doubles[i];
                }
            }
            if (sum >= 0.7 * num) {
                return true;
            } else {
                //允许部分少量MT-3
                if (featureHelper == FeatureHelper.FUNCTION){
                    if (sum >= 0.68 * num && funSim >= 0.98) {
                        return true;
                    }
                    if (sum < 0.68 * num && funSim >= 0.98) {//允许极少量的type-4 当然也许不存在
                        return true;
                    }
                    return false;
                }else if (featureHelper == FeatureHelper.NULLFEATURE) {
                    if (sum >= 0.68 * num && doubles[11] >= 0.98) {
                        return true;
                    }
                    if (sum < 0.68 * num && doubles[11] >= 0.98) {//允许极少量的type-4 当然也许不存在
                        return true;
                    }
                    return false;
                }else {
                    if (sum >= 0.68 * num && doubles[10] >= 0.98) {
                        return true;
                    }
                    if (sum < 0.68 * num && doubles[10] >= 0.98) {//允许极少量的type-4 当然也许不存在
                        return true;
                    }
                    return false;
                }
            }
    //    }
    }


    private static Double[] getSimDoubles(FeatureHelper nonfeatureHelper, Double[] simStrs) {
        Double[] sims;
        if (nonfeatureHelper == FeatureHelper.NULLFEATURE) {
            sims = new Double[12];
        } else
            sims = new Double[11];
        switch (nonfeatureHelper) {
            case VARIABLENAME:
                sims = NotContains(0, simStrs, 11);
                break;
            case TYPE:
                sims = NotContains(1, simStrs, 11);
                break;
            case OPERATETYPE:
                sims = NotContains(2, simStrs, 11);
                break;
            case METHODINVOKENAME:
                sims = NotContains(3, simStrs, 11);
                break;
            case METHODINVOKEPARA:
                sims = NotContains(4, simStrs, 11);
                break;
            case FOR:
                sims = NotContains(5, simStrs, 11);
                break;
            case WHILE:
                sims = NotContains(6, simStrs, 11);
                break;
            case SWITCH:
                sims = NotContains(7, simStrs, 11);
                break;
            case DOWHILE:
                sims = NotContains(8, simStrs, 11);
                break;
            case IF:
                sims = NotContains(9, simStrs, 11);
                break;
            case WHOLESTRUCTURE:
                sims = NotContains(10, simStrs, 11);
                break;
            case FUNCTION:
                sims = NotContains(11, simStrs, 11);
                break;
            default:
                sims = NotContains(12, simStrs, 12);
                break;
        }
        return sims;
    }


    private static Double[] NotContains(int index, Double[] strings, int length) {
        Double[] sims = new Double[length];
        if (index <= length && index > 0) {
            for (int i = 0; i < strings.length; i++) {
                if (i != index) {
                    sims[i] = strings[i];
                }
            }
        }
        return sims;
    }

    private static Double[] transformToDoubles(String[] strings){
        Double[] doubles = new Double[strings.length];
        for (int i=0;i<strings.length;i++){
            doubles[i] = Double.valueOf(strings[i]);
        }
        return doubles;
    }

    private static void createMeasureResultFiles(){
        ParameterStaticValue parameterStaticValue = new ParameterStaticValue();
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("测试时间为：");
        stringBuilder.append(TimeUtils.getTimeAndDateStringFormat(new Date(),"yyy-MM-dd HH-mm-ss"));
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        Properties properties = System.getProperties();
        stringBuilder.append("JAVA的运行版本：");
        stringBuilder.append(properties.getProperty("java.version"));
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("运行的操作系统名称：");
        stringBuilder.append(properties.getProperty("os.name"));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);

        stringBuilder.append("该测试设置的所有参数：");
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("一级相似度阈值:");
        stringBuilder.append(parameterStaticValue.x);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("DT阈值：");
        stringBuilder.append(StructureStaticValue.DT);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("克隆阈值：");
        stringBuilder.append(parameterStaticValue.Clone_Threshold);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);

        stringBuilder.append("随机数种子(seed)：");
        stringBuilder.append(parameterStaticValue.seed);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("迭代次数(nEpoch)：");
        stringBuilder.append(parameterStaticValue.nEpochs);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("学习率：");
        stringBuilder.append(parameterStaticValue.learningRate);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("批处理尺寸：");
        stringBuilder.append(parameterStaticValue.batchsize);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("输入层结点个数：");
        stringBuilder.append(parameterStaticValue.numInputs);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("隐藏层结点个数：");
        stringBuilder.append(parameterStaticValue.numHiddenNodes);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("输出层结点个数：");
        stringBuilder.append(parameterStaticValue.numOutputs);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);

        stringBuilder.append("实验结果：");
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("总精确度：");
        stringBuilder.append(precisipn);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("总召回率：");
        stringBuilder.append(recall);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("总F-measure值：");
        stringBuilder.append(f);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);


        stringBuilder.append("预测出的代码克隆总数：");
        stringBuilder.append(test_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中代码克隆总数：");
        stringBuilder.append(test_true_num);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的T1代码克隆总数：");
        stringBuilder.append(test_type1_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T1个数：");
        stringBuilder.append(type1_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T1召回率：");
        stringBuilder.append(test_type1_num/(type1_num*1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);

        stringBuilder.append("预测出的T2代码克隆总数：");
        stringBuilder.append(test_type2_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T2个数：");
        stringBuilder.append(type2_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T2召回率：");
        stringBuilder.append(test_type2_num/(type2_num*1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的ST3代码克隆总数：");
        stringBuilder.append(test_typeST3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中ST3个数：");
        stringBuilder.append(typeST3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("ST3召回率：");
        stringBuilder.append(test_typeST3_num/(typeST3_num*1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的MT3代码克隆总数：");
        stringBuilder.append(test_typeMT3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中MT3个数：");
        stringBuilder.append(typeMT3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("MT3召回率：");
        stringBuilder.append(test_typeMT3_num/(typeMT3_num*1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的T4代码克隆总数：");
        stringBuilder.append(test_type4_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T4个数：");
        stringBuilder.append(type4_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T4召回率：");
        stringBuilder.append(test_type4_num/(type4_num*1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        FileUtils.writefiles(stringBuilder.toString(),MeasureFileStaticValue.FILEPATH);


    }
}
