package com.ai.sample.db.service.extract;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellFileException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.service.mapping.PropertyOnlineTravelAgentConnectionMappingService;
import com.ai.sample.db.service.property.transaction.TransactionalDetailsService;
import com.ai.sample.isell.util.IExtractFile;
import com.ai.sample.isell.util.ReadExcelExtractFile;
import com.ai.sample.isell.util.ReadJSON;
import com.ai.sample.isell.util.ReadStaahCSVExtract;


public class PopulateDataForExtractsByProperty {

	static Logger logger = Logger.getLogger(PopulateDataForExtractsByProperty.class);

	@Autowired
	TransactionalDetailsService transactionalDeatilsService;

	@Autowired
	PropertyOnlineTravelAgentConnectionMappingService propertyOnlineTravelAgentConnectionMappingService;

	public  void populateOfflineDailyExtractData(String basePath, PropertyDetailsDTO propertyDetailsDto, Date businessDate ) throws ISellProcessingException {

		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			
			transactionalDeatilsService = context.getBean(TransactionalDetailsService.class);
			propertyOnlineTravelAgentConnectionMappingService = context.getBean(PropertyOnlineTravelAgentConnectionMappingService.class);
			
			IExtractFile readExtractCSV = null;
			ReadJSON readConfigJson = new ReadJSON();

//			ClassLoader classLoader = getClass().getClassLoader();
			/*String path = PopulateDataForExtractsByProperty.class.getClassLoader()
					.getResource("integrationexcelconfiguration.json").toExternalForm();

			System.out.println("*******Path 1" + path);
			path = path.replace("file:/", "");
			System.out.println("*******Path 2" + path);
			File file = new File(path);
			IntegrationFileFormatDTO  integrationFileFormatDTO =readConfigJson.getExtractFileTypeDetails(file.getAbsolutePath(),"staah");*/

			
			IntegrationFileFormatDTO  integrationFileFormatDTO =readConfigJson.getExtractFileTypeDetails("","staah");
			if(integrationFileFormatDTO.getFileType().equalsIgnoreCase("csv"))
			{
				readExtractCSV = new ReadStaahCSVExtract();
			}
			else if( integrationFileFormatDTO.getFileType().equalsIgnoreCase("excel") )
			{
				readExtractCSV = new ReadExcelExtractFile();
			}


			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.contains("data")) {
						return true;
					} else {
						return false;
					}
				}
			};

			File baseFilePath = new File(basePath);
			File[] files = baseFilePath.listFiles(textFilter);
			if( files != null && files.length > 0 )
			{
				Arrays.sort(files);
				if( files != null && files.length > 0 )
				{
					for (File fileObj : files) {
						ArrayList<TransactionDetailsDTO> transactionDetailsDTOList = readExtractCSV.parseExcelFile(propertyDetailsDto, 
								fileObj.getAbsolutePath(), integrationFileFormatDTO);
		
						logger.debug("*********Number of elements is: " + transactionDetailsDTOList.size());
						List<PropertyOtaConnectionMappingDTO> peopertyOtaNameList = propertyOnlineTravelAgentConnectionMappingService.findAllOtsForProperty(propertyDetailsDto);
						List<String> missingOtaMappingsList = new ArrayList<String>();
						for(TransactionDetailsDTO transactionDetailsDTO : transactionDetailsDTOList)
						{
							String otaName = transactionDetailsDTO.getOtaName();
							otaName = otaName.trim();
		
							boolean present = false;
							for(PropertyOtaConnectionMappingDTO propertyOtaConnectionMappingDTO : peopertyOtaNameList)
							{
								if(otaName.equalsIgnoreCase(propertyOtaConnectionMappingDTO.getPropertyOtaName()))
								{
									present = true;
									break;
								}
							}
							if(!present)
							{
								missingOtaMappingsList.add(otaName);
							}
						}
		
						for( String missingOtaMapping : missingOtaMappingsList)
						{
							logger.error("Property OTA Mapping missing for property: " + propertyDetailsDto.getName() + "For Property OTA: " + missingOtaMapping );
						} 
						if(missingOtaMappingsList != null || missingOtaMappingsList.size() == 0 )
						{
							transactionalDeatilsService.saveTransactionDetailsList(transactionDetailsDTOList);
						}
						logger.info("TransactionDetailsStaahExcelTest::testCSVExtract end time" + new Date());
		
					}
				}
				
			}
			else
			{
				logger.error("Extract Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
				logger.error("Extract Data location is: " + basePath );
				throw new ISellProcessingException(1, "Extract Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
			}
		}
	catch(ISellDBException | ISellFileException e )
	{

		logger.error("Excception in PopulateDataForExtractsByProperty" + e.getMessage());
	}
}

}
