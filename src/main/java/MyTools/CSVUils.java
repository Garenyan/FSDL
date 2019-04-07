package MyTools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/4/1.
 */
public class CSVUils {
    public static List<String[]> readCSVFileToStringArrayList(String path) throws IOException {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        // 设定UTF-8字符集，使用带缓冲区的字符输入流BufferedReader读取文件内容
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        // file.readLine(); //跳过表头所在的行

        // 遍历数据行并存储在名为records的ArrayList中，每一行records中存储的对象为一个String数组
        while ((record = file.readLine()) != null) {
            String fields[] = record.split(",");
            records.add(fields);
        }
        // 关闭文件
        file.close();
        return records;
    }

    public static Object[][] readCSVFileToObjectArrays(String path) throws IOException {
        List<String[]> records = readCSVFileToStringArrayList(path);
        // 将存储测试数据的List转换为一个Object的二维数组
        Object[][] results = new Object[records.size()][];
        // 设置二位数组每行的值，每行是一个Object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = (Object[]) records.get(i);
        }
        return results;
    }

}
