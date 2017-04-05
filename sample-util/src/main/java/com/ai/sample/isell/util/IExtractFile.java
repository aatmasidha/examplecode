/**
 * 
 */
package com.ai.sample.isell.util;

import java.util.ArrayList;

import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.exception.ISellFileException;

/**
 * @author aparna
 *
 */


public interface IExtractFile{
	
	public IntegrationFileFormatDTO readExcelFile( String integrationType )throws ISellFileException;
    
	public ArrayList<TransactionDetailsDTO> parseExcelFile(PropertyDetailsDTO propertyDetailsDto,  String excelFileName,  IntegrationFileFormatDTO integrationExcelFormatDTO) throws ISellFileException;
	
}
