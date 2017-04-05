package com.ai.sample.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateRequestDTO;
import com.ai.sample.integration.rateshopping.dataapi.test.HotelRateThreadCSV;
import com.ai.sample.integration.rateshopping.exception.RateShoppingDataException;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;

public class RateFetchCSVTest {
	static Logger logger = Logger.getLogger(RateFetchCSVTest.class);

	PropertyDetailsService propertyDetailsService = null;
	RateShoppingPropertyDetailsMappingService rateShoppingPropertyDetailsMappingService = null;

	private static final String TAB_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = {"hotelname", "hotelcode", "otacode",
			"dtcollected", "ratedate", "los", "checkindate", "checkoutdate",
			"netrate", "onsiterate", "roomtype", "ispromotional", "ratetype",
			"discount", "closed","currency" };

	public static void main(String[] args) {
		RateFetchCSVTest restFetchTest = new RateFetchCSVTest();

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"config.xml");

		restFetchTest.rateShoppingPropertyDetailsMappingService = context
				.getBean(RateShoppingPropertyDetailsMappingService.class);

		restFetchTest.propertyDetailsService = context
				.getBean(PropertyDetailsService.class);

		try {
			restFetchTest.fetchAllRates(1, context);
		} catch (RateShoppingDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((ClassPathXmlApplicationContext) context).close();
	}

	public void fetchAllRates(int reqNumDays,  ApplicationContext context) throws RateShoppingDataException {
		try {
	
			InputStream inputCsvStream = RateFetchCSVTest.class
					.getClassLoader().getResourceAsStream(
							"rateshoppinghotellist.csv");

			final Reader readerCsv = new InputStreamReader(inputCsvStream);
			Iterable<CSVRecord> hotelRecords = CSVFormat.EXCEL.parse(readerCsv);

			SimpleDateFormat checkinDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			int row = 0;
			for (CSVRecord hotelRecord : hotelRecords) {
				if (row == 0) {
//					Ignore first record as it is csv header
					row++;
					continue;
				}
				row++;
				Date date = checkinDateFormat.parse("2016-11-20");
				String checkInDateString = checkinDateFormat.format(date);
//				String checkInDateString = checkinDateFormat.format(new Date());
				String processDate = checkInDateString;
				Thread threadArray[] = new Thread[reqNumDays];
				String rateShoppingPropertyUID = hotelRecord.get(1);
				
				RateShoppingPropertyDetailsDTO  rateShoppingPropertyDetailsDTO = rateShoppingPropertyDetailsMappingService.findRateShoppingDetailsForPropertyByPropertyCode(rateShoppingPropertyUID, "2");
				if (rateShoppingPropertyDetailsDTO == null ) {
					logger.error("No Rate Shopping data defined for property: " + hotelRecord.get(0)  + "With PropertyCode:" + hotelRecord.get(1) );
					throw new RateShoppingDataException("No Rate Shopping data defined for property: " + hotelRecord.get(0)  + "With PropertyCode:" + hotelRecord.get(1));
				}

				for (int numDays = 0; numDays < reqNumDays; numDays++) {
						PropertyRateRequestDTO hotelRequestDTO = new PropertyRateRequestDTO();

						hotelRequestDTO.setHotelCode(rateShoppingPropertyUID);
						hotelRequestDTO.setOtaName(rateShoppingPropertyDetailsDTO
										.getRateShoppingOtaID());
						hotelRequestDTO.setCurrency("USD");
						hotelRequestDTO.setCheckInDate(checkInDateString);
						Date checkInDate = checkinDateFormat
								.parse(hotelRequestDTO.getCheckInDate());
						Date checkoutDate = new Date(checkInDate.getTime()
								+ (1000 * 60 * 60 * 24));
						hotelRequestDTO.setCheckOutDate(checkinDateFormat
								.format(checkoutDate));

						HotelRateThreadCSV hoteRateThread = new HotelRateThreadCSV(
								hotelRequestDTO, hotelRecord.get(0), processDate, context);
						threadArray[numDays] = hoteRateThread;
						threadArray[numDays].start();
						threadArray[numDays].join();
						checkInDateString = hotelRequestDTO.getCheckOutDate();
						Thread.sleep(500);
					}
					while (Thread.activeCount() > 1) {
						Thread.sleep(1000);
					}

				
				String RateShoppingCsvFiles =ConnectionProperties.getProperties()
						.getProperty("rateshoppingbasefolder") + File.separator
						+ rateShoppingPropertyUID + File.separator
						+ processDate ;

				String basePath = ConnectionProperties.getProperties()
						.getProperty("rateshoppingbasefolder");
				String filePath = basePath + File.separator + 
						 "ratesummaryreport" + File.separator + processDate + File.separator;
				String pathToSummaryFiles = filePath + "Summary_" + hotelRecord.get(0) +"_"+ rateShoppingPropertyUID +"_"+processDate
						+ ".csv";
				
				File folder = new File(RateShoppingCsvFiles);

//				FileFilter fileFilter = new WildcardFileFilter(
//						Arrays.asList("RateDetails_*.csv"));
//				File[] files = folder.listFiles(fileFilter);

				File[] listOfFiles = folder.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						if (name.toLowerCase().startsWith("ratedetails_")
								&& name.toLowerCase().endsWith(".csv")) {
							return true;
						} else {
							return false;
						}
					}
				});

				logger.debug("File length is: " + listOfFiles.length);

				CSVPrinter csvFilePrinter = null;
				CSVFormat csvFileFormat = CSVFormat.DEFAULT
						.withRecordSeparator(TAB_LINE_SEPARATOR);
				
				 File summaryPath = new File(filePath);
		         
		         // create directories
		         summaryPath.mkdirs();
		         
				FileWriter fileWriter = new FileWriter(pathToSummaryFiles);
				// initialize CSVPrinter object
				csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
				// Create CSV file header
				csvFilePrinter.printRecord(FILE_HEADER);

				for (File csvFile : listOfFiles) {
					InputStream in = new FileInputStream(new File(
							csvFile.getAbsolutePath()));
					final Reader reader = new InputStreamReader(in);
					Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
					int rowOutCSV = 0;
					for (CSVRecord record : records) {
						if (rowOutCSV == 0) {
							rowOutCSV++;
							continue;
						}
						rowOutCSV++;
						csvFilePrinter.printRecord(record);
					}
					reader.close();
					in.close();
				}
				csvFilePrinter.close();
				fileWriter.close();
			}
			inputCsvStream.close();
			readerCsv.close();
			// TODO check where to close the context this has to be
			// changed as child thread are executing but needs to be
			// close after all child threads are terminated
			// ((ClassPathXmlApplicationContext) context).close();
		} catch (InterruptedException e) {
			logger.error("InterruptedException in RateFetchTest: "
					+ e.getMessage());
		} catch (ISellDBException e) {

			logger.error("ISellDBException in in RateFetchCSVTest while fetching the rates for property is: "
					+ e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException in RateFetchCSVTest: "
					+ e.getMessage());
		} catch (IOException e) {
			logger.error("IOException  in RateFetchCSVTest: " + e.getMessage());
		}
	}

}
