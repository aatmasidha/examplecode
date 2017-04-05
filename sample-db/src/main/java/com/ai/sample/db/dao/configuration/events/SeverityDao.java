package com.ai.sample.db.dao.configuration.events;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.events.Severity;

public interface SeverityDao {

	void saveSeverity(Severity severity) throws ISellDBException ;
	
	List<Severity> findAllSeverity() throws ISellDBException;
	
	Severity findSeverityByName(String severityName) throws ISellDBException;
	
	void deleteSeverityByName(String severityName) throws ISellDBException;

	Severity getOrCreateSeverityByName(String severityName) throws ISellDBException;
}
