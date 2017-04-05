/**
 * 
 */
package com.ai.sample.transdata;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author aparna
 *
 */
public class ReadExcel {
	public static void main(String[] args) 
    {
        try
        {
            //FileInputStream file = new FileInputStream(new File("D:/Documents/isell/requirementdocuments/samples/Maximojo_Data.xlsx"));
        	FileInputStream file = new FileInputStream(new File("D:/Documents/isell/requirementdocuments/samples/Maximojo_Data.xlsx"));
        	
            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//        	 org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
            
            
            Workbook wb = WorkbookFactory.create(file);
            Sheet sheet =wb.getSheetAt(0); 
            //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
//        	 org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                 
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                        	if(DateUtil.isCellDateFormatted(cell))
                        	{
                        		 Date dateCellValue = cell.getDateCellValue();
                        		 if (dateCellValue != null) {
                        			 System.out.print(new SimpleDateFormat().format(dateCellValue));
                                 }
                        	}
                        	else
                        	{
                        		System.out.print(cell.getNumericCellValue() + "t");
                        	}
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
