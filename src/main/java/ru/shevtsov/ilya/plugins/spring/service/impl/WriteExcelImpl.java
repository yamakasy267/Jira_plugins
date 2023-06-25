//package ru.shevtsov.ilya.plugins.spring.service.impl;
//
//import ru.shevtsov.ilya.plugins.spring.service.IWriteExcel;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.Set;
//import java.util.Map;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//
//public class WriteExcelImpl implements IWriteExcel {
//
//    public void WriteExcel(Map<Integer, Object[]> issueMap, String epic) {
//        //Blank workbook
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        //Create a blank sheet
//        XSSFSheet sheet = workbook.createSheet("Лист 1");
//        //Iterate over data and write to sheet
//        Set<Integer> keyset = issueMap.keySet();
//        Row row = sheet.createRow(0);
//        int cellNum = 0;
//
//        Object [] objArr = new Object[] {"Код", "Тип задачи", "Тема", "Срок исполнения", "Исполнитель", "Статус"};
//        for (Object obj : objArr)
//        {
//            Cell cell = row.createCell(cellNum++);
//            if(obj instanceof String)
//                cell.setCellValue((String)obj);
//            else if(obj instanceof Integer)
//                cell.setCellValue((Integer)obj);
//
//            XSSFFont font = workbook.createFont();
//            font.setFontHeightInPoints((short) 10);
//            font.setFontName("Arial");
//            font.setColor(IndexedColors.BLACK.getIndex());
//            font.setBold(true);
//            CellStyle style = workbook.createCellStyle();
//            style.setFont(font);
//            cell.setCellStyle(style);
//        }
//
//        objArr = new Object[] {"", "", epic, "", "", ""};
//        row = sheet.createRow(1);
//        cellNum = 0;
//        for (Object obj : objArr)
//        {
//            Cell cell = row.createCell(cellNum++);
//            if(obj instanceof String)
//                cell.setCellValue((String)obj);
//            else if(obj instanceof Integer)
//                cell.setCellValue((Integer)obj);
//
//            XSSFFont font = workbook.createFont();
//            font.setFontHeightInPoints((short) 10);
//            font.setFontName("Arial");
//            CellStyle style = workbook.createCellStyle();
//            style.setFont(font);
//            cell.setCellStyle(style);
//        }
//
//        int rowNum = 2;
//        for (Integer key : keyset)
//        {
//            row = sheet.createRow(rowNum);
//            objArr = issueMap.get(key);
//            cellNum = 0;
//            for (Object obj : objArr)
//            {
//                Cell cell = row.createCell(cellNum++);
//                if(obj instanceof String)
//                    cell.setCellValue((String)obj);
//                else if(obj instanceof Integer)
//                    cell.setCellValue((Integer)obj);
//
//                XSSFFont font = workbook.createFont();
//                font.setFontHeightInPoints((short) 10);
//                font.setFontName("Arial");
//                CellStyle style = workbook.createCellStyle();
//                style.setFont(font);
//                cell.setCellStyle(style);
//            }
//            rowNum++;
//        }
//
//        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
//        for (int i = 0; i < 2; i++) {
//            patriarch.createSimpleShape(new XSSFClientAnchor());
//        }
//
//        int totalColNum = 6;
//        for (int i = 0; i < totalColNum; i++)
//        {
//            sheet.autoSizeColumn(i);
//        }
//
//        try
//        {
//            //Write the workbook in file system
//            FileOutputStream out = new FileOutputStream(new File(epic + ".xlsx"));
//            workbook.write(out);
//            out.close();
//            System.out.println("demo.xlsx written successfully on disk.");
//        }
//        catch (Exception e)
//        {
//            //e.printStackTrace();
//            System.out.println("Процесс не может получить доступ к файлу, так как этот файл занят другим процессом.");
//        }
//    }
//}
