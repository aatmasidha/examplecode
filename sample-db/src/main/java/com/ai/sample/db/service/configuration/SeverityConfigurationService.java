package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;

public interface SeverityConfigurationService {

	public void saveSeverityConfiguration(String severityName) throws ISellDBException;
	
	public List<String> findAllSeverityConfiguration() throws ISellDBException;
	
	public String findSeverityConfigurationByName(String severityName) throws ISellDBException;
	
	public void deleteSeverityConfigurationByName(String severityName) throws ISellDBException;
}
