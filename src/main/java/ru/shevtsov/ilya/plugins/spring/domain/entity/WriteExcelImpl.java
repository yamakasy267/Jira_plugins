package ru.shevtsov.ilya.plugins.spring.domain.entity;

import ru.shevtsov.ilya.plugins.spring.domain.IWriteExcel;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.Date;

import javax.inject.Named;

@Named
public class WriteExcelImpl implements IWriteExcel {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Лист 1");
    final int totalColNum = 6;

    public void WriteExcel(Map<Integer, Object[]> issueMap, String epic) {
        Set<Integer> keyset = issueMap.keySet();
        int rowNum = 0;
        sheet.setRowSumsBelow(false);

        Object[] headerObj = new Object[] {"Код", "Тип задачи", "Тема", "Срок исполнения", "Исполнитель", "Статус"};
        Object[] titleObj = new Object[] {"", "", epic, "", "", ""};

        CellStyle headerStyle = headerStyleCreation();
        CellStyle titleStyle = titleStyleCreation();
        CellStyle defaultStyle = defaultStyleCreation();

        cellPrinting(headerObj, rowNum++, headerStyle);
        cellPrinting(titleObj, rowNum++, titleStyle);

        int elemNumInGroup = -1;

        for (Integer key : keyset) {
            Object[] objArr = issueMap.get(key);
            cellPrinting(objArr, rowNum++, defaultStyle);
            elemNumInGroup++;

            if (elemNumInGroup == (int)objArr[totalColNum]) {
                sheet.groupRow(rowNum - elemNumInGroup, rowNum - 1);
                elemNumInGroup = -1;
            }
        }

        for (int i = 0; i < totalColNum; i++) {
            sheet.autoSizeColumn(i);
        }

        try {
            String home = System.getProperty("user.home");
            String currDateTime = new SimpleDateFormat("_yyMMdd_HHmm").format(new Date());
            FileOutputStream out = new FileOutputStream(home + "/Downloads/" + epic + currDateTime + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println(epic + ".xlsx written successfully in " + home + "\\Downloads");
        }
        catch (Exception error) {
            System.out.println("\n\n" + error + "\n\n");
        }
    }

    private void cellPrinting(Object[] objArr, int rowNum, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        int cellNum = 0;
        for (int i = 0; i < totalColNum; i++)
        {
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue((String)objArr[i]);
            cell.setCellStyle(style);
        }
    }

    private CellStyle headerStyleCreation() {
        XSSFFont font = fontCreation();
        font.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        headerStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private CellStyle titleStyleCreation() {
        XSSFFont font = fontCreation();
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        titleStyle.setVerticalAlignment(VerticalAlignment.TOP);
        titleStyle.setWrapText(true);
        titleStyle.setFont(font);
        return titleStyle;
    }

    private CellStyle defaultStyleCreation() {
        XSSFFont font = fontCreation();
        CellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setAlignment(HorizontalAlignment.LEFT);
        defaultStyle.setVerticalAlignment(VerticalAlignment.TOP);
        defaultStyle.setWrapText(true);
        defaultStyle.setBorderBottom(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        defaultStyle.setBorderRight(BorderStyle.THIN);
        defaultStyle.setBorderTop(BorderStyle.THIN);
        defaultStyle.setFont(font);
        return defaultStyle;
    }

    private XSSFFont fontCreation() {
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        return font;
    }
}
