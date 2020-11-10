package com.xiao.poi;

import org.apache.poi.ss.usermodel.*;
import java.io.*;

/**
 * @description poi读取、写入excel文档demo演示
 * @auther: 笑笑是一个码农
 * @date: 23:43 2020/11/10
 */
public class PoiDemo {
    public static void main(String[] args) throws IOException {
        read(); // 读取测试
        write();// 写入/修改测试
    }

    /**
     * 写入(修改)excel测试方法
     * @throws IOException
     */
    private static void write() throws IOException {
        try (InputStream inp = new FileInputStream("poi-demo.xlsx")) {
            //InputStream inp = new FileInputStream("workbook.xls");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);     // 获取表格的sheet，索引从0开始
            Row row = sheet.getRow(0);          //获取当前sheet下的行, 索引从0开始
            Cell cell = row.getCell(0);         // 获取当前行下的表格，索引从0开始
            cell.setCellValue("姓名");           //设置当前表格期望写入的数据,setCellValue方法有很多重载方法，对应不同类型的数据
            // Write the output to a file
            try (OutputStream fileOut = new FileOutputStream("poi-demo.xlsx")) {
                // 这地方虽然输出与读取的文件名相同，但是不会覆盖，会修改原有的文件
                wb.write(fileOut);
                System.out.println("写入完成");
            }
        }
    }

    /**
     * 读取excel测试方法
     * @throws IOException
     */
    private static void read() throws IOException {
        try (InputStream inp = new FileInputStream("poi-demo.xlsx")) {
            //InputStream inp = new FileInputStream("workbook.xls");
            Workbook wb = WorkbookFactory.create(inp);
            int sheetSize = wb.getNumberOfSheets();  // 获取工作表数量，业务中如果使用到，要考虑使用循环读取工作表
            System.out.println("当前工作表有：" + sheetSize + "个");
            Sheet sheet = wb.getSheetAt(0);     // 获取表格的sheet，索引从0开始
            Row row = sheet.getRow(0);          //获取当前sheet下的行, 索引从0开始
            Cell cell = row.getCell(0);         // 获取当前行下的表格，索引从0开始
            int rows = sheet.getPhysicalNumberOfRows(); // 获取当前工作表中有效的数据行数
            System.out.println("当前有效行数为：" + rows + "行");
            System.out.println(cell.toString());
        }
    }
}
