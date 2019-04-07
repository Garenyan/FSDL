package Test;

import Bean.Helper.FeatureHelper;
import Bean.Lexical.VariableType;
import CodePreProcess.MethodExtract;
import CodePreProcess.MethodNodeVisitor;
import DataTrain.DataSetTagged;
import Features.LexicalFeature;
import MyTools.ASTTool;
import MyTools.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.IOException;
import java.util.List;


public class FileTest {
    public static void main(String args[]) throws IOException {
        String sourcePath = "H:\\workarea\\LSFrame\\TrainDataFiles\\0.3trainData.csv";
        String tarPath = "H:\\workarea\\LSFrame\\TrainDataFiles\\newTrainData.csv";
        DataSetTagged dataSetTagged = new DataSetTagged();
        dataSetTagged.deleteFeaturesTonewFile(FeatureHelper.FUNCTION,sourcePath,tarPath);
    }
}
