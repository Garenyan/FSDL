package MyTools;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by garen on 2018/12/17.
 */
public class FileUtils {

    private final String writeFilePath = "";
    /**这个readFile方法不能直接用来读代码，从而去构建AST，会在读注释时发生错误！！
     * 原因见https://ask.helplib.com/others/post_13065381最后面那几行
     * 返回类型是String,不是list
     * @param filepath  代码文件路径
     * @return
     */
    public static List<String> readFile(String filepath) {
//        StringBuilder contents = new StringBuilder("");
        List<String> contents = new ArrayList<String>();
        try {
            String encoding = "UTF-8";
            File file = new File(filepath);
            //System.out.println(filepath);
            if (file.exists())
            {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String content = null;
                while((content = bufferedReader.readLine()) != null)
                {
                    contents.add(content);
                }
                inputStreamReader.close();
            }
            else
                System.out.println("文件不存在，readFile方法"+filepath);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return contents;
    }
    public static List<String> getJavaFileLists(String dirstr)
    {
        getFileList(dirstr);
        int size = filepaths.size();
        List<String> javaFilePaths = new ArrayList<String>();
        if (size !=0 )
        {
            for (int i=0;i<size;i++)
            {
                if (filepaths.get(i).endsWith("java"))
                    javaFilePaths.add(filepaths.get(i));
            }
        }
        return javaFilePaths;
    }
    private static List<String>  filepaths = new ArrayList<String>();
    private static void getFileList(String strPath) {
        File dir = new File(strPath);
        if (dir.exists()) {
            File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileList(file.getAbsolutePath()); // 获取文件绝对路径
                } else{
                    filepaths.add(file.getAbsolutePath());
                }
            }
        }
        else {
            System.out.println("文件不存在，getFileList方法");

        }


    }
    public static Boolean writeToFiles(String content,String path)
    {
        File writeFile = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
        try {
            if (!writeFile.exists()) {
                writeFile.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writeFile));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean writefiles(String content,String path){
        FileWriter fw = null;
        try {
             //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
