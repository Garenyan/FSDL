package Features;

import Bean.Method;
import Bean.MethodPairSimVector;
import MyTools.TimeUtils;
import MyTools.XMLUtils;
import StaticValue.MyXMLStaticValue;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by garen on 2019/3/19.
 */
public class MethodPairSimVectorHelper {
    public static MethodPairSimVector createMethodPairSimVectorObject(Method s,Method t,Double leSim1,Double leSim2,Double leSim3,Double leSim4,Double leSim5,Double sSim1,Double sSim2,Double sSim3,Double sSim4,Double sSim5,
    Double sSim6,Double fSim)
    {
        MethodPairSimVector methodPairSimVector = new MethodPairSimVector();
        methodPairSimVector.setSourceMethod(s);
        methodPairSimVector.setTargetMethod(t);
        methodPairSimVector.setLeSim1(leSim1);
        methodPairSimVector.setLeSim2(leSim2);
        methodPairSimVector.setLeSim3(leSim3);
        methodPairSimVector.setLeSim4(leSim4);
        methodPairSimVector.setLeSim5(leSim5);
        methodPairSimVector.setsSim1(sSim1);
        methodPairSimVector.setsSim2(sSim2);
        methodPairSimVector.setsSim3(sSim3);
        methodPairSimVector.setsSim4(sSim4);
        methodPairSimVector.setsSim5(sSim5);
        methodPairSimVector.setsSim6(sSim6);
        methodPairSimVector.setFunSim(fSim);
        //methodPairSimVector.setLable(lable);
        return methodPairSimVector;
    }

    /**
     * 产生方法对XML文件
     * @param methodPairSimVectors
     */
    public static void convertToXMLFile(List<MethodPairSimVector> methodPairSimVectors){
        String XMLPath = MyXMLStaticValue.XMLPATH + TimeUtils.getTimeAndDateStringFormat(new Date(),"yyy-MM-dd HH-mm-ss")+".xml";
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(MyXMLStaticValue.ROOTELEMENTNAME);
        //document.addComment("这里是注释");//添加XML文件注释
        for (MethodPairSimVector methodPairSimVector:methodPairSimVectors) {
            Element methodPairElement = root.addElement(MyXMLStaticValue.METHODPAIRELEMENT);
            methodPairElement.addAttribute(MyXMLStaticValue.ATTRIBUTE_SOURCENAME,methodPairSimVector.getSourceMethod().getMethodName().toString());
            methodPairElement.addAttribute(MyXMLStaticValue.ATTRIBUTE_SOURCEPATH,methodPairSimVector.getSourceMethod().getFilepath());
            methodPairElement.addAttribute(MyXMLStaticValue.ATTRIBUTE_TARGETNAME,methodPairSimVector.getTargetMethod().getMethodName().toString());
            methodPairElement.addAttribute(MyXMLStaticValue.ATTRIBUTE_TARGETPATH,methodPairSimVector.getTargetMethod().getFilepath());
            Element simVectorElement = methodPairElement.addElement(MyXMLStaticValue.SIMVECTOR);
            simVectorElement.setText(methodPairSimVector.getLeSim1()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getLeSim2()+MyXMLStaticValue.XMLSPILT
                    +methodPairSimVector.getLeSim3()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getLeSim4()
            +MyXMLStaticValue.XMLSPILT+methodPairSimVector.getLeSim5()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim1()
            +MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim2()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim3()
            +MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim4()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim5()
            +MyXMLStaticValue.XMLSPILT+methodPairSimVector.getsSim6()+MyXMLStaticValue.XMLSPILT+methodPairSimVector.getFunSim());
            Element tagElement  = methodPairElement.addElement(MyXMLStaticValue.TAG);
            tagElement.setText(methodPairSimVector.getLable());

        }
        //用于格式化xml内容和设置头部标签
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter(XMLPath);
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(document);
            writer.close();
            //System.out.print("生成XML文件成功");
        } catch (IOException e) {
            //System.out.print("生成XML文件失败");
            e.printStackTrace();
        }



    }
}
