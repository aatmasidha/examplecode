package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.dao.configuration.RoomTypeMasterDao;
import com.ai.sample.db.dao.configuration.StateDao;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.configuration.State;

@Service("masterRoomTypeService")
@Transactional
public class MasterRoomTypeServiceImpl implements MasterRoomTypeService {

	@Autowired
	RoomTypeMasterDao roomTypeMasterDao;
	
	@Override
	public List<MasterRoomTypeDTO> findAllMasterRoomTypes()
			throws ISellDBException {
		List<RoomTypeMaster> masterRoomTypeList = roomTypeMasterDao.findAllMasterRoomTypes();
		List<MasterRoomTypeDTO> masterRoomTypeDTOList = new ArrayList<MasterRoomTypeDTO>();
		for(int cnt =0; cnt < masterRoomTypeList.size(); cnt++)
		{
			RoomTypeMaster roomTypeMaster = masterRoomTypeList.get(cnt);
			MasterRoomTypeDTO masterRoomTypeDTO = new MasterRoomTypeDTO(roomTypeMaster.getId(), roomTypeMaster.getName());
			masterRoomTypeDTOList.add(masterRoomTypeDTO);
		}
		return masterRoomTypeDTOList;
	}

	

}
