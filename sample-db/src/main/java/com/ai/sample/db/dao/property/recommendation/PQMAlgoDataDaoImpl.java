package com.ai.sample.db.dao.property.recommendation;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

@Repository("PQMAlgoDataDao")
public class PQMAlgoDataDaoImpl extends AbstractDao implements
		PQMAlgoDataDao {

	static final Logger LOGGER = Logger
			.getLogger(PQMAlgoDataDaoImpl.class);

	@Override
	public void deletePQMAlgoDataForOta(
			PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping)
			throws ISellDBException {
		try {
			Query query = getSession().createQuery(
					"delete from PQMAlgoData where propertyotamappingid = :propertyotamappingid");
			query.setParameter("propertyotamappingid", propertyOnlineTravelAgentConnectionMapping);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PQMAlgoDataDaoImpl::deletePQMAlgoDataForOta : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		
	}
}
