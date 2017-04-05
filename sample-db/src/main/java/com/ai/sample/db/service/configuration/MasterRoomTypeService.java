package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface MasterRoomTypeService {
	public List<MasterRoomTypeDTO> findAllMasterRoomTypes() throws ISellDBException;
}
