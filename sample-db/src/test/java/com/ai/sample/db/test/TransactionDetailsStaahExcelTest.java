package com.ai.sample.db.test;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.db.service.mapping.PropertyOnlineTravelAgentConnectionMappingService;
import com.ai.sample.db.service.property.transaction.TransactionalDetailsService;
import com.ai.sample.isell.util.IExtractFile;
import com.ai.sample.isell.util.ReadExcelExtractFile;
import com.ai.sample.isell.util.ReadJSON;
import com.ai.sample.isell.util.ReadStaahCSVExtract;

public class TransactionDetailsStaahExcelTest  extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(TransactionDetailsStaahExcelTest.class);
	
	@Autowired
	TransactionalDetailsService transactionalDeatilsService;
	
	@Autowired
	PropertyOnlineTravelAgentConnectionMappingService propertyOnlineTravelAgentConnectionMappingService;
	
	@Test
	@Rollback(false)
	public void testCSVExtract() throws Exception{
		try
		{
			logger.info("TransactionDetailsTest::testCSVExtract start time" + new Date());
			IExtractFile readExtractCSV = null;
			ReadJSON readConfigJson = new ReadJSON();
			
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("integrationexcelconfiguration.json").getFile());
			
			IntegrationFileFormatDTO  integrationFileFormatDTO =readConfigJson.getExtractFileTypeDetails(file.getAbsolutePath(),"staah");
//			IntegrationFileFormatDTO  integrationFileFormatDTO = readExtractCSV.getExtractFileTypeDetails("Hotel Aurora Towers","staah");
			if(integrationFileFormatDTO.getFileType().equalsIgnoreCase("csv"))
			{
				readExtractCSV = new ReadStaahCSVExtract();
			}
			else if( integrationFileFormatDTO.getFileType().equalsIgnoreCase("excel") )
			{
				 readExtractCSV = new ReadExcelExtractFile();
			}
			
			CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
			propertyDetailsDto.setCityDto(cityDto);
			propertyDetailsDto.setName("Hotel Aurora Towers");
			ArrayList<TransactionDetailsDTO> transactionDetailsDTOList = readExtractCSV.parseExcelFile(propertyDetailsDto, 
					"D:/Documents/isell/requirementdocuments/Staah_Aurora_Tower/Staah_Data 20th Sep.xls", integrationFileFormatDTO);
			
			
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
		catch(Exception e)
		{
			logger.error("Failure while executing the TransDetailsTest::testCSVExtract" + e.getMessage());
			logger.error("Failure while executing the TransDetailsTest::testCSVExtract" + e.getCause());
			e.getStackTrace();
			assertFalse(true);
		}
	}
}
