package com.ai.sample.db.dao.property.recommendation;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

public interface PQMAlgoDataDao {

	void deletePQMAlgoDataForOta(PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping) throws ISellDBException;
}
