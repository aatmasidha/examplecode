package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

@Service("onlineTravelAgentService")
@Transactional
public class OnlineTravelAgentServiceImpl implements OnlineTravelAgentService{

	@Autowired
	OnlineTravelAgentDetailsDao  onlineTravelAgentDetailsDao;
	
	@Override
	public void saveOnlineTravelAgent(OnlineTravelAgentDetailsDTO otaDetailsDto)
			throws ISellDBException {
		OnlineTravelAgentDetails otaDetails = onlineTravelAgentDetailsDao.findOnlineTravelAngentByName(otaDetailsDto.getOtaName());
		if(otaDetails == null)
		{
			otaDetails = new OnlineTravelAgentDetails(otaDetailsDto.getOtaName(), otaDetailsDto.getOtaDisplayName());
		}
		onlineTravelAgentDetailsDao.saveOnlineTravelAgentDetails(otaDetails);	
	}

	@Override
	public List<OnlineTravelAgentDetailsDTO> findAllOnlineTravelAgents()
			throws ISellDBException {
		List<OnlineTravelAgentDetailsDTO> onlineTravelAgentDetailsDTOList = new ArrayList<OnlineTravelAgentDetailsDTO>();
		List<OnlineTravelAgentDetails> onlineTravelAgentList = onlineTravelAgentDetailsDao.findAllOnlineTravelAgent();
		for(OnlineTravelAgentDetails onlineTravelAgentDetails : onlineTravelAgentList)
		{
			OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDTO 
				= new OnlineTravelAgentDetailsDTO( onlineTravelAgentDetails.getId(), 
						onlineTravelAgentDetails.getName(), onlineTravelAgentDetails.getDisplayName());
			onlineTravelAgentDetailsDTOList.add(onlineTravelAgentDetailsDTO);
		}
		return onlineTravelAgentDetailsDTOList;
	}

	@Override
	public OnlineTravelAgentDetailsDTO findOnlineTravelAgentByName(
			String otaName) throws ISellDBException {
		OnlineTravelAgentDetails otaDetails = onlineTravelAgentDetailsDao.findOnlineTravelAngentByName(otaName);
		OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDTO = new OnlineTravelAgentDetailsDTO(otaDetails.getId(), otaDetails.getName(), otaDetails.getDisplayName());
		return onlineTravelAgentDetailsDTO;
	}

	@Override
	public void deleteOnlineTravelAgentNameByName(String otaName)
			throws ISellDBException {
		onlineTravelAgentDetailsDao.deleteOnlineTravelAgentByName(otaName);
	}

}
