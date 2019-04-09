package MyTools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yjz on 2018/3/22.
 */
public class ExcelCommonUtil {
    /****EXCEl注意：只要你做了任何对EXCEL的修改 如果你想保存修改 必须写回去（或者称之为输出到本地）！！！ 无论这个EXCEL是否存在！！***************/
    /****Excel注意：一些excel样式设置必须是对cell进行最后操作，即new cellstyle-cellstyle.set(这一步可能有时候不需要)--cell.set(cellstyle)*****************/

    public static ArrayList<ArrayList<Object>> readExcel(String path, String sheetName) {
        File file = new File(path);
        if (file == null) {
            return null;
        }
        if (file.getName().endsWith("xlsx")) {
            //处理ecxel2007
            return readExcel2007(file, sheetName);
        } else {
            //处理ecxel2003
            return readExcel2003(file, sheetName);
        }
    }

    public static ArrayList<ArrayList<Object>> readExcel(String path) {
        File file = new File(path);
        if (file == null) {
            return null;
        }
        if (file.getName().endsWith("xlsx")) {
            //处理ecxel2007
            return readExcel2007(file);
        } else {
            //处理ecxel2003
            return readExcel2003(file);
        }
    }


    /**
     * 自动读取第一个sheet
     *
     * @param file
     * @return
     */
    public static ArrayList<ArrayList<Object>> readExcel2003(File file) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            Object value;
            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //当读取行为空时
                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                } else {
                    rowCount++;
                }
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        //当该单元格为空
                        if (j != row.getLastCellNum()) {//判断是否是该行中最后一个单元格
                            colList.add("");
                        }
                        continue;
                    }
                    int i1 = cell.getCellType();
                    if (i1 == XSSFCell.CELL_TYPE_STRING) {
                        System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();

                    } else {
                        if (cell.getCellType() == 0) {
                            Object lastValue = null;
                            //Double excelValue = cell.getNumericCellValue();
                            Long longVal = Math.round(cell.getNumericCellValue());
                            Double doubleVal = cell.getNumericCellValue();
                            if (Double.parseDouble(longVal + ".0") == doubleVal) {   //判断是否含有小数位.0,防止1变成1.0
                                lastValue = longVal;
                            } else {
                                lastValue = doubleVal;
                            }
                            value = String.valueOf(lastValue);
                        } else {
                            value = cell.toString();
                        }
                    }
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i
            /**以下代码块用来排除最后几行为空行的BUG，
             * 即在读取Excel时，当excel不是新建的excel时，是对一个已存在的excel复制--粘贴--删除产生，同时
             * 这个新产生的excel的行数比原先的少，虽然这个excel已经执行了清除内容等操作。
             * 但在excel在读取时，可能仍会按照原先excel的行数会读取，这样就会导致读取的最后几行全为空。**/
            int size = rowList.size();
            if (size!=0)
            {
                /**由于ArrayList在使用remove方法时性能较差，所以这里使用“懒惰删除”**/
                Boolean[] deletes = new Boolean[size];
                for (int i=0;i<size;i++)
                {
                    deletes[i] = false;
                }
                Boolean flag= false;
                for (int i = size-1 ;i < size;i--){
                    int count =0 ;
                    for (int j=0;j<rowList.get(i).size();j++)
                    {
                        if (rowList.get(i).get(j).equals(""))
                        {
                            count++;
                        }
                    }
                    if (count == rowList.get(i).size()) //说明这一行全为空
                    {
                        deletes[i] = true;
                        flag = true;
                    }
                    else
                    {
                        break;
                    }
                }
                if (!flag)
                {
                    return rowList;
                }
                else
                {
                    ArrayList<ArrayList<Object>> newRowList = new ArrayList<ArrayList<Object>>();
                    for (int i=0;i<size;i++)
                    {
                        if (!deletes[i])
                        {
                            newRowList.add(rowList.get(i));
                        }
                    }
                    return newRowList;
                }
            }
            else
                return rowList;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 用来读取特定sheet
     *
     * @param file
     * @param sheetName
     * @return
     */
    public static ArrayList<ArrayList<Object>> readExcel2003(File file, String sheetName) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = wb.getSheet(sheetName);
            HSSFRow row;
            HSSFCell cell;
            Object value;
            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //当读取行为空时
                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                } else {
                    rowCount++;
                }
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        //当该单元格为空
                        if (j != row.getLastCellNum()) {//判断是否是该行中最后一个单元格
                            colList.add("");
                        }
                        continue;
                    }
                    int i1 = cell.getCellType();
                    if (i1 == XSSFCell.CELL_TYPE_STRING) {
                        System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();

                    } else {
                        if (cell.getCellType() == 0) {
                            Object lastValue = null;
                            //Double excelValue = cell.getNumericCellValue();
                            Long longVal = Math.round(cell.getNumericCellValue());
                            Double doubleVal = cell.getNumericCellValue();
                            if (Double.parseDouble(longVal + ".0") == doubleVal) {   //判断是否含有小数位.0,防止1变成1.0
                                lastValue = longVal;
                            } else {
                                lastValue = doubleVal;
                            }
                            value = String.valueOf(lastValue);
                        } else {
                            value = cell.toString();
                        }
                    }
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i
            /**以下代码块用来排除最后几行为空行的BUG，
             * 即在读取Excel时，当excel不是新建的excel时，是对一个已存在的excel复制--粘贴--删除产生，同时
             * 这个新产生的excel的行数比原先的少，虽然这个excel已经执行了清除内容等操作。
             * 但在excel在读取时，可能仍会按照原先excel的行数会读取，这样就会导致读取的最后几行全为空。**/
            int size = rowList.size();
            if (size!=0)
            {
                /**由于ArrayList在使用remove方法时性能较差，所以这里使用“懒惰删除”**/
                Boolean[] deletes = new Boolean[size];
                for (int i=0;i<size;i++)
                {
                    deletes[i] = false;
                }
                Boolean flag= false;
                for (int i = size-1 ;i < size;i--){
                    int count =0 ;
                    for (int j=0;j<rowList.get(i).size();j++)
                    {
                        if (rowList.get(i).get(j).equals(""))
                        {
                            count++;
                        }
                    }
                    if (count == rowList.get(i).size()) //说明这一行全为空
                    {
                        deletes[i] = true;
                        flag = true;
                    }
                    else
                    {
                        break;
                    }
                }
                if (!flag)
                {
                    return rowList;
                }
                else
                {
                    ArrayList<ArrayList<Object>> newRowList = new ArrayList<ArrayList<Object>>();
                    for (int i=0;i<size;i++)
                    {
                        if (!deletes[i])
                        {
                            newRowList.add(rowList.get(i));
                        }
                    }
                    return newRowList;
                }
            }
            else
                return rowList;
        } catch (Exception e) {
            return null;
        }
    }


    //存储是先行后列,excel是从0开始的,也是用来读取第一个sheet
    public static ArrayList<ArrayList<Object>> readExcel2007(File file) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            int colnum = 0;//定义列数
            int rownum = 0;//定义行数
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheetAt(0);
            rownum = sheet.getPhysicalNumberOfRows();  //得到行数
            if (rownum >= 1 && sheet.getRow(0) != null)  //如果行数大于等于1行且行不为空时，得到列数
            {
                colnum = sheet.getRow(0).getPhysicalNumberOfCells();        //得到列数
            }
            XSSFRow row;
            XSSFCell cell;
            Object value = null;
            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < rownum; i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //当读取行为空时
                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                } else {
                    rowCount++;
                }
                for (int j = 0; j < colnum; j++) {
                    cell = row.getCell(j);
                    //int i1 = cell.getCellType();
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        //当该单元格为空
                        value = "";

                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        value = cell.getStringCellValue();
                    } else {
                        if (cell.getCellType() == 0) {
                            Object lastValue = null;
                            //Double excelValue = cell.getNumericCellValue();
                            Long longVal = Math.round(cell.getNumericCellValue());
                            Double doubleVal = cell.getNumericCellValue();
                            if (Double.parseDouble(longVal + ".0") == doubleVal) {   //判断是否含有小数位.0,防止1变成1.0
                                lastValue = longVal;
                            } else {
                                lastValue = doubleVal;
                            }
                            value = String.valueOf(lastValue);
                        } else {
                            value = cell.toString();
                        }
                    }
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i

            /**以下代码块用来排除最后几行为空行的BUG，
             * 即在读取Excel时，当excel不是新建的excel时，是对一个已存在的excel复制--粘贴--删除产生，同时
             * 这个新产生的excel的行数比原先的少，虽然这个excel已经执行了清除内容等操作。
             * 但在excel在读取时，可能仍会按照原先excel的行数会读取，这样就会导致读取的最后几行全为空。**/
            int size = rowList.size();
            if (size!=0)
            {
                /**由于ArrayList在使用remove方法时性能较差，所以这里使用“懒惰删除”**/
                Boolean[] deletes = new Boolean[size];
                for (int i=0;i<size;i++)
                {
                    deletes[i] = false;
                }
                Boolean flag= false;
                for (int i = size-1 ;i < size;i--){
                    int count =0 ;
                    for (int j=0;j<rowList.get(i).size();j++)
                    {
                        if (rowList.get(i).get(j).equals(""))
                        {
                            count++;
                        }
                    }
                    if (count == rowList.get(i).size()) //说明这一行全为空
                    {
                        deletes[i] = true;
                        flag = true;
                    }
                    else
                    {
                        break;
                    }
                }
                if (!flag)
                {
                    return rowList;
                }
                else
                {
                    ArrayList<ArrayList<Object>> newRowList = new ArrayList<ArrayList<Object>>();
                    for (int i=0;i<size;i++)
                    {
                        if (!deletes[i])
                        {
                            newRowList.add(rowList.get(i));
                        }
                    }
                    return newRowList;
                }
            }
            else
                return rowList;
        } catch (Exception e) {
            System.out.println("exception");
            return null;
        }
    }

    /**
     * 同来读取特定sheet
     *
     * @param file
     * @param sheetName
     * @return
     */
    public static ArrayList<ArrayList<Object>> readExcel2007(File file, String sheetName) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            int colnum = 0;//定义列数
            int rownum = 0;//定义行数
            //FileChannel fileChannel = new FileInputStream(file).getChannel();

            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheet(sheetName);
            //XSSFSheet sheet = wb.getSheetAt(0);
            rownum = sheet.getPhysicalNumberOfRows();  //得到行数
            if (rownum >= 1 && sheet.getRow(0) != null)  //如果行数大于等于1行且行不为空时，得到列数
            {
                colnum = sheet.getRow(0).getPhysicalNumberOfCells();        //得到列数
            }
            XSSFRow row;
            XSSFCell cell;
            Object value = null;
            for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < rownum; i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //当读取行为空时
                    if (i != sheet.getPhysicalNumberOfRows()) {//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                } else {
                    rowCount++;
                }
                for (int j = 0; j < colnum; j++) {
                    cell = row.getCell(j);
                    //int i1 = cell.getCellType();
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        //当该单元格为空
                        value = "";
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        value = cell.getStringCellValue();
                    } else {
                        if (cell.getCellType() == 0) {
                            Object lastValue = null;
                            //Double excelValue = cell.getNumericCellValue();
                            Long longVal = Math.round(cell.getNumericCellValue());
                            Double doubleVal = cell.getNumericCellValue();
                            if (Double.parseDouble(longVal + ".0") == doubleVal) {   //判断是否含有小数位.0,防止1变成1.0
                                lastValue = longVal;
                            } else {
                                lastValue = doubleVal;
                            }
                            value = String.valueOf(lastValue);
                        } else {
                            value = cell.toString();
                        }
                    }
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i
            /**以下代码块用来排除最后几行为空行的BUG，
             * 即在读取Excel时，当excel不是新建的excel时，是对一个已存在的excel复制--粘贴--删除产生，同时
             * 这个新产生的excel的行数比原先的少，虽然这个excel已经执行了清除内容等操作。
             * 但在excel在读取时，可能仍会按照原先excel的行数会读取，这样就会导致读取的最后几行全为空。**/
            int size = rowList.size();
            if (size!=0)
            {
                /**由于ArrayList在使用remove方法时性能较差，所以这里使用“懒惰删除”**/
                Boolean[] deletes = new Boolean[size];
                for (int i=0;i<size;i++)
                {
                    deletes[i] = false;
                }
                Boolean flag= false;
                for (int i = size-1 ;i < size;i--){
                    int count =0 ;
                    for (int j=0;j<rowList.get(i).size();j++)
                    {
                        if (rowList.get(i).get(j).equals(""))
                        {
                            count++;
                        }
                    }
                    if (count == rowList.get(i).size()) //说明这一行全为空
                    {
                        deletes[i] = true;
                        flag = true;
                    }
                    else
                    {
                        break;
                    }
                }
                if (!flag)
                {
                    return rowList;
                }
                else
                {
                    ArrayList<ArrayList<Object>> newRowList = new ArrayList<ArrayList<Object>>();
                    for (int i=0;i<size;i++)
                    {
                        if (!deletes[i])
                        {
                            newRowList.add(rowList.get(i));
                        }
                    }
                    return newRowList;
                }
            }
            else
                return rowList;
        } catch (Exception e) {
            System.out.println("exception");
            return null;
        }
    }

    /**
     * 读取某一列名下的数据
     */
    public static List<String> readDatasByColumnName(String filepath, String sheetName, String columnName) {
        ArrayList<ArrayList<Object>> datas = readExcel(filepath, sheetName);
        List<String> lists = new ArrayList<String>();
        int columnIndex = 1;
        for (int i = 0; i < datas.get(0).size(); i++) {
            if (datas.get(0).get(i).equals(columnName)) {
                columnIndex = i;
            }
        }
        for (int step = 1; step < datas.size(); step++) {
            String resultData = (String) datas.get(step).get(columnIndex);
            lists.add(resultData);

        }
        return lists;


    }

    /**
     * 写入Excel
     */
    public static void writeToExcel(Sheet sheet, int row, int col, Object value) {
        //这里必须注意：同一行的Row对象只能创建一次！！！！，所以这里必须要判断！！！
        HSSFRow rowObject = (HSSFRow) sheet.getRow(row);
        HSSFRow hssfRow = null;
        if (rowObject == null) {
            hssfRow = (HSSFRow) sheet.createRow(row);
        } else
            hssfRow = rowObject;
        HSSFCell cell = hssfRow.createCell(col);

        //value为空写入时，最好处理一下！！！
        if (value == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((String) value);
        }
    }

    /**
     * 写入Excel(含有自定义cellstyle)
     */

    public static void writeToExcel(Sheet sheet, int row, int col, Object value, CellStyle cellStyle) {
        //这里必须注意：同一行的Row对象只能创建一次！！！！，所以这里必须要判断！！！
        HSSFRow rowObject = (HSSFRow) sheet.getRow(row);
        HSSFRow hssfRow = null;
        if (rowObject == null) {
            hssfRow = (HSSFRow) sheet.createRow(row);
        } else
            hssfRow = rowObject;
        HSSFCell cell = hssfRow.createCell(col);

        //value为空写入时，最好处理一下！！！
        if (value == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    public static void createExcel(String excelPath, HSSFWorkbook workbook) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(excelPath);
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            out = null;
        }
    }


    /**
     * 写入EXCEL（数据已封装好）
     *
     * @param result 数据集
     * @param path   文件路径(无论是否存在)
     */
    public static void writeExcel(ArrayList<ArrayList<Object>> result, String path) {
        if (result == null) {
            return;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        //sheet.addMergedRegion()
        for (int i = 0; i < result.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            if (result.get(i) != null) {
                for (int j = 0; j < result.get(i).size(); j++) {
                    HSSFCell cell = row.createCell(j);
                    if (result.get(i).get(j) != null) {
                        cell.setCellValue((String) result.get(i).get(j));
                    } else
                        cell.setCellValue("");
                }
            }
        }
        createExcel(path, wb);
    }

    /**
     * 合并单元格
     *
     * @param firstRow 合并的开始行
     * @param lastRow  合并的结束行
     * @param firstCol 合并的开始列
     * @param lastCol  合并的结束列
     * @return
     * @throws Exception
     */
    public static void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) throws Exception {
        CellRangeAddress cra = null;
        if (firstRow <= lastRow && firstCol <= lastCol)
            cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(cra);
        //return cra;
    }


}
