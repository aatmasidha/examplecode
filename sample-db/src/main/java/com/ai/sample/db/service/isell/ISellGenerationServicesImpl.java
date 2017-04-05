package com.ai.sample.db.service.isell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.ChannelManagerISellDTO;
import com.ai.sample.common.dto.OccupancyDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.common.dto.isell.ISellEventDetailsDTO;
import com.ai.sample.common.dto.ota.OtaRevenueSummaryDetailsDTO;
import com.ai.sample.common.dto.ota.OtaSoldCountByOTA;
import com.ai.sample.common.dto.pricing.RecommendedPriceByDayDTO;
import com.ai.sample.common.dto.rateshopping.ISellRateShoppingRatesByDayDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingISellDataDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.isell.ISellRateShoppingRatesByDayDao;
import com.ai.sample.db.dao.isell.OtaPerformanceSummaryDetailsByDayDao;
import com.ai.sample.db.dao.pricing.RecommendedPriceByDayDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyCompetitorMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyEventMappingDao;
import com.ai.sample.db.dao.property.transaction.ChannelManagerAvailabilityDetailsDao;
import com.ai.sample.db.dao.property.transaction.PropertyOccupancyDetailsDao;
import com.ai.sample.db.model.isell.RateShoppingRatesSummaryByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyCompetitorMapping;
import com.ai.sample.db.service.mapping.PropertyOnlineTravelAgentConnectionMappingService;
import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.util.DBConfigurationProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.ai.sample.db.service.algo.CalculateRecommendedPrice;

@Service("iSellGenerationServices")
@Transactional
public class ISellGenerationServicesImpl implements ISellGenerationServices {

	Logger logger = Logger.getLogger(ISellGenerationServicesImpl.class);

	String[] headers = new String[] { "Date", "Day", "Event", "Type",
			"Hotel Capacity", "Actual Rooms", "Virtual Rooms", "OTA Sold",
			"Pick up", "Online Revenue", "ADR", "Rate on Distribution Channel" };

	public static final int DATA_START = 2;
	public static final int HEADER_START = 1;
	public static final int MAIN_HEADER_START = 0;

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	ISellOtaPerformanceDailyDetailsServices iSellOtaPerformanceDailyDetailsServices;

	@Autowired
	ISellRateShoppoingDailyDetailsServices iSellRateShoppoingDailyDetailsServices;

	@Autowired
	PropertyOnlineTravelAgentConnectionMappingService propertyOnlineTravelAgentConnectionMappingService;

	@Autowired
	PropertyCompetitorMappingDao propertyCompetitorMappingDao;

	@Autowired
	ISellRateShoppingRatesByDayDao iSellRateShoppingRatesByDayDao;

	@Autowired
	PropertyEventMappingDao propertyEventMappingDao;

	@Autowired
	PropertyOccupancyDetailsDao propertyOccupancyDetailsDao;

	@Autowired
	OtaPerformanceSummaryDetailsByDayDao otaPerformanceSummaryDetailsByDayDao;

	@Autowired
	ChannelManagerAvailabilityDetailsDao channelManagerAvailabilityDetailsDao;

	//@Autowired
	//CalculateRecommendedPrice calculateRecommendedPrice;

	@Autowired
	RecommendedPriceByDayDao recommendedPriceByDayDao;

	private PropertyDetails propertyDetails;

	Map<Integer, PropertyCompetitorMapping> propertyCompPriorityMap;

	Map<String, Integer> propertyOtaMap = new HashMap<String, Integer>();

	Map<Date, Map<String, OtaSoldCountByOTA>> otaSoldCountByOta = new HashMap<Date, Map<String, OtaSoldCountByOTA>>();

	int olaSoldDistributionColumn = 0;

	@Override
	public void generateISellData(Date businessDate,
			PropertyDetailsDTO propertyDetailsDto, int reportNumDays)
			throws ISellProcessingException {
		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDto);
	
			if (propertyDetails == null) {
				logger.error("ISellGenerationServicesImpl::generateISellData()  The property to generate the "
						+ "extract is not configured in the database."
						+ propertyDetailsDto.toString());
				throw new ISellProcessingException(
						1,
						"ISellGenerationServicesImpl::generateISellData()  The property to generate the "
								+ "extract is not configured in the database."
								+ propertyDetailsDto.toString());
			}
	
			logger.info("ISellGenerationServicesImpl::generateISellData() calling OTA Performance ");
			iSellOtaPerformanceDailyDetailsServices
					.saveOrUpdateISellOtaPerformanceDailyDetailsData(businessDate,
							propertyDetails);
	
			List<PropertyCompetitorMapping> competitorList = propertyCompetitorMappingDao
					.findCompetitorsForProperty(propertyDetails);
	
			logger.info("ISellGenerationServicesImpl::generateISellData() calling Rate Shopping Data");
			List<RateShoppingISellDataDTO> rateshoppingdata = iSellRateShoppoingDailyDetailsServices
					.getRateShoppingDailyDetailsForProperty(businessDate,
							propertyDetails, reportNumDays);
	
			Map<PropertyDetails, List<RateShoppingISellDataDTO>> rateShoppingDetailsMap = new HashMap<PropertyDetails, List<RateShoppingISellDataDTO>>();
	
			rateShoppingDetailsMap.put(propertyDetails, rateshoppingdata);
			for (PropertyCompetitorMapping competitorDetails : competitorList) {
				PropertyDetails comPropertyDetails = competitorDetails
						.getCompetitorDetails();
				rateshoppingdata = iSellRateShoppoingDailyDetailsServices
						.getRateShoppingDailyDetailsForProperty(businessDate,
								comPropertyDetails, reportNumDays);
				rateShoppingDetailsMap.put(comPropertyDetails, rateshoppingdata);
			}
	
			Map<Date, MyJson> rateshoppingRatePerDayMap = new HashMap<Date, MyJson>();
	
			Set<PropertyDetails> propertyDetailsSet = rateShoppingDetailsMap
					.keySet();
			for (PropertyDetails ratePropertyDetails : propertyDetailsSet) {
	
				List<RateShoppingISellDataDTO> rateShoppingRatesList = rateShoppingDetailsMap
						.get(ratePropertyDetails);
				for (RateShoppingISellDataDTO rateShoppingISellDataDTO : rateShoppingRatesList) {
					MyJson myJsonObject = rateshoppingRatePerDayMap
							.get(rateShoppingISellDataDTO.getCheckinDate());
	
					ISellRateShoppingRatesByDayDTO iSellRateShoppingRatesByDayDTO = new ISellRateShoppingRatesByDayDTO(
							ratePropertyDetails.getId(),
							ratePropertyDetails.getName(),
	//						rateShoppingISellDataDTO.getNetRate(),
							rateShoppingISellDataDTO.getOnSiteRate(),
							rateShoppingISellDataDTO.isClosed());
	
					if (myJsonObject == null) {
						myJsonObject = new MyJson();
						List<ISellRateShoppingRatesByDayDTO> iSellRateShoppingRatesByDayDTOList = new ArrayList<ISellRateShoppingRatesByDayDTO>();
	
						Gson gson = new Gson();
						Type listType = new TypeToken<List<ISellRateShoppingRatesByDayDTO>>() {
						}.getType();
	
						iSellRateShoppingRatesByDayDTOList
								.add(iSellRateShoppingRatesByDayDTO);
						String json = gson.toJson(
								iSellRateShoppingRatesByDayDTOList, listType);
						myJsonObject.setStringProp(json);
	
					} else {
						Type listType = new TypeToken<List<ISellRateShoppingRatesByDayDTO>>() {
						}.getType();
						Gson gson = new Gson();
						List<ISellRateShoppingRatesByDayDTO> iSellRateShoppingRatesByDayDTOList = gson
								.fromJson(myJsonObject.getStringProp(), listType);
						iSellRateShoppingRatesByDayDTOList
								.add(iSellRateShoppingRatesByDayDTO);
						String json = gson.toJson(
								iSellRateShoppingRatesByDayDTOList, listType);
						myJsonObject.setStringProp(json);
					}
	
					rateshoppingRatePerDayMap
							.put(rateShoppingISellDataDTO.getCheckinDate(),
									myJsonObject);
				}
				logger.debug("ratePropertyDetails:  " + ratePropertyDetails);
			}
	
			logger.info(".................Creating online Travel Agent Sold Data.................");
			try {
				otaSoldCountByOta = generateOnlineTravelAgentSellData(
						propertyDetailsDto, propertyDetails, businessDate,
						reportNumDays);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			Set<Date> dailyRatesByPropertySet = rateshoppingRatePerDayMap.keySet();
			for (Date dailyRatesByProperty : dailyRatesByPropertySet) {
	
				MyJson propertyCompRateDTO = rateshoppingRatePerDayMap
						.get(dailyRatesByProperty);
	
				RateShoppingRatesSummaryByDay iSellRateShoppingRatesByDay = iSellRateShoppingRatesByDayDao
						.findRateShoppingRateForPropertyAndDay(propertyDetails,
								dailyRatesByProperty);
				if (iSellRateShoppingRatesByDay == null) {
					iSellRateShoppingRatesByDay = new RateShoppingRatesSummaryByDay();
					iSellRateShoppingRatesByDay
							.setBusinessDate(dailyRatesByProperty);
					iSellRateShoppingRatesByDay.setPropertyDetails(propertyDetails);
					iSellRateShoppingRatesByDay
							.setRateshoppingrates(propertyCompRateDTO);
				} else {
					iSellRateShoppingRatesByDay
							.setRateshoppingrates(propertyCompRateDTO);
				}
				iSellRateShoppingRatesByDayDao
						.saveOrUpdateRateShoppingRateForPropertyAndDay(iSellRateShoppingRatesByDay);
			}
		}
		catch(ISellDBException e)
		{
			logger.error("ISellGenerationServicesImpl::generateISellData");
			throw new ISellProcessingException(-1, e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private Map<Date, Map<String, OtaSoldCountByOTA>> generateOnlineTravelAgentSellData(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyDetails propertyDetails, Date businessDate,
			int reportNumDays) throws ISellProcessingException, ParseException {
		try
		{
		
			List<PropertyOtaConnectionMappingDTO> otaList = propertyOnlineTravelAgentConnectionMappingService
					.findAllOtsForProperty(propertyDetailsDto);
		
		if (otaList == null || otaList.size() <= 0) {
			logger.error("Property OTA mapping does not exist: ISellGenerationServicesImpl::generateOnlineTravelAgentSellData. ");
			throw new ISellProcessingException(
					1,
					"Property OTA mapping does not exist: ISellGenerationServicesImpl::generateOnlineTravelAgentSellData. ");
		}

		int cnt = 0;
		for (PropertyOtaConnectionMappingDTO connectionDetails : otaList) {
			String otaName = connectionDetails.getOnlineTravelAgentDetailsDto()
					.getOtaName();
			Integer cntTemp = propertyOtaMap.get(otaName);

			if (cntTemp == null) {
				propertyOtaMap.put(otaName, cnt);
				cnt++;
			}
		}
		otaSoldCountByOta = iSellOtaPerformanceDailyDetailsServices
				.findOtaSoldCountByOTA(businessDate, propertyDetails,
						reportNumDays);

		List<Date> dateList = new ArrayList<Date>(otaSoldCountByOta.keySet());
		Collections.sort(dateList);
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date date1
		 * = sdf.parse("2017-01-15");
		 */
		String otaName = otaList.get(0).getOnlineTravelAgentDetailsDto()
				.getOtaName();

		Date endDate = DateUtils.addDays(businessDate, reportNumDays);
		Date checkInDate = businessDate;

		while (endDate.after(checkInDate) || endDate.equals(checkInDate)) {
			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date
			 * date1 = sdf.parse("2017-03-07");
			 * 
			 * if(checkInDate.equals(date1)) { logger.debug("End Date is: " +
			 * endDate); }
			 */
			Map<String, OtaSoldCountByOTA> otaCountByOtaMap = otaSoldCountByOta
					.get(checkInDate);
			if (otaCountByOtaMap == null) {
				OtaSoldCountByOTA otaObj = new OtaSoldCountByOTA(checkInDate,
						otaName, 0);
				otaCountByOtaMap = new HashMap<String, OtaSoldCountByOTA>();
				otaCountByOtaMap.put(otaName, otaObj);

				otaSoldCountByOta.put(checkInDate, otaCountByOtaMap);
			}
			checkInDate = DateUtils.addDays(checkInDate, 1);

		}
		return otaSoldCountByOta;
		}
		catch(ISellDBException isellDBException)
		{
			throw new ISellProcessingException(1, isellDBException.getMessage());
		}
	}

	@Override
	public void writeISellData(Date businessDate,
			PropertyDetailsDTO propertyDetailsDto, String basePath,
			int reportNumDays) throws ISellProcessingException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		FileOutputStream fileOut = null;
		try {

			File fileFolder = new File(basePath);

			// create directories
			boolean folderCreated = fileFolder.mkdirs();
			if (!folderCreated && !fileFolder.exists()) {
				logger.error("Unable to create folder so can not create the isell output for property: "
						+ propertyDetailsDto.getName());
				throw new ISellProcessingException(1,
						"Unable to create folder so can not create the isell output for property: "
								+ propertyDetailsDto.getName());
			}

			// Workbook workbook = new XSSFWorkbook();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String reportDate = df.format(businessDate);

			fileOut = new FileOutputStream(basePath + File.separator + "isell_"
					+ propertyDetailsDto.getName() + "_" + reportDate + ".xlsx");
			Sheet sheet = workBook.createSheet();

			propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDto);

			List<PropertyCompetitorMapping> propertyCompetitorMapping = propertyCompetitorMappingDao
					.findCompetitorsForProperty(propertyDetails);
			propertyCompPriorityMap = new HashMap<Integer, PropertyCompetitorMapping>();
			for (PropertyCompetitorMapping propCompMapping : propertyCompetitorMapping) {
				propertyCompPriorityMap.put(
						propCompMapping.getCompetitorSequence(),
						propCompMapping);
			}

			createISellHeader(sheet, workBook);

			setCellRangeStyle(0, 0, 0, 3, sheet, workBook, "Property Name:"
					+ propertyDetailsDto.getName());
			setCellRangeStyle(0, 0, 5, 8, sheet, workBook, "Report Date:"
					+ reportDate);

			setCellRangeStyle(0, 0, 10, 13, sheet, workBook,
					"Last Report Date: ");

			logger.info(".................Creating Events Data.................");
			createEventData(sheet, workBook, businessDate, reportNumDays);

			logger.info(".................Creating Occupancy Data.................");
			createOccupancyData(sheet, workBook, businessDate, reportNumDays);

			logger.info(".................Creating OTA Data.................");
			createOTAData(sheet, workBook, businessDate, reportNumDays);

			logger.info(".................Creating RateShopping Data.................");
			createRateShoppingData(sheet, workBook, businessDate, reportNumDays);

			logger.info("............Print sold count by OTA..............");
			createOnlineTravelAgentSellData(sheet, workBook, businessDate,
					reportNumDays);

			

			logger.info(".................Creating ChannelManager Data.................");
			createChannelManagerAvilabilityData(sheet, workBook, businessDate,
					reportNumDays);

			logger.info(".................Executing Recommendation Service.................");

			Properties extractProperties = DBConfigurationProperties
					.getProperties();
			String pythonHome = extractProperties.getProperty("pythonhome",
					"C:\\Anaconda3");
			String commandLine = pythonHome
					+ File.separator
					+ "python "
					+ extractProperties
							.getProperty("ratescriptfile",
									"D:\\Development\\python\\isell\\15Feb2017\\callpricing.py");
			String line = commandLine;

			/*CommandLine cmdLine = CommandLine.parse(line);

			executor.setExitValue(1);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
			executor.setWatchdog(watchdog);

			System.out.println("cmdLine" + cmdLine);
			exitValue = executor.execute(cmdLine);
*/
			Process process = Runtime.getRuntime().exec(line);
			process.waitFor();
			
			logger.info("");
			createRecommendedPriceByDay(sheet, workBook, businessDate,
					reportNumDays);

			logger.info(".................Executing Completed.................");
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException in ISellGenerationServicesImpl::writeISellData  = "
					+ e.getMessage());
		} catch (IOException e) {
			logger.error("IOException in ISellGenerationServicesImpl::writeISellData = "
					+ e.getMessage());
		} catch (ISellDBException e) {
			logger.error("ISellDBException in ISellGenerationServicesImpl::writeISellData = "
					+ e.getMessage());
		} catch (Exception e) {
			logger.error("Exception in ISellGenerationServicesImpl::writeISellData = "
					+ e.getMessage());
		} finally {
			try {
				if (fileOut != null) {
					workBook.write(fileOut);
					fileOut.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createRecommendedPriceByDay(Sheet sheet,
			XSSFWorkbook workBook, Date businessDate, int reportNumDays)
			throws ISellDBException, ParseException {
		try {
			List<RecommendedPriceByDayDTO> recommendedPriceByDayList = recommendedPriceByDayDao
					.findRecommendedPriceForProperty(propertyDetails,
							businessDate, reportNumDays);

			int rowDataStart = DATA_START;
			for (int cnt = 0; cnt < recommendedPriceByDayList.size(); cnt++) {
				RecommendedPriceByDayDTO recommendedPriceByDayDTO = recommendedPriceByDayList
						.get(cnt);
				Row row = sheet.getRow(rowDataStart);
				String value = row.createCell(12).getStringCellValue();
				CellStyle cellStyleRight = createRightStyle(true, workBook);

				Cell cell = row.createCell(12);
				cell.setCellValue(recommendedPriceByDayDTO.getPrice());
				cell.setCellStyle(cellStyleRight);

				rowDataStart++;
			}
		} catch (ISellDBException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception is: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}

	private void createOnlineTravelAgentSellData(Sheet sheet,
			XSSFWorkbook workBook, Date businessDate, int reportNumDays) {

		List<Date> dateList = new ArrayList<Date>(otaSoldCountByOta.keySet());
		Collections.sort(dateList);

		int cnt = 0;

		int rowDataStart = DATA_START;
		for (Date date : dateList) {

			/*
			 * if (cnt >= 90) { break; }
			 */

			Row row = sheet.getRow(rowDataStart);
			if (row == null) {
				row = sheet.createRow(rowDataStart);
			}

			int columnNum = olaSoldDistributionColumn;
			Map<String, OtaSoldCountByOTA> otaSoldMap = otaSoldCountByOta
					.get(date);
			int cntInner = 0;

			// Set<String> otaDetailsList = otaSoldMap.keySet();
			Set<String> otaDetailsList = propertyOtaMap.keySet();

			/*
			 * for (String otaName : propertyOtaMap.keySet()) {
			 * 
			 * int col = olaSoldDistributionColumn +
			 * propertyOtaMap.get(otaName); System.out.println("ota name is 1:"
			 * + otaName + " Column Number 1: " + col); }
			 */
			for (String otaName : otaDetailsList) {

				int columnNumber = columnNum + propertyOtaMap.get(otaName);

				Cell cell = row.createCell(columnNumber);
				int otaSoldCount = 0;

				if (otaSoldMap.get(otaName) != null) {
					otaSoldCount = otaSoldMap.get(otaName).getOtaSoldCount();
				}
				cell.setCellValue(otaSoldCount);

				CellStyle cellStyleRight = createRightStyle(false, workBook);
				cell.setCellStyle(cellStyleRight);

			}
			cnt++;
			rowDataStart++;
		}

	}

	private void createChannelManagerAvilabilityData(Sheet sheet,
			XSSFWorkbook workbook, Date businessDate, int reportNumDays)
			throws ISellDBException {
		try {
			List<ChannelManagerISellDTO> channelManagerAvailabilityList = channelManagerAvailabilityDetailsDao
					.findChannelManagerAvailabilityDetailsForISell(
							propertyDetails, businessDate, reportNumDays);

			int rowDataStart = DATA_START;
			for (int cnt = 0; cnt < channelManagerAvailabilityList.size(); cnt++) {
				ChannelManagerISellDTO otaRevenueSummaryDetailsDTO = channelManagerAvailabilityList
						.get(cnt);
				Row row = sheet.getRow(rowDataStart);
				String value = row.createCell(6).getStringCellValue();
				CellStyle cellStyleRight = createRightStyle(true, workbook);
				if (value == null || value.length() <= 0) {
					Cell cell = row.createCell(6);
					cell.setCellValue(otaRevenueSummaryDetailsDTO
							.getRemainingCapacity());
					cell.setCellStyle(cellStyleRight);
				}
				// Rate on CM is column number 11
				Cell cell = row.createCell(11);
				cell.setCellValue(otaRevenueSummaryDetailsDTO
						.getRatePlanValue());
				cell.setCellStyle(cellStyleRight);

				rowDataStart++;
			}
		} catch (ISellDBException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception is: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	private XSSFCellStyle createRightStyle(boolean isRight,
			XSSFWorkbook workbook) {
		XSSFCellStyle cellStyleRight = workbook.createCellStyle();

		if (isRight) {
			cellStyleRight.setBorderRight(BorderStyle.THICK);
		} else {
			cellStyleRight.setBorderRight(BorderStyle.THIN);
		}
		cellStyleRight.setBorderBottom(BorderStyle.THIN);
		cellStyleRight.setBorderLeft(BorderStyle.THIN);
		cellStyleRight.setBorderTop(BorderStyle.THIN);

		return cellStyleRight;
	}

	private void createRateShoppingData(Sheet sheet, XSSFWorkbook workbook,
			Date businessDate, int reportNumDays) throws ISellDBException {
		try {
			Map<Integer, Integer> propertyCompPriorityMapByPropertyID = new HashMap<Integer, Integer>();
			List<Integer> compIDList = new ArrayList<Integer>(
					propertyCompPriorityMap.keySet());
			propertyCompPriorityMapByPropertyID.put(propertyDetails.getId(), 0);

			for (int compID : compIDList) {
				propertyCompPriorityMapByPropertyID.put(propertyCompPriorityMap
						.get(compID).getCompetitorDetails().getId(), compID);
			}

			Map<Date, ArrayList<ISellRateShoppingRatesByDayDTO>> iSellRateShoppingRatesByDayDTOmap = iSellRateShoppingRatesByDayDao
					.findRateShoppingRateForPropertyForConfigurableDays(
							propertyDetails, businessDate, reportNumDays);

			List<Date> dateList = new ArrayList<Date>(
					iSellRateShoppingRatesByDayDTOmap.keySet());
			int cnt = 0;
			int rowDataStart = DATA_START;

			Collections.sort(dateList);

			for (Date date : dateList) {

				if (cnt > 90) {
					break;
				}

				Row row = sheet.getRow(rowDataStart);
				if (row == null) {
					row = sheet.createRow(rowDataStart);
				}
				int columnNum = 13;
				ArrayList<ISellRateShoppingRatesByDayDTO> iSellRateShoppingRatesListPerDay = iSellRateShoppingRatesByDayDTOmap
						.get(date);
				int cntInner = 0;
				for (ISellRateShoppingRatesByDayDTO iSellRateShoppingRatesByDayDTO : iSellRateShoppingRatesListPerDay) {
					cntInner++;
					Integer colInd = propertyCompPriorityMapByPropertyID
							.get(iSellRateShoppingRatesByDayDTO
									.getPropertyDetailsID());
					if (colInd == null) {
						continue;
					}
					int columnNumber = columnNum + colInd;

					Cell cell = row.createCell(columnNumber);
					cell.setCellValue(iSellRateShoppingRatesByDayDTO.getRate());

					// if (cntInner == iSellRateShoppingRatesListPerDay.size())
					// {
					if (columnNumber == propertyCompPriorityMap.size()
							+ columnNum) {
						CellStyle cellStyleRight = createRightStyle(true,
								workbook);
						cellStyleRight.setDataFormat(workbook
								.createDataFormat().getFormat("0.00"));
						cell.setCellStyle(cellStyleRight);
					} else {
						CellStyle cellStyleRight = createRightStyle(false,
								workbook);
						cellStyleRight.setDataFormat(workbook
								.createDataFormat().getFormat("0.00"));
						cell.setCellStyle(cellStyleRight);
					}
				}
				cnt++;
				rowDataStart++;
			}
		} catch (Exception e) {
			logger.error("Exception in ISellGenerationServicesImpl::createRateShoppingData : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	private void createOTAData(Sheet sheet, XSSFWorkbook workbook,
			Date businessDate, int reportNumDays) throws ISellDBException,
			ParseException {
		try {
			List<OtaRevenueSummaryDetailsDTO> otaRevenueSummaryDetailsDTOList = otaPerformanceSummaryDetailsByDayDao
					.findOtaPerformanceForISell(propertyDetails, businessDate,
							reportNumDays);
			int rowDataStart = DATA_START;
			for (int cnt = 0; cnt < otaRevenueSummaryDetailsDTOList.size()
					&& cnt <= reportNumDays; cnt++) {
				// int columnNum = 9;

				OtaRevenueSummaryDetailsDTO otaRevenueSummaryDetailsDTO = otaRevenueSummaryDetailsDTOList
						.get(cnt);

				logger.debug("otaRevenueSummaryDetailsDTO: " + (cnt)
						+ " otaName: "
						+ otaRevenueSummaryDetailsDTO.getBusinessDate());
				Row row = sheet.getRow(rowDataStart);
				// OTA Sold column is 7

				XSSFCellStyle cellStylePink = createRightStyle(false, workbook);

				cellStylePink
						.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
				cellStylePink.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStylePink.setFillForegroundColor(new XSSFColor(
						new java.awt.Color(255, 153, 204)));
				Cell cell = row.createCell(7);

				cell.setCellValue(otaRevenueSummaryDetailsDTO.getOtaSoldCount());
				cell.setCellStyle(cellStylePink);
				// Value for last pickup is in Column 8
				CellStyle cellStyle = createRightStyle(false, workbook);

				cell = row.createCell(8);
				cell.setCellValue(otaRevenueSummaryDetailsDTO.getOtaSoldCount());
				cell.setCellStyle(cellStylePink);

				XSSFCellStyle cellStylePickup = createRightStyle(false,
						workbook);

				cellStylePickup
						.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
				cellStylePickup.setFillPattern(CellStyle.SOLID_FOREGROUND);
				int pickupValue = otaRevenueSummaryDetailsDTO
						.getLastPickupValue();
				if (pickupValue > 0) {
					cellStylePickup.setFillForegroundColor(new XSSFColor(
							new java.awt.Color(0, 112, 192)));
				} else if (pickupValue < 0) {
					cellStylePickup.setFillForegroundColor(new XSSFColor(
							new java.awt.Color(255, 0, 0)));
				} else {
					cellStylePickup.setFillForegroundColor(IndexedColors.WHITE
							.getIndex());
				}

				cell.setCellValue(pickupValue);
				cell.setCellStyle(cellStylePickup);
				// OTA Revenue Column is 9
				cell = row.createCell(9);
				cell.setCellStyle(cellStyle);

				cell.setCellValue(otaRevenueSummaryDetailsDTO
						.getOtaTotalRevenue());
				// OTA ADR column is 10
				CellStyle cellStyleRight = createRightStyle(true, workbook);
				Cell cellRight = row.createCell(10);
				cellRight.setCellStyle(cellStyleRight);
				cellRight.setCellValue(otaRevenueSummaryDetailsDTO.getOtaADR());

				rowDataStart++;
			}
		} catch (ISellDBException e) {
			logger.error("Error while generating ISell for OTAs is: "
					+ e.getMessage());
			throw e;
		}
	}

	private void createOccupancyData(Sheet sheet, XSSFWorkbook workbook,
			Date businessDate, int reportNumDays) throws ISellDBException {
		List<OccupancyDetailsDTO> occupancyDetailsDTOList = propertyOccupancyDetailsDao
				.findOccupancyDetailsForISell(propertyDetails, businessDate,
						reportNumDays);

		int rowDataStart = DATA_START;
		for (int cnt = 0; cnt < occupancyDetailsDTOList.size()
				&& cnt <= reportNumDays; cnt++) {
			// int columnNum = 4;
			OccupancyDetailsDTO occupancyDetailsDTO = occupancyDetailsDTOList
					.get(cnt);
			Row row = sheet.getRow(rowDataStart);
			CellStyle cellStyle = createRightStyle(false, workbook);

			// Capacity Column is 4
			Cell cell = row.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(occupancyDetailsDTO.getCapacity());

			// Room Available to sell Column is 5
			cell = row.createCell(5);
			cell.setCellValue(occupancyDetailsDTO.getVacantCount());
			cell.setCellStyle(cellStyle);

			rowDataStart++;
		}
	}

	private void createEventData(Sheet sheet, XSSFWorkbook workbook,
			Date businessDate, int numReportDays) throws ISellDBException {

		List<ISellEventDetailsDTO> propertyEventMappingList = propertyEventMappingDao
				.findEventsFromToDate(propertyDetails, businessDate,
						numReportDays);

		int rowDataStart = DATA_START;
		for (int cnt = 0; cnt < propertyEventMappingList.size()
				&& cnt <= numReportDays; cnt++) {
			// int columnNum = 0;
			Row row = sheet.createRow(rowDataStart);
			ISellEventDetailsDTO eventDetailsDto = propertyEventMappingList
					.get(cnt);
			CreationHelper createHelper = workbook.getCreationHelper();

			CellStyle cellStyle = createRightStyle(false, workbook);

			Cell cell = row.createCell(0);
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
					"yyyy-MM-dd"));

			sheet.autoSizeColumn(0);
			cell.setCellValue(eventDetailsDto.getOccupancyDate());
			cell.setCellStyle(cellStyle);

			// Column position for DOW is one
			cell = row.createCell(1);
			cell.setCellValue(eventDetailsDto.getDayOfWeek());
			cell.setCellStyle(cellStyle);

			// Column position for Event Name is two
			cell = row.createCell(2);
			cell.setCellValue(eventDetailsDto.getEventName());
			cell.setCellStyle(cellStyle);

			// Column position for Event Type is three
			Cell cell_right = row.createCell(3);
			cell_right.setCellValue(eventDetailsDto.getEventType());
			CellStyle cellStyleRight = createRightStyle(true, workbook);
			cell_right.setCellStyle(cellStyleRight);

			rowDataStart++;
		}
	}

	private int createISellHeader(Sheet sheet, XSSFWorkbook workbook) {

		Row row = sheet.createRow(HEADER_START);
		for (int rn = 0; rn < headers.length; rn++) {
			XSSFCellStyle style = createRightStyle(false, workbook);
			style.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setWrapText(true);
			Font font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.BLACK.getIndex());
			style.setFont(font);

			if (rn < 4) {
				style.setFillForegroundColor(new XSSFColor(new java.awt.Color(
						214, 224, 41)));
			} else if (rn >= 4 && rn < 7) {
				style.setFillForegroundColor(new XSSFColor(new java.awt.Color(
						140, 198, 63)));
			} else if (rn >= 7 && rn < 11) {
				style.setFillForegroundColor(new XSSFColor(new java.awt.Color(
						5, 135, 67)));
			} else {
				style.setFillForegroundColor(new XSSFColor(new java.awt.Color(
						0, 74, 124)));
			}
			Cell cellHeader = row.createCell(rn);
			cellHeader.setCellValue(headers[rn]);
			cellHeader.setCellStyle(style);
		}
		int startCompHeader = headers.length;

		XSSFCellStyle stylePrice = createRightStyle(false, workbook);
		stylePrice.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
		stylePrice.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stylePrice.setWrapText(true);
		Font fontPrice = workbook.createFont();
		fontPrice.setBold(true);
		fontPrice.setColor(IndexedColors.BLACK.getIndex());
		stylePrice.setFont(fontPrice);
		stylePrice.setFillForegroundColor(new XSSFColor(new java.awt.Color(150,
				198, 150)));

		Cell cellRecommendedPrice = row.createCell(startCompHeader);
		cellRecommendedPrice.setCellValue("Recommended Price");
		cellRecommendedPrice.setCellStyle(stylePrice);

		startCompHeader = startCompHeader + 1;
		// TODO check if the competitors are not mapped
		if (propertyCompPriorityMap != null
				&& propertyCompPriorityMap.size() > 0) {
			XSSFCellStyle style = createRightStyle(false, workbook);
			style.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(106,
					161, 184)));
			style.setWrapText(true);
			Font font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.BLACK.getIndex());
			style.setFont(font);

			Cell cellHeader = row.createCell(startCompHeader);
			cellHeader.setCellValue(propertyCompPriorityMap.get(1)
					.getPropertyDetails().getName());
			cellHeader.setCellStyle(style);
			startCompHeader++;
			for (Integer key : propertyCompPriorityMap.keySet()) {

				cellHeader = row.createCell(startCompHeader);
				cellHeader.setCellValue(propertyCompPriorityMap.get(key)
						.getCompetitorDetails().getName());
				startCompHeader++;
				cellHeader.setCellStyle(style);
			}
		}
		olaSoldDistributionColumn = startCompHeader;
		if (propertyOtaMap != null && propertyOtaMap.size() > 0) {
			XSSFCellStyle style = createRightStyle(false, workbook);
			style.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(new XSSFColor(new java.awt.Color(125,
					0, 184)));
			style.setWrapText(true);
			Font font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.BLACK.getIndex());
			style.setFont(font);

			for (String otaName : propertyOtaMap.keySet()) {

				int col = startCompHeader + propertyOtaMap.get(otaName);
				logger.debug("ota name is:" + otaName
						+ " Column Number: " + col);
			}

			for (String key : propertyOtaMap.keySet()) {

				int col = startCompHeader + propertyOtaMap.get(key);
				logger.debug("ota name is 2:" + key + " Column Number: "
						+ col);
				Cell cellHeader = row.createCell(col);
				cellHeader.setCellValue(key);

				cellHeader.setCellStyle(style);
			}
		}
		return startCompHeader - 1;
	}

	private void setCellRangeStyle(int row, int row1, int column, int column1,
			Sheet sheet, XSSFWorkbook workbook, String value) {
		CellRangeAddress cellRangeAddress = new CellRangeAddress(row, row,
				column, column1);

		Row rowLine = sheet.getRow((short) MAIN_HEADER_START);
		if (rowLine == null) {
			rowLine = sheet.createRow((short) MAIN_HEADER_START);
		}

		Cell cell1 = rowLine.createCell((short) column);
		cell1.setCellValue(value);

		sheet.addMergedRegion(cellRangeAddress);

		XSSFCellStyle style = createRightStyle(false, workbook);

		style.setFillBackgroundColor(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(new java.awt.Color(16, 55,
				89)));

		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);

		RegionUtil.setBorderBottom(CellStyle.THICK_HORZ_BANDS,
				cellRangeAddress, sheet);
		RegionUtil.setBorderLeft(CellStyle.THICK_HORZ_BANDS, cellRangeAddress,
				sheet);
		RegionUtil.setBorderRight(CellStyle.THICK_HORZ_BANDS, cellRangeAddress,
				sheet);
		RegionUtil.setBorderTop(CellStyle.THICK_HORZ_BANDS, cellRangeAddress,
				sheet);

		Font font = workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(font);
		cell1.setCellStyle(style);
	}

}
