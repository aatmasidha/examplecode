package com.ai.sample.db.service.ota;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

@Service("onlineTravelAgentDetailsService")
@Transactional
public class OnlineTravelAgentDetailsServiceImpl implements OnlineTravelAgentDetailsService{

	Logger logger = Logger.getLogger(OnlineTravelAgentDetailsServiceImpl.class);

	@Autowired
	OnlineTravelAgentDetailsDao  onlineTravelAgentDetailsDao;
	
	@Override
	public void saveOnlineTravelAgentDetails(
			OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto)
			throws ISellDBException {
		
		OnlineTravelAgentDetails onlineTravelAgentDetails = null;
		String otaName = onlineTravelAgentDetailsDto.getOtaName();
		onlineTravelAgentDetails = onlineTravelAgentDetailsDao.findOnlineTravelAngentByName(otaName);
		if(onlineTravelAgentDetails == null )
		{
			onlineTravelAgentDetails = new OnlineTravelAgentDetails();
			onlineTravelAgentDetails.setName(onlineTravelAgentDetailsDto.getOtaName());
		}
		onlineTravelAgentDetails.setDisplayName(onlineTravelAgentDetailsDto.getOtaDisplayName());
		onlineTravelAgentDetailsDao.saveOnlineTravelAgentDetails(onlineTravelAgentDetails);
	}

	@Override
	public List<OnlineTravelAgentDetailsDTO> findAllOnlineTravelAgentDetails() throws ISellDBException{
		List<OnlineTravelAgentDetails> onlineTravelAgentDetailsList = onlineTravelAgentDetailsDao.findAllOnlineTravelAgent();
		List<OnlineTravelAgentDetailsDTO>  onlineTravelAgentDetailsDTOList = new ArrayList<OnlineTravelAgentDetailsDTO>(); 
		for( OnlineTravelAgentDetails onlineTravelAgentDetails : onlineTravelAgentDetailsList)
		{
			OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto = new OnlineTravelAgentDetailsDTO(onlineTravelAgentDetails.getName(), onlineTravelAgentDetails.getDisplayName());
			onlineTravelAgentDetailsDTOList.add(onlineTravelAgentDetailsDto);	
		}
		return onlineTravelAgentDetailsDTOList;
	}

	@Override
	public OnlineTravelAgentDetailsDTO findAllOnlineTravelAgentDetails(
			String onlineTravelAgentName) throws ISellDBException {
		
		OnlineTravelAgentDetails onlineTravelAgentDetails = onlineTravelAgentDetailsDao.findOnlineTravelAngentByName(onlineTravelAgentName);
		OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto = new OnlineTravelAgentDetailsDTO(onlineTravelAgentDetails.getName(), onlineTravelAgentDetails.getDisplayName());
		return onlineTravelAgentDetailsDto;
	}
}
