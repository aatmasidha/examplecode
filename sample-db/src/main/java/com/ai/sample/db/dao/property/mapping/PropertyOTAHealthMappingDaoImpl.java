package com.ai.sample.db.dao.property.mapping;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

@Repository("propertyOTAHealthMappingDao")
public class PropertyOTAHealthMappingDaoImpl extends AbstractDao implements
PropertyOTAHealthMappingDao {

	
	static final Logger LOGGER = Logger
			.getLogger(PropertyOTAHealthMappingDaoImpl.class);

	@Override
	public void deletePropertyOTAHealthMappingForOTA(
			PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping)
			throws ISellDBException {
		try {
			Query query = getSession().createQuery(
					"delete from PropertyOTAHealthMapping where propertyotaconnectionmappingid = :propertyotaconnectionmappingid");
			query.setParameter("propertyotaconnectionmappingid", propertyOnlineTravelAgentConnectionMapping);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOTAHealthMappingDaoImpl::deletePropertyOTAHealthMappingForOTA : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
