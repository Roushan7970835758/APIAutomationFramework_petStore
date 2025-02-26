package api.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLUtility {
    private final String path;
    
    public XLUtility(String path) {
        this.path = path;
    }

    // Get total row count in a sheet
    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fi)) {
            Sheet sheet = workbook.getSheet(sheetName);
            return sheet.getLastRowNum();
        }
    }

    // Get total column count in a sheet
    public int getCellCount(String sheetName, int rowNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fi)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            return row.getLastCellNum();
        }
    }

    // Get cell data from specific row and column
    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fi)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        }
    }

    // Get all data from sheet for DataProvider
    public Object[][] getSheetData(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fi)) {
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();

            List<Object[]> data = new ArrayList<>();
            
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                Object[] rowData = new Object[colCount];
                
                for (int j = 0; j < colCount; j++) {
                    rowData[j] = getCellData(sheetName, i, j);
                }
                data.add(rowData);
            }
            
            return data.toArray(new Object[0][0]);
        }
    }

    // Get all data from sheet with header map (for key-value pairs)
    public List<List<String>> getSheetDataWithHeaders(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fi)) {
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum() + 1;
            int colCount = sheet.getRow(0).getLastCellNum();

            List<List<String>> data = new ArrayList<>();
            
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                List<String> rowData = new ArrayList<>();
                
                for (int j = 0; j < colCount; j++) {
                    rowData.add(getCellData(sheetName, i, j));
                }
                data.add(rowData);
            }
            
            return data;
        }
    }
}