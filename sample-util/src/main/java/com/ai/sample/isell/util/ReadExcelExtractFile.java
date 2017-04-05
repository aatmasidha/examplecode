/**
 * 
 */
package com.ai.sample.isell.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.codehaus.jettison.json.JSONException;

import com.ai.sample.common.constants.ColumnHeadingConstants;
import com.ai.sample.common.dto.AdditionalInformationDTO;
import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellFileException;

/**
 * @author aparna
 *
 */


public class ReadExcelExtractFile implements IExtractFile{
	Logger logger =  Logger.getLogger(ReadExcelExtractFile.class);
	
	Map<Integer, String> staahHeaderOrder = new HashMap<Integer, String>();
	
	public IntegrationFileFormatDTO readExcelFile(  String integrationType )
    {
        try
        {
        	ReadJSON readJSON = new ReadJSON();
//        	HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile("D:/Development/isell/isell/isell-util/src/main/resources/integrationexcelconfiguration.json");
        	ClassLoader classLoader = getClass().getClassLoader();
//        	File file = new File(classLoader.getResource("integrationexcelconfiguration.json").getFile());
        	String path = this.getClass().getClassLoader().getResource("integrationexcelconfiguration.json").toExternalForm();
        	path = path.replace("file:/", "");
        	File file = new File(path);
    		HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile(file.getAbsolutePath());
        	IntegrationFileFormatDTO integrationExcelFormatDTO = integrationHeaderDetails.get(integrationType);
        	
        	return integrationExcelFormatDTO;         
        } 
        catch (Exception e) 
        {
        	logger.error("Exception In readExcelFile is: "+ e.getCause());
        }
		return null;
    }
	
	
	public ArrayList<TransactionDetailsDTO> parseExcelFile(PropertyDetailsDTO propertyDetailsDto, String excelFileName,  IntegrationFileFormatDTO integrationExcelFormatDTO) throws ISellFileException
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(excelFileName));
			 Workbook wb = WorkbookFactory.create(file);
	         Sheet sheet =wb.getSheetAt(0); 
	         int numberOfRows = sheet.getPhysicalNumberOfRows();
	         //Iterate through each rows one by one
	         Iterator<Row> rowIterator = sheet.iterator();
	         ArrayList<TransactionDetailsDTO> transactionalDetailsDTOList = new ArrayList<TransactionDetailsDTO>();
	         int rowCnt = 1;
	         while (rowIterator.hasNext() && rowCnt < (numberOfRows - integrationExcelFormatDTO.getIgnoreEndLines() - integrationExcelFormatDTO.getDataStartLine() )) 
	         { 
	             Row row = rowIterator.next();
	             //For each row, iterate through all the columns
	             Iterator<Cell> cellIterator = row.cellIterator();
	             if(rowCnt == integrationExcelFormatDTO.getHeaderStartLine() )
	             {
	            	 readHeaderStructure(cellIterator);
	             }
	             TransactionDetailsDTO extractExcelData = new TransactionDetailsDTO();
	             List<AdditionalInformationDTO> additionalInfoList = new ArrayList<AdditionalInformationDTO>(); 
	             
	             if( rowCnt >=  integrationExcelFormatDTO.getDataStartLine())
	             {
		             while (cellIterator.hasNext()) 
		             {
		                 Cell cell = cellIterator.next();
		                 
		                 int columnNumber = cell.getColumnIndex(); 
		                 String columnName = staahHeaderOrder.get(columnNumber);
		                
		                  
		                 String fieldName = integrationExcelFormatDTO.getColumnHeader().get(columnName);
		                 if(fieldName == null || fieldName.length()<=0 )
		                 {
		                	 logger.trace("Column does not exists in the required list...." + columnName);
		                	 continue;
		                 }
	
		                 logger.debug("FieldName" + fieldName);
		                 switch(fieldName)
		                 {
		                 	case ColumnHeadingConstants.BOOKING_REFERENCE:
		                 		if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC )
		                 		{                			
		                 			long refID = (long) cell.getNumericCellValue();
		                 			String refString = refID + "";
		                 			extractExcelData.setRefID(  Long.parseLong(refString) + "");
		                 		}
		                 		else
		                 		{
		                 			String refID = cell.getStringCellValue();
		                 			
		                 			extractExcelData.setRefID( Long.parseLong(refID) + "");
		                 		}
		                 		break;
		                 	case ColumnHeadingConstants.BOOKING_NO :
		                 		String bookingID = "";
		                 		switch (cell.getCellType()) 
		                        {
		                            case Cell.CELL_TYPE_STRING:
		                            {
		                            	bookingID = cell.getStringCellValue();
		                            	break;
		                            }
		                            case Cell.CELL_TYPE_NUMERIC:
		                            {	
		                            	bookingID = (long)cell.getNumericCellValue() + "";
		                            	break;
		                            }
		                 		}
//		                 		staahExcelData.setBookingID(bookingID);
		                 		extractExcelData.setBookingRefID(bookingID);
		                 		break;
		                 	case ColumnHeadingConstants.ARRIVAL_DATE:
		                 		Date arrivalDate = cell.getDateCellValue();
		                 		extractExcelData.setArrivalDate(arrivalDate);
		                 		break;
		                 	case ColumnHeadingConstants.DEPARTURE_DATE:
		                 		Date departureDate = cell.getDateCellValue();
		                 		extractExcelData.setDepartureDate(departureDate);
		                 		break;
		                 	case ColumnHeadingConstants.BOOKING_DATE:
		                 		Date creationDate = cell.getDateCellValue();
		                 		//staahExcelData.setCreationDate(creationDate);
		                 		extractExcelData.setCaptureDate(creationDate);
		                 		break;
		                 	case ColumnHeadingConstants.CHANNEL_NAME:
		  
		                 		String channelMemberName = cell.getStringCellValue();
		                 		String channelName = "";
		                 		String[] channelMemberNameArray = channelMemberName.split("-");
		                 		channelName = channelMemberNameArray[0];
		                 		channelName = channelName.trim();
		                 		extractExcelData.setOtaName(channelName);
		                 		break;
		                 	case ColumnHeadingConstants.BOOKING_STATUS:
		                 		String bookingStatus = cell.getStringCellValue();
		                 		extractExcelData.setBookingStatus(bookingStatus);
		                 		break;
		                 	case ColumnHeadingConstants.CURRENCY:
		                 		String currency = cell.getStringCellValue();
		                 		extractExcelData.setTransactionCurrency(currency);
		                 		break;
	//	                 		TODO we do not get number of guests for staah
		                 	case ColumnHeadingConstants.NUMBER_OF_GUESTS:
		                 		double numGuests = cell.getNumericCellValue();
		                 		extractExcelData.setNumGuests((int) numGuests);
		                 		break;
		                 	case ColumnHeadingConstants.AMOUNT_PAID:
		                 		float totalAmount = (float)cell.getNumericCellValue();
		                 		extractExcelData.setTotalAmount(totalAmount);
		                 		break;
		                 	case ColumnHeadingConstants.NET_AMOUNT:
		                 		float netAmount = (float)cell.getNumericCellValue();
		                 		extractExcelData.setTotalAmount(netAmount);
		                 		break;
		                 	case ColumnHeadingConstants.NO_EXTRA_ADULT:
		                 		int extraAdult = (int)cell.getNumericCellValue();
//		                 		extractExcelData.setTotalAmount(extraAdult);
		                 		AdditionalInformationDTO additionalInformationDTO = new AdditionalInformationDTO(ColumnHeadingConstants.NO_EXTRA_ADULT, extraAdult+"");
		                 		additionalInfoList.add(additionalInformationDTO);
		                 		break;
		                 	case ColumnHeadingConstants.ADDRESS:
		                 		String address = cell.getStringCellValue();
		                 		extractExcelData.setAddress(address);
		                 		break;
		                 	case ColumnHeadingConstants.ROOMS:
		                 		int numOfRooms = (int)cell.getNumericCellValue();
		                 		
		                 		extractExcelData.setNumOfRooms(numOfRooms);
		                 		break;
		                 }
		             }
		             extractExcelData.setModificationDate(new Date());
		             extractExcelData.setPropertyDetailsDto(propertyDetailsDto);
		            /* extractExcelData.setPropertyName(propertyName);
		             CityDTO cityDTO = new CityDTO(city);
		             extractExcelData.setCityName(cityDTO);*/
		             extractExcelData.setAdditionalInformationDTO(additionalInfoList);
		             logger.debug("Object to database is: "+extractExcelData.toString());
		             System.out.println("");
	             
		             transactionalDetailsDTOList.add(extractExcelData);
	             }
	             rowCnt++;
	             
	         }
	         file.close();
			return transactionalDetailsDTOList;
		}
		catch( IOException | EncryptedDocumentException | InvalidFormatException | JSONException e)
		{
			logger.error("Exception in ReadExcelExtractFile::parseExcelFile: " + e.getMessage());
			throw new ISellFileException(-1, e.getMessage());
		}
		catch(  Exception e)
		{
			logger.error("Exception in ReadExcelExtractFile::parseExcelFile: " + e.getMessage());
			throw new ISellFileException(-1, e.getMessage());
		}
	}


	private void readHeaderStructure(Iterator<Cell> cellIterator) {
		while (cellIterator.hasNext()) 
        {
            Cell cell = cellIterator.next();
            //Check the cell type and format accordingly
            switch (cell.getCellType()) 
            {
                case Cell.CELL_TYPE_STRING:
                	staahHeaderOrder.put(cell.getColumnIndex(), cell.getStringCellValue());
            }
		
        }
	}
	public static void main(String args[])
	{
		ReadExcelExtractFile readExtractExcel = new ReadExcelExtractFile();
		IntegrationFileFormatDTO integrationFileFormatDTO = readExtractExcel.readExcelFile("maximojo");
		try {
				CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
				PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
				propertyDetailsDto.setCityDto(cityDto);
				propertyDetailsDto.setName("Taj");
				ArrayList<TransactionDetailsDTO> transactionDetailsDTOList = readExtractExcel.parseExcelFile(propertyDetailsDto,"D:/Documents/isell/requirementdocuments/MaxiMojo/Maximojo_DATA_Zense_02nd Sept.xlsx", integrationFileFormatDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
