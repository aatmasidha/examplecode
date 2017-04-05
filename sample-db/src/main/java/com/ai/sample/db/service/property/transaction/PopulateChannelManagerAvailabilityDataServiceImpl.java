package com.ai.sample.db.service.property.transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

import com.ai.sample.common.dto.ChannelManagerAvailabilityDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.transaction.ChannelManagerAvailabilityDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.ChannelManagerAvailabilityDetails;

@Service("populateChannelManagerAvailabilityDataService")
@Transactional
public class PopulateChannelManagerAvailabilityDataServiceImpl implements
		PopulateChannelManagerAvailabilityDataService {
	Logger logger = Logger
			.getLogger(PopulateChannelManagerAvailabilityDataServiceImpl.class);

	Map<Integer, String> occupacyReportHeaderOrder = new HashMap<Integer, String>();

	@Autowired
	ChannelManagerAvailabilityDetailsDao channelManagerAvailabilityDetailsDao;

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;

	
	
//	Here channel Manager data is received every day so we can to clean the data for future and retain
//	the data for past. This will help to keep values by deleting only values for future using businessDate
	@Override
	public void readChannelManagerData(String propertyName, CityDTO cityDto,
			String fileName, Date businessDate) throws ISellProcessingException {
		try {

			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
			propertyDetailsDto.setName(propertyName);
			propertyDetailsDto.setCityDto(cityDto);
			
			FileInputStream file = new FileInputStream(new File(fileName));
			Workbook wb = WorkbookFactory.create(file);
			Sheet sheet = wb.getSheetAt(0);

//			int numberOfRows = sheet.getPhysicalNumberOfRows();
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();

			Map<Date, ChannelManagerAvailabilityDTO> channelManagerAvailabilityDetailsDTOMap = new LinkedHashMap<Date, ChannelManagerAvailabilityDTO>();
			int rowCnt = 1;

			int roomTypeStartRow = 0;
			int lowestRatePlanStartline = 0;
			for (Row row : sheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.getRichStringCellValue().getString().trim()
								.equals("Availability - Room Type")) {
							roomTypeStartRow = row.getRowNum();

						} else if (cell.getRichStringCellValue().getString()
								.trim().equals("Rate - Rate Plan")) {
							lowestRatePlanStartline = row.getRowNum();
						}
					}
				}

			}

			if (lowestRatePlanStartline != 0) {
				lowestRatePlanStartline = lowestRatePlanStartline + 1;
			}
			rowIterator = sheet.iterator();

			int columnToStart = 1;
			SimpleDateFormat sm = new SimpleDateFormat("E dd MMM yy");
			while (rowIterator.hasNext()
					&& rowCnt < lowestRatePlanStartline + 2) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				if (rowCnt == lowestRatePlanStartline) {
					readHeaderStructure(cellIterator);
				}

				
				if (lowestRatePlanStartline != 0
						&& rowCnt == (lowestRatePlanStartline + 1)) {
					String ratePlan = "";
					while (cellIterator.hasNext()) {
						ChannelManagerAvailabilityDTO occupancyData = new ChannelManagerAvailabilityDTO();
						Cell cell = cellIterator.next();
						int columnNumber = cell.getColumnIndex();
						String columnName = occupacyReportHeaderOrder
								.get(columnNumber);
						float rateValue = 0.0f;
						if (columnName == null || columnName.length() <= 0) {
							continue;
						}
						switch (columnName) {
						case "Rate - Rate Plan": {
							ratePlan = cell.getStringCellValue();
							break;
						}
						default: {
							rateValue = (float) cell.getNumericCellValue();
						}
						}
						if (!columnName.equals("Rate - Rate Plan")) {
							String tempColName = columnName.replace("\n", " ");
							Date occupancyDate = sm.parse(tempColName);
							occupancyData.setOccupancyDate(occupancyDate);
							occupancyData.setRatePlan(ratePlan);
							occupancyData.setRateOnChannelManager(rateValue);
							channelManagerAvailabilityDetailsDTOMap.put(occupancyDate, occupancyData);
						}
					}
				}
				rowCnt++;
			}
			
			rowIterator = sheet.iterator();
			occupacyReportHeaderOrder.put(0, "Availability - Room Type");
//			Get the room type information.
			columnToStart = 1;
			rowCnt = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				if (roomTypeStartRow != 0
						&& rowCnt >= roomTypeStartRow ) {
					String roomType = "";
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						int columnNumber = cell.getColumnIndex();
						
						String columnName = occupacyReportHeaderOrder
								.get(columnNumber);
						int availableCount = 0;
						if (columnName == null || columnName.length() <= 0) {
							continue;
						}
						switch (columnName) {
						case "Availability - Room Type": {
							roomType = cell.getStringCellValue();
							break;
						}
						default: {
							availableCount = (int) cell.getNumericCellValue();
						}
						}
						if (!columnName.equals("Availability - Room Type")) {
							String tempColName = columnName.replace("\n", " ");
							Date occupancyDate = sm.parse(tempColName);
							ChannelManagerAvailabilityDTO occupancyData = channelManagerAvailabilityDetailsDTOMap.get(occupancyDate);
							 Map<String, Integer> roomTypeCapcityMap = occupancyData.getRoomTypeCapcityMap();
							 roomTypeCapcityMap.put(roomType, availableCount);
							occupancyData.setRoomTypeCapcityMap(roomTypeCapcityMap);
							channelManagerAvailabilityDetailsDTOMap.put(occupancyDate, occupancyData);
						}
					}
				}
				rowCnt++;
			}

			saveChannelManagerAvailabilityData(propertyDetailsDto, channelManagerAvailabilityDetailsDTOMap, businessDate);
		}

		catch (IOException e) {
			logger.error("IOException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
			throw new ISellProcessingException(-1,"IOException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
		} catch (EncryptedDocumentException e) {
			logger.error("EncryptedDocumentException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
			throw new ISellProcessingException(-1,"EncryptedDocumentException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error("InvalidFormatException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
			throw new ISellProcessingException(-1,"InvalidFormatException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
			throw new ISellProcessingException(-1,"ParseException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"
					+ e.getMessage());
		}  catch (ISellDBException e) { 
			logger.error("ISellDBException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"+ e.getMessage());
			throw new ISellProcessingException(-1, "ISellDBException in PopulateChannelManagerAvailabilityDataServiceImpl::readHistoryCSVData"+ e.getMessage());
		}
		 
	}

	private void saveChannelManagerAvailabilityData(PropertyDetailsDTO propertyDetailsDto,Map<Date, ChannelManagerAvailabilityDTO> channelManagerAvailabilityDetailsDTOMap, Date occupancyDate) throws ISellDBException {
		
		
		Set<Date> headerKeySet = channelManagerAvailabilityDetailsDTOMap.keySet();
		Iterator<Date> iter = headerKeySet.iterator();
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
		channelManagerAvailabilityDetailsDao.deleteChannelManagerDataForFuture(propertyDetails, occupancyDate );
		while (iter.hasNext()) {
		    Date businessDate = iter.next();
		    ChannelManagerAvailabilityDTO channelManagerAvailabilityDto =   (ChannelManagerAvailabilityDTO)channelManagerAvailabilityDetailsDTOMap.get(businessDate);
		       
		    Map<String, Integer> channelManagerAvailabilityByRoomTypeMap = channelManagerAvailabilityDto.getRoomTypeCapcityMap();
		    
		    Set<String> roomTypeKeySet = channelManagerAvailabilityByRoomTypeMap.keySet();
			Iterator<String> iterRoomType = roomTypeKeySet.iterator();
			while (iterRoomType.hasNext()) {
			    String propertyRoomType = iterRoomType.next();
			    int remainingCapacity = channelManagerAvailabilityByRoomTypeMap.get(propertyRoomType);
			    PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(propertyRoomType, propertyDetails);
			    
			    ChannelManagerAvailabilityDetails channelManagerAvailabilityDetails = channelManagerAvailabilityDetailsDao.findChannelManagerAvailabilityByDate(propertyDetailsDto, businessDate, propertyRoomType);
	        	if(channelManagerAvailabilityDetails == null)
	        	{
	        		channelManagerAvailabilityDetails = new ChannelManagerAvailabilityDetails(propertyDetails, propertyRoomTypeMapping.getRoomTypeMaster(), 
		        			businessDate, remainingCapacity, channelManagerAvailabilityDto.getRateOnChannelManager());
	        	}
	        	else
	        	{
	        		remainingCapacity = channelManagerAvailabilityDetails.getRemainingCapacity() + remainingCapacity;
	        		channelManagerAvailabilityDetails.setRemainingCapacity(remainingCapacity);
	        		channelManagerAvailabilityDetails.setMinRatePlanAmount(channelManagerAvailabilityDto.getRateOnChannelManager());
	        	}
	        	channelManagerAvailabilityDetailsDao.saveChannelManagerAvailabilityDetails(channelManagerAvailabilityDetails);
			}
		    
		}
	}

	private void readHeaderStructure(Iterator<Cell> cellIterator) {
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			// Check the cell type and format accordingly
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				occupacyReportHeaderOrder.put(cell.getColumnIndex(),
						cell.getStringCellValue());
			}

		}
	}

}
