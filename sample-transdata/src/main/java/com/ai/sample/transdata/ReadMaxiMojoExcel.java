/**
 * 
 */
package com.ai.sample.transdata;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.sample.db.service.property.transaction.TransactionalDetailsService;
import com.ai.sample.transdata.utilities.Constants;
import com.ai.sample.transdata.utilities.InputExcelData;
import com.ai.sample.transdata.utilities.ReadStatus;

/**
 * @author aparna
 *
 */


public class ReadMaxiMojoExcel{
	
	@Autowired 
	TransactionalDetailsService transactionalDetailsService;
	
	Map<Integer, String> maxiMojoExcelHeaderOrder = new HashMap<Integer, String>();
	
	List<InputExcelData> maxiMojoExcelData = new ArrayList<InputExcelData>();
	
	public static void main(String[] args) 
    {
        try
        {
        	ReadMaxiMojoExcel readMaxiMojoExcel = new ReadMaxiMojoExcel();
        	readMaxiMojoExcel.readExcelFile("D:/Documents/isell/requirementdocuments/samples/Maximojo_Data.xlsx");         
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
	
	
	public ReadStatus readExcelFile(String excelFilePath) throws Exception
	{
		FileInputStream file = new FileInputStream(new File(excelFilePath));
		
		 Workbook wb = WorkbookFactory.create(file);
    
         Sheet sheet =wb.getSheetAt(0); 
         //Iterate through each rows one by one
         Iterator<Row> rowIterator = sheet.iterator();
         
         int rowCnt = 1;
         while (rowIterator.hasNext()) 
         {
             Row row = rowIterator.next();
             //For each row, iterate through all the columns
             Iterator<Cell> cellIterator = row.cellIterator();
             if(rowCnt == 1 )
             {
            	 readHeaderStructure(cellIterator);
             }
             InputExcelData maxiMojoExcelData = new InputExcelData();
             if( rowCnt >  1)
             {
	             while (cellIterator.hasNext()) 
	             {
	                 Cell cell = cellIterator.next();
	                 
	                 int columnNumber = cell.getColumnIndex(); 
	                 String columnName = maxiMojoExcelHeaderOrder.get(columnNumber);
	                 
	                 switch(columnName)
	                 {
	                 	
	                 	case Constants.MAXIMOJO_BOOKING_NO:
	                 		String bookingID = cell.getStringCellValue();
	                 		maxiMojoExcelData.setBookingID(bookingID);
	                 		break;
	                 	case Constants.MAXIMOJO_ARRIVAL_DATE:
	                 		Date arrivalDate = cell.getDateCellValue();
	                 		maxiMojoExcelData.setArrivalDate(arrivalDate);
	                 		break;
	                 	case Constants.MAXIMOJO_DEPARTURE_DATE:
	                 		Date departureDate = cell.getDateCellValue();
	                 		maxiMojoExcelData.setDepartureDate(departureDate);
	                 		break;
	                 	case Constants.MAXIMOJO_BOOKING_DATE:
	                 		Date creationDate = cell.getDateCellValue();
	                 		maxiMojoExcelData.setCreationDate(creationDate);
	                 		break;
	                 	case Constants.MAXIMOJO_CHANNEL_NAME:
	                 		String channelName = cell.getStringCellValue();
	                 		maxiMojoExcelData.setChannelName(channelName);
	                 		break;
	                 	case Constants.MAXIMOJO_BOOKING_STATUS:
	                 		String bookingStatus = cell.getStringCellValue();
	                 		maxiMojoExcelData.setBookingStatus(bookingStatus);
	                 		break;
	                 	case Constants.MAXIMOJO_CURRENCY:
	                 		String currency = cell.getStringCellValue();
	                 		maxiMojoExcelData.setCurrency(currency);
	                 		break;
	                 	case Constants.MAXIMOJO_NUMBER_OF_GUESTS:
	                 		double numGuests = cell.getNumericCellValue();
	                 		maxiMojoExcelData.setNumGuests((int) numGuests);
	                 		break;
	                 	case Constants.MAXIMOJO_AMOUNT_PAID:
	                 		float totalAmount = (float)cell.getNumericCellValue();
	                 		maxiMojoExcelData.setTotalAmount(totalAmount);
	                 }
	             }
	             
	             System.out.println(maxiMojoExcelData.toString());
	             System.out.println("");
             }
             rowCnt++;
//             TransactionDetailsDTO transaction = new TransactionDetailsDTO();
//             transactionalDetailsService.saveTransactionDetails(transaction);
         }
         file.close();
		return null;
	}

	private void readHeaderStructure(Iterator<Cell> cellIterator) {
		while (cellIterator.hasNext()) 
        {
            Cell cell = cellIterator.next();
            //Check the cell type and format accordingly
            switch (cell.getCellType()) 
            {
                case Cell.CELL_TYPE_STRING:
                	maxiMojoExcelHeaderOrder.put(cell.getColumnIndex(), cell.getStringCellValue());
            }
		
        }
	}
}
