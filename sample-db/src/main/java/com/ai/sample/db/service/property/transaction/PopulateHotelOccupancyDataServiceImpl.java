package com.ai.sample.db.service.property.transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.OccupancyDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.transaction.PropertyOccupancyDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.PropertyOccupancyDetails;


@Service("populateHotelOccupancyDataService")
@Transactional
public class PopulateHotelOccupancyDataServiceImpl implements
PopulateHotelOccupancyDataService {
	Logger logger =  Logger.getLogger(PopulateHotelOccupancyDataServiceImpl.class);
	
	Map<Integer, String> occupacyReportHeaderOrder = new HashMap<Integer, String>();
	@Autowired
	PropertyOccupancyDetailsDao propertyOccupancyDetailsDao;
	
	@Autowired
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;

	@Override
	public void readHistoryCSVData(String propertyName, CityDTO cityDto,
			String fileName) throws ISellProcessingException {
		try
		{
	
			FileInputStream file = new FileInputStream(new File(fileName));
			 Workbook wb = WorkbookFactory.create(file);
	         Sheet sheet =wb.getSheetAt(0); 
	         
	         int numberOfRows = sheet.getPhysicalNumberOfRows();
	         //Iterate through each rows one by one
	         Iterator<Row> rowIterator = sheet.iterator();
	         
	         
	         ArrayList<OccupancyDetailsDTO> occupancyDetailsDTOList = new ArrayList<OccupancyDetailsDTO>();
	         int rowCnt = 1;
	         while (rowIterator.hasNext() && rowCnt < (numberOfRows - 2 )) 
	         { 
	        	 Row row = rowIterator.next();
	             //For each row, iterate through all the columns
	        	 Iterator<Cell> cellIterator = row.cellIterator();
	             if(rowCnt == 3 )
	             {
	            	 readHeaderStructure(cellIterator);
	             }
	             OccupancyDetailsDTO occupancyData = new OccupancyDetailsDTO();
	             if( rowCnt >=  5)
	             {
	            	 int roomCount = 1;
	            	 int occCount = 1;
	            	 while (cellIterator.hasNext()) 
		             {
	            		 int numRooms = 0;

			             Cell cell = cellIterator.next();
			             int columnNumber = cell.getColumnIndex(); 
			             String columnName = occupacyReportHeaderOrder.get(columnNumber);
			             
			             if(columnName == null || columnName.length() <= 0)
			             {
			            	 continue;
			             }
		
		                 switch(columnName)
		                 {
		                 	case "Rooms" :
		                 		if(roomCount == 1)
		                 		{
			                 		numRooms = (int) cell.getNumericCellValue();
			                 		occupancyData.setCapacity(numRooms);
			                 		logger.debug("Capacity is: " + numRooms);
		                 		}
		                 		roomCount++;
		                 		break;
		                 	case "Occ":
		                 		if(occCount == 1)
		                 		{
			                 		int occupancy = (int) cell.getNumericCellValue();
			                 		occupancyData.setOccupancy(occupancy);
			                 		logger.info("Occ is: " + occupancy);
		                 		}
		                 		occCount++;
		                 		break;
		                 	case "Date":
		                 		String occupancyDateString = cell.getStringCellValue();
		                 		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		                 		occupancyData.setOccupancyDate(df.parse(occupancyDateString));
		                 		logger.info("Date is: " + occupancyDateString);
		                 		break;
		                 	case "OOO":
		                 		int outOfOrderCount = (int) cell.getNumericCellValue();
		                 		occupancyData.setOutOfOrderCount(outOfOrderCount);
		                 		logger.info("OOO is: " + outOfOrderCount);
		                 		break;
//		                 		TODO at present the occupancy report
//		                 		does not provide occupancy details by  roomtype but we need
//		                 		this data by ROOMTYPE. So by  default if roomtype is null we are
//		                 		planning to map it to hotel.
		                 	case "RoomType":
		                 		String roomTypeName = cell.getStringCellValue();
		                 		occupancyData.setRoomTypeName(roomTypeName);
		                 		logger.info("Room Type is: " + roomTypeName);
		                 		break;
		                 	case "Vacant":
		                 		int vacantCount = (int) cell.getNumericCellValue();
		                 		occupancyData.setVacantCount(vacantCount);
		                 		logger.info("Room Type is: " + vacantCount);
		                 		break;
		                 }
		             }
	             }
	
	             String roomTypeName = occupancyData.getRoomTypeName();
	             if(roomTypeName == null || roomTypeName.length() <= 0)
	             {
	            	 occupancyData.setRoomTypeName("Hotel");
	             }
	             if(occupancyData.getOccupancyDate() != null)
	             {
	            	 occupancyDetailsDTOList.add(occupancyData);
	             }
	             rowCnt++;
	         }
	        PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
	        propertyDetailsDto.setName(propertyName);
	        propertyDetailsDto.setCityDto(cityDto);
	        PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
	        int cnt = 0;
	        for(OccupancyDetailsDTO occupancyDetails : occupancyDetailsDTOList)
	        {
	        	cnt++;
	        	if( cnt > 10)
	        	{
	        		logger.info("Date is:" + occupancyDetails.toString());
	        	}
	        	Date occupancyDate = occupancyDetails.getOccupancyDate();
	        	String propertyRoomType = occupancyDetails.getRoomTypeName();
	        	PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(propertyRoomType, propertyDetails);
	        	
	        	PropertyOccupancyDetails propertyOccupancyDetails = propertyOccupancyDetailsDao.findPropertyOccupancyByDate(propertyDetailsDto, occupancyDate, propertyRoomType);
	        	int occupanyCount = occupancyDetails.getOccupancy();
	        	if(propertyOccupancyDetails == null)
	        	{
	        		int pickupSinceLast = occupanyCount;
		        	propertyOccupancyDetails = new PropertyOccupancyDetails(propertyDetails, 
		        			propertyRoomTypeMapping.getRoomTypeMaster(), occupancyDate, occupancyDetails.getCapacity(), 
		        			occupanyCount, pickupSinceLast,occupancyDetails.getOutOfOrderCount(), occupancyDetails.getVacantCount());
	        	}
	        	else
	        	{
	        		int pickupSinceLast = propertyOccupancyDetails.getOccupancy();
	        		propertyOccupancyDetails.setCapacity(occupancyDetails.getCapacity());
	        		propertyOccupancyDetails.setOccupancy(occupanyCount);
	        		propertyOccupancyDetails.setLastPickup(occupanyCount - pickupSinceLast);
	        		propertyOccupancyDetails.setOutOfOrderCount(occupancyDetails.getOutOfOrderCount());
	        		propertyOccupancyDetails.setVacantCount(occupancyDetails.getVacantCount());;
	        		
	        	}
		        propertyOccupancyDetailsDao.savePropertyOccupancyDetails(propertyOccupancyDetails);
	        }
		}
		catch( IOException e)
		{
			logger.error("IOException in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
		} catch (EncryptedDocumentException e) {
			logger.error("EncryptedDocumentException in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error("InvalidFormatException in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
		} catch (ISellDBException e) {
			logger.error("ISellDBException in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
		}catch(Exception e)
		{
			logger.error("Exception in PopulateHotelOccupancyDataServiceImpl::readHistoryCSVData" + e.getMessage());
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
                	occupacyReportHeaderOrder.put(cell.getColumnIndex(), cell.getStringCellValue());
            }
		
        }
	}
	
}
