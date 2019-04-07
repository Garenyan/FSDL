package MyTools;

import Bean.MyASTNode;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.eclipse.jdt.core.dom.ASTParser.K_COMPILATION_UNIT;

/**
 * Created by garen on 2018/12/17.
 */
public class ASTTool {
    private static Boolean isOut = false;

    public static Boolean getOut() {
        return isOut;
    }

    public static CompilationUnit createAST(String filepath)
    {

        ASTParser astParser = ASTParser.newParser(AST.JLS8);
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        astParser.setCompilerOptions(options);
        astParser.setKind(K_COMPILATION_UNIT); //AST基于源文件形式
        //String content = FileUtils.readFile(filepath);
        byte[] input = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filepath));
            input = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(input);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        astParser.setSource(new String(input).toCharArray());
        astParser.setResolveBindings(true);
        astParser.setBindingsRecovery(true);
        astParser.setStatementsRecovery(true);
        CompilationUnit results =null;
        try {
            results = (CompilationUnit) astParser.createAST(null);
        }catch (Exception e)
        {
            isOut = true;
            System.out.println(e.toString());
        }
        return results;
    }
}
