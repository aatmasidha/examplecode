package com.ai.sample.db.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.common.dto.ui.PropertyOnlineTravelAgentMappingDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyOTAHealthMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyOnlineTravelAgentConnectionMappingDaoImpl;
import com.ai.sample.db.dao.property.recommendation.PQMAlgoDataDao;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

@Service("propertyOnlineTravelAgentConnectionMappingService")
@Transactional
public class PropertyOnlineTravelAgentConnectionMappingServiceImpl implements
PropertyOnlineTravelAgentConnectionMappingService {

	Logger logger = Logger
			.getLogger(PropertyOnlineTravelAgentConnectionMappingServiceImpl.class);

	@Autowired(required = true)
	PropertyOnlineTravelAgentConnectionMappingDaoImpl propertyOTAConnectionMappingDao;

	@Autowired(required = true)
	PropertyDetailsDao propertyDetailsDao;

	@Autowired(required = true)
	OnlineTravelAgentDetailsDao onlineTravelAgentDetailsDao;

	@Autowired(required = true)
	PQMAlgoDataDao pQMAlgoDataDao;
	
	@Autowired(required=true)
	PropertyOTAHealthMappingDao propertyOTAHealthMappingDao;
	
	@Override
	@Transactional
	public void saveOnlineTravelAgent(PropertyDetailsDTO propertyDetailsDto,
			String otaName, String propertyOtaName) throws ISellDBException {
		try {
			PropertyOnlineTravelAgentConnectionMapping propertyOtaDetails = propertyOTAConnectionMappingDao
					.findPropertyOnlineTravelAgentByPropertyAndOta(
							propertyDetailsDto, propertyOtaName);
			if (propertyOtaDetails == null) {
				PropertyDetails propertyDetails = propertyDetailsDao
						.findPropertyDetails(propertyDetailsDto);
				if (propertyDetails == null) {
					logger.error("Property to define mapping for OTA is not present in the database: "
							+ propertyDetailsDto.toString());
					throw new ISellDBException(1,
							"Property to define mapping for OTA is not present in the database: "
									+ propertyDetailsDto.toString());
				}
				OnlineTravelAgentDetails onlineTravelAgent = onlineTravelAgentDetailsDao
						.findOnlineTravelAngentByName(otaName);
				if (onlineTravelAgent == null) {
					logger.error("OTA to define mapping for Property is not present in the database: "
							+ otaName);
					throw new ISellDBException(1,
							"OTAto define mapping for Property is not present in the database: "
									+ otaName);
				}
				propertyOtaDetails = new PropertyOnlineTravelAgentConnectionMapping(
						propertyDetails, onlineTravelAgent, propertyOtaName);
			}
			propertyOTAConnectionMappingDao.saveOrUpdate(propertyOtaDetails);
		} catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyOnlineTravelAgentConnectionMappingServiceImpl::saveOnlineTravelAgent: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<PropertyOtaConnectionMappingDTO> findAllOtsForProperty(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		try {
			List<PropertyOnlineTravelAgentConnectionMapping> propertyOtaConnectionMappingList = propertyOTAConnectionMappingDao
					.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto);
			if (propertyOtaConnectionMappingList == null
					|| propertyOtaConnectionMappingList.size() <= 0) {
				logger.error("OTA mapping is not defined for property : "
						+ propertyDetailsDto.toString());
				throw new ISellDBException(1,
						"OTA mapping is not defined for property : "
								+ propertyDetailsDto.toString());
			}
			List<PropertyOtaConnectionMappingDTO> propertyOtaConnectionMappingDTOList = new ArrayList<PropertyOtaConnectionMappingDTO>();
			for (PropertyOnlineTravelAgentConnectionMapping propertyOtaConnectionMapping : propertyOtaConnectionMappingList) {
				OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto = new OnlineTravelAgentDetailsDTO(
						propertyOtaConnectionMapping.getOtaDetails().getId(),
						propertyOtaConnectionMapping.getOtaDetails().getName(),
						propertyOtaConnectionMapping.getOtaDetails()
						.getDisplayName());
				PropertyOtaConnectionMappingDTO propertyOtaConnectionMappingDTO = new PropertyOtaConnectionMappingDTO(
						propertyOtaConnectionMapping.getId(),
						propertyDetailsDto, onlineTravelAgentDetailsDto,
						propertyOtaConnectionMapping.getPropertyOtaName());

				propertyOtaConnectionMappingDTOList
				.add(propertyOtaConnectionMappingDTO);
			}
			return propertyOtaConnectionMappingDTOList;
		} catch (HibernateException e) {
			logger.error("HibernateException in PropertyOnlineTravelAgentConnectionMappingServiceImpl::findAllOtsForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void saveOnlineTravelAgent(PropertyDetailsDTO propertyDetailsDto,
			PropertyOnlineTravelAgentMappingDTO propertyOTANameOld,
			PropertyOnlineTravelAgentMappingDTO propertyOTANameNew, boolean edit) throws ISellDBException {
		try {
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			OnlineTravelAgentDetailsDTO onlineTravelAgentNew = propertyOTANameNew.getOtaDetailsDTO();
			String masterOtaName = onlineTravelAgentNew.getOtaName();
			OnlineTravelAgentDetails onlineTravelAgentMasterNew = onlineTravelAgentDetailsDao.findOnlineTravelAngentByName(masterOtaName);
			if(onlineTravelAgentMasterNew == null )
			{
				logger.error("Master OTA name is not present in database: "+ masterOtaName);
				throw new ISellDBException(-1, "Master OTA name is not present in database: "+ masterOtaName);
			}
			
			
			if (edit == false) 
			{
				PropertyOnlineTravelAgentConnectionMapping propertyOtaDetailsNew = propertyOTAConnectionMappingDao
						.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto, propertyOTANameNew.getPropertyOTAName());
				if (propertyOtaDetailsNew != null) {
					logger.error("OTA name to add already exist: "+ propertyOTANameNew);
					throw new ISellDBException(-1, "OTA name to add already exist: "
									+ propertyOTANameNew);
				}
				
				PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping =
						new PropertyOnlineTravelAgentConnectionMapping(propertyDetails, onlineTravelAgentMasterNew, propertyOTANameNew.getPropertyOTAName());
				propertyOTAConnectionMappingDao.saveOrUpdatePropertyOtaMapping(propertyOnlineTravelAgentConnectionMapping);
			} else {
				OnlineTravelAgentDetailsDTO onlineTravelAgentOld = propertyOTANameOld.getOtaDetailsDTO();
				PropertyOnlineTravelAgentConnectionMapping propertyOtaDetailsOld = propertyOTAConnectionMappingDao
						.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto, propertyOTANameOld.getPropertyOTAName());
				if (propertyOtaDetailsOld == null) {
					logger.error("OTA name to change configuration does not exist: " + propertyOTANameOld);
					throw new ISellDBException(-1,
							"OTA name to change configuration does not exist: "
									+ propertyOTANameOld);
				}
				
				PropertyOnlineTravelAgentConnectionMapping propertyOtaDetailsNew = propertyOTAConnectionMappingDao
						.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto, propertyOTANameNew.getPropertyOTAName());
				if (propertyOtaDetailsNew != null) {
					throw new ISellDBException(-1, "OTA name to change configuration already exist: "
									+ propertyOTANameNew);
				}
				
				if( !onlineTravelAgentOld.getOtaName().equalsIgnoreCase(onlineTravelAgentNew.getOtaName()))
				{
					propertyOtaDetailsOld.setOtaDetails(onlineTravelAgentMasterNew);
				}
				propertyOtaDetailsOld.setPropertyOtaName(propertyOTANameNew.getPropertyOTAName());
				propertyOTAConnectionMappingDao.saveOrUpdate(propertyOtaDetailsOld);
			}
		} catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyOnlineTravelAgentConnectionMappingServiceImpl::saveOnlineTravelAgent: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteOnlineTravelAgent(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyOnlineTravelAgentMappingDTO propertyOnlineTravelAgentMappingDTO)
			throws ISellDBException {
		try
		{
			String propertyOtaName = propertyOnlineTravelAgentMappingDTO.getPropertyOTAName();
			PropertyOnlineTravelAgentConnectionMapping propertyOTAConnection = propertyOTAConnectionMappingDao.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto, propertyOtaName);
			
			if(propertyOTAConnection == null)
			{
				logger.error("Ota information to delete does not exists: " + propertyOnlineTravelAgentMappingDTO.getPropertyOTAName() + " for Property: " + propertyDetailsDto.getName());
				throw new ISellDBException(-1, "Ota information to delete does not exists: " + propertyOnlineTravelAgentMappingDTO.getPropertyOTAName() + " for Property: " + propertyDetailsDto.getName());
			}
			propertyOTAHealthMappingDao.deletePropertyOTAHealthMappingForOTA(propertyOTAConnection);
			pQMAlgoDataDao.deletePQMAlgoDataForOta(propertyOTAConnection);
			propertyOTAConnectionMappingDao.deleteConnectionForOta(propertyDetailsDto, propertyOtaName);
		}
		catch (ISellDBException e) {
			logger.error("Exception in PropertyOnlineTravelAgentConnectionMappingServiceImpl::saveOnlineTravelAgent: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
