package com.ai.sample.db.dao.property.mapping;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

public interface PropertyOTAHealthMappingDao {
  void deletePropertyOTAHealthMappingForOTA(PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping) throws ISellDBException;
}

