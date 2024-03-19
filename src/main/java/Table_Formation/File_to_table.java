package Table_Formation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tech.tablesaw.api.*;
import tech.tablesaw.api.Table;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
public class File_to_table {

    public   Table file_to_table(String excelFilePath) {
        Table table = null;
        if(excelFilePath.endsWith(".csv"))
        {
            table = Table.read().csv(excelFilePath);
        }
        else {
            try {
                FileInputStream inputStream = new FileInputStream(excelFilePath);
                Workbook workbook = null;
                if (excelFilePath.endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                } else if (excelFilePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else {
                    System.out.println("Invalid file format");
                }

                // Assuming the data is in the first sheet (index 0)
                assert workbook != null;
                Sheet sheet = workbook.getSheetAt(0);
                //print the sheet with values
                table = convertSheetToTable(sheet);

//            System.out.println(table.summary()); // Print summary of the table
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  table;
    }

    public static Table convertSheetToTable(Sheet sheet) {
        Table table = Table.create(sheet.getSheetName());
        int rowindex=1;
        for(int i=1; i<sheet.getPhysicalNumberOfRows();i++)
        {
            if(sheet.getRow(i).getPhysicalNumberOfCells()==sheet.getRow(0).getLastCellNum())
            {
                rowindex=sheet.getRow(i).getRowNum();
                break;
            }
        }

        //Create empty columns in the table
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            if(sheet.getRow(rowindex).getCell(i).getCellType() == CellType.STRING) {
                table.addColumns(StringColumn.create(sheet.getRow(0).getCell(i).getStringCellValue()));
            }
            else if(sheet.getRow(rowindex).getCell(i).getCellType() == CellType.NUMERIC  ) {
                table.addColumns(DoubleColumn.create(sheet.getRow(0).getCell(i).getStringCellValue()));
            }
            else if(sheet.getRow(rowindex).getCell(i).getCellType() == CellType.BOOLEAN){
                table.addColumns(BooleanColumn.create(sheet.getRow(0).getCell(i).getStringCellValue()));
            }
            else if(sheet.getRow(rowindex).getCell(i).getCellType() == CellType.FORMULA){
                table.addColumns(StringColumn.create(sheet.getRow(0).getCell(i).getStringCellValue()));
            }
        }
        // Add data to the table
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {

                if(row.getCell(j)==null)
                {
                    table.column(j).appendCell("");
                }
                else{

                    if(row.getCell(j).getCellType() == CellType.STRING) {
                        table.column(j).appendCell(row.getCell(j).getStringCellValue());
                    }
                    else if(row.getCell(j).getCellType() == CellType.NUMERIC  ) {
                        table.column(j).appendCell(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                    else if(row.getCell(j).getCellType() == CellType.BOOLEAN){
                        table.column(j).appendCell(String.valueOf(row.getCell(j).getBooleanCellValue()));
                    }
                    else if(row.getCell(j).getCellType() == CellType.FORMULA){
                        table.column(j).appendCell(row.getCell(j).getCellFormula());
                    }
               }
            }
        }

        //converting double column to integer which are originally integer

        NumericColumn<?>[] numericColumns =  table.numericColumns().toArray(new NumericColumn[0]);
        for(NumericColumn<?> col : numericColumns)
        {
            double sum = col.sum();
            int sum_first = (int)sum;
            if(sum-sum_first==0)
            {
                IntColumn co = col.asIntColumn();
                table.replaceColumn(col.name(),co);
            }
        }

        return table;
    }
}
