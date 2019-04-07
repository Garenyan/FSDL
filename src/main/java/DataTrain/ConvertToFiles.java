package DataTrain;

import Bean.MethodPairSimVector;
import MyTools.FileUtils;
import StaticValue.MyXMLStaticValue;
import StaticValue.TrainDataFileStaticValue;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;






import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2019/3/9.
 */
public class ConvertToFiles {
    public Boolean getTrainDataNameText(List<MethodPairSimVector> methodPairSimVectors){
        StringBuilder stringBuilder = new StringBuilder("");
        String path = TrainDataFileStaticValue.TRAINDATANAMEFILE;
        int size = methodPairSimVectors.size();
        stringBuilder.append("训练数据集共计：");
        stringBuilder.append(size);
        stringBuilder.append("条目");
        stringBuilder.append("\n");


       // String sourceFilePath =  methodPairSimVector.getSourceMethod().getFilepath();
        //String targetFilePath = methodPairSimVector.getTargetMethod().getFilepath();
        for (MethodPairSimVector methodPairSimVector:methodPairSimVectors) {
            String sourceMethodName = methodPairSimVector.getSourceMethod().getMethodName().toString();
            String targetMethodName = methodPairSimVector.getTargetMethod().getMethodName().toString();
            stringBuilder.append(sourceMethodName);
            stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
            stringBuilder.append(targetMethodName);
            stringBuilder.append("\n");
        }
        if (FileUtils.writeToFiles(stringBuilder.toString(),path))
        {
            return true;
        }
        else
            return false;
    }

    public void getDataFileWithoutLabel(List<MethodPairSimVector> methodPairSimVectors, String fileType)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        String path;
        if (!fileType.contains(".")) {
           path = TrainDataFileStaticValue.TRAINDATAFILE + "testFile." + fileType.toLowerCase();
        }else
            path = TrainDataFileStaticValue.TRAINDATAFILE+"testFile" + fileType;
        int count=0;
        if (methodPairSimVectors != null) {
            int size = methodPairSimVectors.size();
            for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                count++;
                stringBuilder.append(methodPairSimVector.getLeSim1());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim2());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim3());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim4());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim5());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim1());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim2());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim3());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim4());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim5());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim6());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getFunSim());
                if (count != size - 1) {
                    stringBuilder.append("\n");
                }
            }
        }
        //System.out.println(stringBuilder.toString());
        FileUtils.writeToFiles(stringBuilder.toString(),path);
    }

    public void getDataFileWithLabel(List<MethodPairSimVector> methodPairSimVectors,String fileType){
        StringBuilder stringBuilder = new StringBuilder("");
        String path;
        if (!fileType.contains(".")) {
            path = TrainDataFileStaticValue.TRAINDATAFILE + "testFile." + fileType.toLowerCase();
        }else
            path = TrainDataFileStaticValue.TRAINDATAFILE+"testFile" + fileType;
        int count=0;
        if (methodPairSimVectors != null) {
            int size = methodPairSimVectors.size();
            for (MethodPairSimVector methodPairSimVector : methodPairSimVectors) {
                count++;
                if (methodPairSimVector.getLable().equals(TrainDataFileStaticValue.CLONE)){
                    stringBuilder.append("1");
                    stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                }else if (methodPairSimVector.getLable().equals(TrainDataFileStaticValue.NONCLONE)){
                    stringBuilder.append("0");
                    stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                }
                stringBuilder.append(methodPairSimVector.getLeSim1());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim2());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim3());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim4());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getLeSim5());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim1());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim2());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim3());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim4());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim5());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getsSim6());
                stringBuilder.append(TrainDataFileStaticValue.SPILTSTR);
                stringBuilder.append(methodPairSimVector.getFunSim());
                if (count != size - 1) {
                    stringBuilder.append("\n");
                }
            }
        }
        //System.out.println(stringBuilder.toString());
        FileUtils.writeToFiles(stringBuilder.toString(),path);

    }

    /***
     * 需要手动转换TXT至CSV文件，换言之转完后，改个后缀名即可
     * @param XMLPath
     * @param txtpath
     */
    public static void getCSVFileFromXMLFile(String XMLPath,String txtpath){
        SAXReader saxReader = new SAXReader();
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            Document document = saxReader.read(new File(XMLPath));
            Element rootElement = document.getRootElement();
            //System.out.println(rootElement.getName());
            List<Element> methodPairElements = rootElement.elements(MyXMLStaticValue.METHODPAIRELEMENT);
            //System.out.println(methodPairElements.size());
            for (int i=0;i<methodPairElements.size();i++){
                Element element = methodPairElements.get(i).element(MyXMLStaticValue.SIMVECTOR);
                String text = element.getText();
                if (i!=methodPairElements.size()-1) {
                    stringBuilder.append("0");
                    stringBuilder.append(",");
                    stringBuilder.append(text);
                    stringBuilder.append("\n");
                }else {
                    stringBuilder.append("0");
                    stringBuilder.append(",");
                    stringBuilder.append(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtils.writeToFiles(stringBuilder.toString(),txtpath);


    }

    /**
     * 将XML文件转换为CSV
     * @param args
     */
    public static void main(String[] args) {
        //DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        String XMLPath = "H:\\workarea\\LSFrame\\TrainDataFiles\\NONCLONEDATA.xml";
        String path=".\\TrainDataFiles\\NonCloneTrainData.csv";
        getCSVFileFromXMLFile(XMLPath,path);
    }


}
