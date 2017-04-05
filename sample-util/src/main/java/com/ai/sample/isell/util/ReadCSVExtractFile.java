package com.ai.sample.isell.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import com.ai.sample.common.constants.ColumnHeadingConstants;
import com.ai.sample.common.dto.AdditionalInformationDTO;
import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.TransactionalOtherDetailsDTO;
import com.ai.sample.common.exception.ISellFileException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ReadCSVExtractFile {
	
	Logger logger =  Logger.getLogger(ReadCSVExtractFile.class);
	Map<Integer, String> extractHeaderOrder = new HashMap<Integer, String>();


	public IntegrationFileFormatDTO getExtractFileTypeDetails( String integrationType) throws ISellFileException
	{
		ReadJSON readJSON = new ReadJSON();
//    	HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile("D:/Development/isell/isell/isell-util/src/main/resources/integrationexcelconfiguration.json");
		
		ClassLoader classLoader = getClass().getClassLoader();
//		File file = new File(classLoader.getResource("integrationexcelconfiguration.json").getFile());
		String path = this.getClass().getClassLoader().getResource("integrationexcelconfiguration.json").toExternalForm();
    	path = path.replace("file:/", "");
    	File file = new File(path);
		HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile(file.getAbsolutePath());
    	IntegrationFileFormatDTO integrationExcelFormatDTO = integrationHeaderDetails.get(integrationType);
    	
    	if(integrationExcelFormatDTO == null)
    	{
    		throw new ISellFileException(1, "Integration file format is not specified in Configuration file.");
    	}
    	
    	return integrationExcelFormatDTO;
	}
	
	public ArrayList<TransactionDetailsDTO> parseExtractFile(PropertyDetailsDTO propertyDetailsDto, String excelFileName,  IntegrationFileFormatDTO integrationExcelFormatDTO) throws Exception
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(excelFileName));

			Reader in = new FileReader(excelFileName);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
			CSVFormat fmt = CSVFormat.EXCEL.withDelimiter('\t');
			CSVParser parser = new CSVParser(new FileReader(excelFileName), fmt);
			long numRows = parser.getRecords().size();
			int rowCnt = 1;
			for (CSVRecord record : records ) {
				if(rowCnt == integrationExcelFormatDTO.getHeaderStartLine() )
	             {
					String values = record.toString();
			    	String[] rowTokenList = values.split(integrationExcelFormatDTO.getDelimiter());
					
	            	 readHeaderStructure(rowTokenList);
	            	 rowCnt++;
	            	 break;
	             }
			}
			
			rowCnt = 0; 
			ArrayList<TransactionDetailsDTO> transactionalDetailsDTOList = new ArrayList<TransactionDetailsDTO>();
		    for (CSVRecord record : records ) {
		    	if(integrationExcelFormatDTO.getDataStartLine() <= rowCnt)
		    	{
		    	
			    	if( rowCnt < (numRows - integrationExcelFormatDTO.getIgnoreEndLines() - integrationExcelFormatDTO.getHeaderStartLine())  )
			    	{
				    	String values = record.toString();
				    	String[] rowTokenList = values.split(integrationExcelFormatDTO.getDelimiter());
						if(rowCnt == 243)
						{
							logger.debug("Added for debugging should be removed later");
						}
						
						TransactionDetailsDTO transactionExcelData = new TransactionDetailsDTO();
						 
						 List<AdditionalInformationDTO> additionalInfoList = new ArrayList<AdditionalInformationDTO>(); 
						 if( rowCnt >=  integrationExcelFormatDTO.getDataStartLine())
			             {
							 int columnCnt = 0;
							
							 for(String rowToken : rowTokenList) 
						     {  
								 columnCnt++;
								 String columnName = extractHeaderOrder.get(columnCnt);
								 String fieldName = integrationExcelFormatDTO.getColumnHeader().get(columnName);
				                 if(fieldName == null || fieldName.length()<=0 )
				                 {
				                	 logger.trace("Column does not exists in the required list...." + columnName);
				                	 continue;
				                 }
				                 float roomRevenue = 0;
				                 switch(fieldName)
				                 {
				                 	case ColumnHeadingConstants.BOOKING_REFERENCE:
				                 		String refID = rowToken;
				                 		refID = refID.replace('[', ' ');
				                 		refID  = refID.trim();
				                 		transactionExcelData.setRefID(Long.parseLong(refID) + "");
				                 		break;
				                 	case ColumnHeadingConstants.BOOKING_NO :
				                 		transactionExcelData.setBookingRefID(rowToken);
				                 		break;
	
				                 	case ColumnHeadingConstants.CHANNEL_NAME:
				                 		transactionExcelData.setOtaName(rowToken);
				                 		break;
				                	case ColumnHeadingConstants.ARRIVAL_DATE:
	//			                 		Date arrivalDate = new Date(rowToken);
				                 		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				                         Date arrivalDate =  df.parse(rowToken);
				                 		transactionExcelData.setArrivalDate(arrivalDate);
				                 		break;
				                 	case ColumnHeadingConstants.DEPARTURE_DATE:
				                 		 DateFormat departureFormat = new SimpleDateFormat("yyyy-MM-dd");
				                         Date departureDate =  departureFormat.parse(rowToken);
				                 		transactionExcelData.setDepartureDate(departureDate);
				                 		break;
				                 	case ColumnHeadingConstants.BOOKING_DATE:
				                 		 DateFormat creationFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				                         Date creationDate =  creationFormat.parse(rowToken);
				                 		transactionExcelData.setCaptureDate(creationDate);
				                 		break;
				                 	case ColumnHeadingConstants.BOOKING_STATUS:
				                 		String bookingStatus = rowToken;
				                 		transactionExcelData.setBookingStatus(bookingStatus);
				                 		break;
				                 	case ColumnHeadingConstants.CURRENCY:
				                 		String currency = rowToken;
				                 		transactionExcelData.setTransactionCurrency(currency);
				                 		break;
				                 	case ColumnHeadingConstants.AMOUNT_PAID:
				                 		float totalAmount = Float.parseFloat(rowToken);
				                 		roomRevenue = totalAmount;
				                 		transactionExcelData.setTotalAmount(totalAmount);
				                 		break;
				                 	case ColumnHeadingConstants.NO_EXTRA_ADULT:
				                 		if(rowToken == null || rowToken.length() == 0)
				                 		{
				                 			rowToken = 0 + "";
				                 		}
				                 		int extraAdult = Integer.parseInt(rowToken);
//				                 		transactionExcelData.setTotalAmount(extraAdult);
				                 		AdditionalInformationDTO additionalInformationDTO = new AdditionalInformationDTO(ColumnHeadingConstants.NO_EXTRA_ADULT, extraAdult+"");
				                 		additionalInfoList.add(additionalInformationDTO);
				                 		break;
				                 	case ColumnHeadingConstants.ADDRESS:
				                 		String address = rowToken;
				                 		transactionExcelData.setAddress(address);
				                 		break;
				                 	case ColumnHeadingConstants.ROOMS:
				                 		String numOfRooms = rowToken;
				                 		if(rowToken == null)
				                 		{
				                 			rowToken = "0";
				                 		}
				                 		transactionExcelData.setNumOfRooms(Integer.parseInt(numOfRooms));
				                 		break;
				                 	case ColumnHeadingConstants.ROOMTYPE:
				                 		String roomType = rowToken;
				                 		
				                 		List<TransactionalOtherDetailsDTO> transactionAdditionalDetailsDTOList =  new ArrayList<TransactionalOtherDetailsDTO>();
				                 		
				                 		TransactionalOtherDetailsDTO transactionAdditionalDetailsDTO = new TransactionalOtherDetailsDTO();
				                 		transactionAdditionalDetailsDTO.setRoomType(roomType);
				                 		
				           
				                 		String roomTypeJsonString = "{\"AdditionalRevenue\": \""+ roomRevenue + "\"}";

				                 		Gson gson = new Gson();
				                 		JsonElement element = gson.fromJson(roomTypeJsonString, JsonElement.class);
				                 		JsonObject revenueByRoomType = element.getAsJsonObject();
				                 		transactionAdditionalDetailsDTO.setRevenueByRoomType(revenueByRoomType);
				                 		transactionAdditionalDetailsDTO.setRoomRevenue(roomRevenue);
				                 		transactionAdditionalDetailsDTOList.add(transactionAdditionalDetailsDTO);
				                 		transactionExcelData.setTransactionalOtherInformationDTOList(transactionAdditionalDetailsDTOList);
				                 		break;
//				                 		TODO ADD THE DATA TO FETCH ROOM TYPE INFORMATION
				                 }
						     }		 
							 

//							 TODO at present we do not get split of room revenue by
//							 by room type so this information is not yet poulated.
							 transactionExcelData.setPropertyDetailsDto(propertyDetailsDto);
							 transactionExcelData.setAdditionalInformationDTO(additionalInfoList);
							 transactionalDetailsDTOList.add(transactionExcelData);
			             }
			    	}
			    	
		    	}
		    	rowCnt++;
		    }
			
	        file.close();
	        
	        parser.close();
			return transactionalDetailsDTOList;
		}
		catch(Exception e)
		{
			logger.error("Exception is: " + e.getMessage());
			return null;
		}
	}
	
	private void readHeaderStructure(String[] headerTokenList) {
		int columnNo = 1;
		for(String headerToken : headerTokenList) 
        {   
			headerToken = headerToken.replace('[', ' ');
			headerToken = headerToken.replace(']', ' ');
			headerToken = headerToken.trim();
          	extractHeaderOrder.put(columnNo, headerToken);
          	columnNo++;
        }
	}
}
