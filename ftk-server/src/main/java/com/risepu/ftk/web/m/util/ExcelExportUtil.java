package com.risepu.ftk.web.m.util;

import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * @author ronghaohua
 */
public class ExcelExportUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    /**
     * 导出 Excel
     *
     * @param response
     * @param fileName
     * @param sheetName
     * @param tableName
     * @param data
     */
    public static void download(HttpServletResponse response, String fileName, String sheetName, String[] tableName, List<List<String>> data) {
        OutputStream ouputStream = null;
        try {
            HSSFWorkbook workbook = ExcelExportUtil.generateExcel(data, sheetName, tableName);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1") + ".xls");
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }
            }
        }
    }

    /**
     * 组装 Excel 对象
     *
     * @param datas
     * @param sheetName
     * @param tableName
     * @return
     */
    private static HSSFWorkbook generateExcel(List<List<String>> datas, String sheetName, String[] tableName) {
        HSSFWorkbook book = new HSSFWorkbook();
        try {
            HSSFSheet sheet = book.createSheet(sheetName);
            sheet.autoSizeColumn(1, true);// 自适应列宽度

            // 填充表头标题
            HSSFRow firstRow = sheet.createRow(0);// 第几行（从0开始）

            HSSFCellStyle sheetStyle = book.createCellStyle();
            sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中显示
            HSSFFont font = book.createFont();
            font.setFontHeightInPoints((short) 16);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            sheetStyle.setFont(font);
            firstRow.setRowStyle(sheetStyle);
            for (int i = 0; i < tableName.length; i++) {
                firstRow.createCell(i).setCellValue(tableName[i]);
            }
            // 填充表格内容
            if (datas != null && !datas.isEmpty()) {
                for (int i = 0; i < datas.size(); i++) {
                    HSSFRow row2 = sheet.createRow(i + 1);// index：第几行
                    List<String> data = datas.get(i);
                    for (int j = 0; j < data.size(); j++) {
                        HSSFCell cell = row2.createCell(j);// 第几列：从0开始
                        //设置单元格内容为字符串型
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(data.get(j));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return book;
    }

}
