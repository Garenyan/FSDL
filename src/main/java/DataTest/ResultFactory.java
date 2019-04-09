package DataTest;

import Bean.Helper.FeatureHelper;
import Bean.Helper.ResultHelper;
import Bean.MethodPairSimVector;
import DataTrain.ParameterStaticValue;
import MyTools.FileUtils;
import MyTools.TimeUtils;
import StaticValue.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;

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
    private static double f = 0.0;

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
                Double funSim = doubles[11];
                if (checkType_1(doubles)) {
                    type1_num++;
                } else if (checkType_2(doubles)) {
                    type2_num++;
                } else if (checkTypeST3(doubles)) {
                    typeST3_num++;
                } else if (checkTypeMT3(doubles, featureHelper, funSim)) {
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
            getTestTrueNum(testMethodPairSimVectors, featureHelper);
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
                System.out.println("type1总个数：" + type1_num);
            }
            if (flag) {
                if (type2_num == 0) {
                    System.out.println("type2召回率：--");
                } else {
                    System.out.println("type2召回率：" + test_type2_num / (type2_num * 1.0));
                    System.out.println("type2个数：" + test_type2_num);
                    System.out.println("type2总个数：" + type2_num);
                }
            }
            if (typeST3_num == 0) {
                System.out.println("typeST3召回率：--");
            } else {
                System.out.println("typeST3召回率：" + test_typeST3_num / (typeST3_num * 1.0));
                System.out.println("typeST3个数：" + test_typeST3_num);
                System.out.println("typeST3总个数：" + typeST3_num);
            }
            if (typeMT3_num == 0) {
                System.out.println("typeMT3召回率：--");
            } else {
                System.out.println("typeMT3召回率：" + test_typeMT3_num / (typeMT3_num * 1.0));
                System.out.println("typeMT3个数：" + test_typeMT3_num);
                System.out.println("typeMT3总个数：" + typeMT3_num);
            }
            if (type4_num == 0) {
                System.out.println("type4召回率：--");
            } else {
                System.out.println("type4召回率：" + test_type4_num / (type4_num * 1.0));
                System.out.println("type4个数：" + test_type4_num);
                System.out.println("type4总个数：" + type4_num);
            }
        }
        String modelName = resultHelper.getModelName();
        ParameterStaticValue parameterStaticValue = new ParameterStaticValue();
        createMeasureResultTxtFiles(modelName, parameterStaticValue);
        createResultXlsx(modelName, parameterStaticValue);
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

    private static Boolean checkTypeMT3(Double[] sims, FeatureHelper featureHelper, Double funSim) {
        int num = 0;
        Double sum = 0.0;
        for (int i = 0; i < sims.length; i++) {
            if (sims[i] != 0.5) {
                num++;
                sum = sum + sims[i];
            }
        }
        if (featureHelper == FeatureHelper.FUNCTION) {
            if (sum < 0.7 * num && sum >= 0.68 * num && funSim >= 0.98) {
                return true;
            } else {
                return false;
            }
        } else if (featureHelper == FeatureHelper.NULLFEATURE) {
            if (sum < 0.7 * num && sum >= 0.68 * num && sims[11] >= 0.98) {
                return true;
            } else {
                return false;
            }
        } else {
            if (sum < 0.7 * num && sum >= 0.68 * num && sims[10] >= 0.98) {
                return true;
            } else {
                return false;
            }

        }

    }

    private static void getTestTrueNum(List<MethodPairSimVector> tests, FeatureHelper featureHelper) {
        if (tests == null) {
            test_true_num = 0;
        } else {
            for (int i = 0; i < tests.size(); i++) {
                Double[] sims = new Double[]{tests.get(i).getLeSim1(), tests.get(i).getLeSim2(), tests.get(i).getLeSim3(),
                        tests.get(i).getLeSim4(), tests.get(i).getLeSim5(), tests.get(i).getsSim1(), tests.get(i).getsSim2(),
                        tests.get(i).getsSim3(), tests.get(i).getsSim4(), tests.get(i).getsSim5(), tests.get(i).getsSim6(),
                        tests.get(i).getFunSim()};
                Double[] finalsims = getSimDoubles(featureHelper, sims);
                Double funSim = sims[11];
                if (isClone(sims, featureHelper, funSim)) {
                    test_true_num++;
                    if (checkType_1(finalsims)) {
                        test_type1_num++;
                    } else if (checkType_2(finalsims)) {
                        test_type2_num++;
                    } else if (checkTypeST3(finalsims)) {
                        test_typeST3_num++;
                    } else if (checkTypeMT3(finalsims, featureHelper, funSim)) {
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
    private static Boolean isClone(Double[] sims, FeatureHelper featureHelper, Double funSim) {
        int num = 0;
        Double sum = 0.0;
        Double[] doubles = getSimDoubles(featureHelper, sims);
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
            if (featureHelper == FeatureHelper.FUNCTION) {
                if (sum >= 0.68 * num && funSim >= 0.98) {
                    return true;
                }
                if (sum < 0.68 * num && funSim >= 0.98) {//允许极少量的type-4 当然也许不存在
                    return true;
                }
                return false;
            } else if (featureHelper == FeatureHelper.NULLFEATURE) {
                if (sum >= 0.68 * num && doubles[11] >= 0.98) {
                    return true;
                }
                if (sum < 0.68 * num && doubles[11] >= 0.98) {//允许极少量的type-4 当然也许不存在
                    return true;
                }
                return false;
            } else {
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

    private static Double[] transformToDoubles(String[] strings) {
        Double[] doubles = new Double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            doubles[i] = Double.valueOf(strings[i]);
        }
        return doubles;
    }

    private static void createMeasureResultTxtFiles(String modelName, ParameterStaticValue parameterStaticValue) {

        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("测试时间为：");
        stringBuilder.append(TimeUtils.getTimeAndDateStringFormat(new Date(), "yyy-MM-dd HH-mm-ss"));
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
        //int seed;
        int nEpoch = 0;
        int batchsize = 0;
        Double learningRate = 0.0;
        int inputNum = 0;
        int outputNum = 0;
        int hiddenNum = 0;

        List<LinkedHashMap<String, String>> mapList = parseModelInfoTxt();
        if (mapList != null) {
            for (int i = 0; i < mapList.size(); i++) {
                LinkedHashMap<String, String> map = mapList.get(i);
                if (map.get("model名称").contains(modelName)) {
                    learningRate = Double.valueOf(map.get("学习率"));
                    nEpoch = Integer.valueOf(map.get("迭代次数"));
                    batchsize = Integer.valueOf(map.get("Batchsize"));
                    inputNum = Integer.valueOf(map.get("输入层结点个数"));
                    hiddenNum = Integer.valueOf(map.get("隐藏层结点个数"));
                    outputNum = Integer.valueOf(map.get("输入层结点个数"));
                    break;
                }
            }
        }


        stringBuilder.append("随机数种子(seed)：");
        stringBuilder.append(parameterStaticValue.seed);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("迭代次数(nEpoch)：");
        stringBuilder.append(nEpoch);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("学习率：");
        stringBuilder.append(learningRate);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("批处理尺寸：");
        stringBuilder.append(batchsize);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("输入层结点个数：");
        stringBuilder.append(inputNum);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("隐藏层结点个数：");
        stringBuilder.append(hiddenNum);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("输出层结点个数：");
        stringBuilder.append(outputNum);
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
        stringBuilder.append("准确的代码克隆总数：");
        stringBuilder.append(test_true_num);
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的T1代码克隆总数：");
        stringBuilder.append(test_type1_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T1个数：");
        stringBuilder.append(type1_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T1召回率：");
        stringBuilder.append(test_type1_num / (type1_num * 1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);

        stringBuilder.append("预测出的T2代码克隆总数：");
        stringBuilder.append(test_type2_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T2个数：");
        stringBuilder.append(type2_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T2召回率：");
        stringBuilder.append(test_type2_num / (type2_num * 1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的ST3代码克隆总数：");
        stringBuilder.append(test_typeST3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中ST3个数：");
        stringBuilder.append(typeST3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("ST3召回率：");
        stringBuilder.append(test_typeST3_num / (typeST3_num * 1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的MT3代码克隆总数：");
        stringBuilder.append(test_typeMT3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中MT3个数：");
        stringBuilder.append(typeMT3_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("MT3召回率：");
        stringBuilder.append(test_typeMT3_num / (typeMT3_num * 1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append("预测出的T4代码克隆总数：");
        stringBuilder.append(test_type4_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("基准中T4个数：");
        stringBuilder.append(type4_num);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        stringBuilder.append("T4召回率：");
        stringBuilder.append(test_type4_num / (type4_num * 1.0));
        stringBuilder.append(MeasureFileStaticValue.NEWLINE);
        stringBuilder.append(MeasureFileStaticValue.SPACE);
        FileUtils.writefiles(stringBuilder.toString(), MeasureFileStaticValue.FILEPATH);


    }


    private static void createResultXlsx(String modelName, ParameterStaticValue parameterStaticValue) {
        File file = new File(ExcelStaticValue.RESULTEXCELPATH);
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet1 = wb.getSheet(ExcelStaticValue.SHEETNAME);
        int lastRow = sheet1.getLastRowNum();
        int insertStartPointer = lastRow + 1;
        XSSFRow row = createRow(sheet1, insertStartPointer);
        Properties properties = System.getProperties();
        row.createCell(0).setCellValue(TimeUtils.getTimeAndDateStringFormat(new Date(), "yyy-MM-dd HH-mm-ss"));
        row.createCell(1).setCellValue(properties.getProperty("java.version"));
        row.createCell(2).setCellValue(properties.getProperty("os.name"));
        row.createCell(3).setCellValue(parameterStaticValue.x);
        row.createCell(4).setCellValue(StructureStaticValue.DT);
        row.createCell(5).setCellValue(parameterStaticValue.Clone_Threshold);
        int nEpoch = 0;
        int batchsize = 0;
        Double learningRate = 0.0;
        int inputNum = 0;
        int outputNum = 0;
        int hiddenNum = 0;

        List<LinkedHashMap<String, String>> mapList = parseModelInfoTxt();
        if (mapList != null) {
            for (int i = 0; i < mapList.size(); i++) {
                LinkedHashMap<String, String> map = mapList.get(i);
                if (map.get("model名称").contains(modelName)) {
                    learningRate = Double.valueOf(map.get("学习率"));
                    nEpoch = Integer.valueOf(map.get("迭代次数"));
                    batchsize = Integer.valueOf(map.get("Batchsize"));
                    inputNum = Integer.valueOf(map.get("输入层结点个数"));
                    hiddenNum = Integer.valueOf(map.get("隐藏层结点个数"));
                    outputNum = Integer.valueOf(map.get("输入层结点个数"));
                    break;
                }
            }
        }
        row.createCell(6).setCellValue(learningRate);
        row.createCell(7).setCellValue(nEpoch);
        row.createCell(8).setCellValue(inputNum);
        row.createCell(9).setCellValue(outputNum);
        row.createCell(10).setCellValue(hiddenNum);
        row.createCell(11).setCellValue(parameterStaticValue.seed);
        row.createCell(12).setCellValue(batchsize);
        row.createCell(13).setCellValue(precisipn);
        row.createCell(14).setCellValue(recall);
        row.createCell(15).setCellValue(f);
        row.createCell(16).setCellValue(test_num);
        row.createCell(17).setCellValue(test_true_num);
        row.createCell(18).setCellValue(benchmark_num);
        row.createCell(19).setCellValue(test_type1_num);
        row.createCell(20).setCellValue(test_type2_num);
        row.createCell(21).setCellValue(test_typeST3_num);
        row.createCell(22).setCellValue(test_typeMT3_num);
        row.createCell(23).setCellValue(test_type4_num);
        row.createCell(24).setCellValue(type1_num);
        row.createCell(25).setCellValue(type2_num);
        row.createCell(26).setCellValue(typeST3_num);
        row.createCell(27).setCellValue(typeMT3_num);
        row.createCell(28).setCellValue(type4_num);
        row.createCell(29).setCellValue(test_type1_num / (type1_num * 1.0));
        row.createCell(30).setCellValue(test_type2_num / (type2_num * 1.0));
        row.createCell(31).setCellValue(test_typeST3_num / (typeST3_num * 1.0));
        row.createCell(32).setCellValue(test_typeMT3_num / (typeMT3_num * 1.0));
        row.createCell(33).setCellValue(test_type4_num / (type4_num * 1.0));
        saveExcel(wb);
    }

    private static void saveExcel(XSSFWorkbook wb) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(ExcelStaticValue.RESULTEXCELPATH);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {
        XSSFRow row = null;
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            sheet.shiftRows(rowIndex, lastRowNo, 1);
        }
        row = sheet.createRow(rowIndex);
        return row;
    }

    private static XSSFWorkbook returnWorkBookGivenFileHandle() {
        XSSFWorkbook wb = null;
        FileInputStream fis = null;
        File f = new File(ExcelStaticValue.RESULTEXCELPATH);
        try {
            if (f != null) {
                fis = new FileInputStream(f);
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wb;
    }

    public static List<LinkedHashMap<String, String>> parseModelInfoTxt() {
        List<String> contents = FileUtils.readFile(TrainDataFileStaticValue.MODELPARAFILE);
        if (contents == null) {
            return null;
        }
        List<LinkedHashMap<String, String>> mapList = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> infoMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < contents.size(); i++) {
            if (!contents.get(i).equals("")) {
                String keyValue[] = contents.get(i).split(":");
                if (keyValue.length == 1) {
                    continue;
                }
                infoMap.put(keyValue[0], keyValue[1]);
            } else {
                mapList.add(infoMap);
                infoMap = new LinkedHashMap<String, String>();
            }
        }
        return mapList;
//        for (int i=0;i<lists.size();i++){
//            for (int j=0;j<)
//        }

    }
}
