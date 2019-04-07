package Test;

import MyTools.ASTTool;
import MyTools.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.List;

/**
 * Created by garen on 2019/3/16.
 */
public class DataSetTest {
    public static void main(String[] args)
    {
        String dataSetPath = "H:\\PostPaperMaterial\\IJaDataset_BCEvalVersion\\bcb_reduced\\4\\selected";
        List<String> paths = FileUtils.getJavaFileLists(dataSetPath);
        System.out.println(paths.size());
//        for(String path:paths){
//            System.out.println(path);
//            CompilationUnit r = ASTTool.createAST(path);
//            System.out.println("ok!!!");
//            //System.out.println("\n");
//        }
//        System.out.println(paths.size());

    }
}
