/**
 * 
 */
package com.ai.sample.isell.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellFileException;

/**
 * @author aparna
 *
 */
public class ReadStaahCSVExtract extends ReadCSVExtractFile implements IExtractFile{
	Logger logger =  Logger.getLogger(ReadStaahCSVExtract.class);

	public ReadStaahCSVExtract() {
		super();
	}

	public IntegrationFileFormatDTO readExcelFile(String integrationType) throws ISellFileException
    {
	        return super.getExtractFileTypeDetails( integrationType);
    }
	
	public ArrayList<TransactionDetailsDTO> parseExcelFile(PropertyDetailsDTO propertyDetailsDTO, String excelFileName,  IntegrationFileFormatDTO integrationExcelFormatDTO) throws ISellFileException
	{
		try
		{
			ArrayList<TransactionDetailsDTO> transactionalDetailsDTOList = super.parseExtractFile(propertyDetailsDTO, excelFileName, integrationExcelFormatDTO);
			 for (TransactionDetailsDTO transactionDetailsDTO : transactionalDetailsDTOList) {
				 String otaName = transactionDetailsDTO.getOtaName();
				 String[] otaNameSplitArray = otaName.split("-");
				 otaName = otaNameSplitArray[0];
				 transactionDetailsDTO.setOtaName(otaName);
	         }
			return transactionalDetailsDTOList;
		}
		catch(Exception e)
		{
			logger.error("Exception is: " + e.getMessage());
			return null;
		}
	}
	
	public static void main(String args[])
	{
		ReadStaahCSVExtract readExtractExcel = new ReadStaahCSVExtract();
		try {
			IntegrationFileFormatDTO integrationExcelFormatDTO = readExtractExcel.readExcelFile("staah");
			CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
			propertyDetailsDto.setCityDto(cityDto);
			propertyDetailsDto.setName("Taj");
			
			readExtractExcel.parseExcelFile(propertyDetailsDto,"D:/Documents/isell/requirementdocuments/Staah_Aurora_Tower/Staah_Data 14th Sep.xls", integrationExcelFormatDTO);
		} catch (ISellFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
