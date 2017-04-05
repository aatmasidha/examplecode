package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.events.SeverityDao;
import com.ai.sample.db.model.configuration.events.Severity;

@Service("severityConfigurationService")
@Transactional
public class SeverityConfigurationServiceImpl implements SeverityConfigurationService{

	@Autowired
	SeverityDao  severityDao;

	@Override
	public void saveSeverityConfiguration(String severityName)
			throws ISellDBException {
		Severity severityConfiguration = severityDao.findSeverityByName(severityName);
		if(severityConfiguration == null)
		{
			severityConfiguration =  new Severity(severityName);
		}
		severityDao.saveSeverity(severityConfiguration);		
	}

	@Override
	public List<String> findAllSeverityConfiguration() throws ISellDBException {
		List<Severity> severityList = severityDao.findAllSeverity();
		List<String> severityNameList = new ArrayList<String>();
		for(Severity severity : severityList)
		{
			severityNameList.add(severity.getName());
		}
		return severityNameList;
	}

	@Override
	public String findSeverityConfigurationByName(String severityName)
			throws ISellDBException {

		Severity severityConfiguration = severityDao.findSeverityByName(severityName);
		
		if(severityConfiguration == null)
		{
			throw new ISellDBException(-1, "Severity Name not found: " + severityName);
		}
		return severityConfiguration.getName();
	}

	@Override
	public void deleteSeverityConfigurationByName(String severityName)
			throws ISellDBException {
		severityDao.deleteSeverityByName(severityName);
	}
}
