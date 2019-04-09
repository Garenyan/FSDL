package Test;

import Bean.Helper.FeatureHelper;
import Bean.Lexical.VariableType;
import CodePreProcess.MethodExtract;
import CodePreProcess.MethodNodeVisitor;
import DataTest.ResultFactory;
import DataTrain.DataSetTagged;
import DataTrain.ParameterStaticValue;
import Features.LexicalFeature;
import MyTools.ASTTool;
import MyTools.FileUtils;
import StaticValue.ExcelStaticValue;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class FileTest {
    private static void createResultXlsx(String modelName, ParameterStaticValue parameterStaticValue) {
        File file = new File(ExcelStaticValue.RESULTEXCELPATH);
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createResultXlsx("111",new ParameterStaticValue());
    }
}
