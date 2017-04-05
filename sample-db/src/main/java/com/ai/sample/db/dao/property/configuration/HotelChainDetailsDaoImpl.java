package com.ai.sample.db.dao.property.configuration;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.property.configuration.HotelChainDetails;

@Repository("hotelChainDetailsDao")
public class HotelChainDetailsDaoImpl extends AbstractDao implements
		HotelChainDetailsDao {

	static final Logger LOGGER = Logger
			.getLogger(HotelChainDetailsDaoImpl.class);

	public void saveHotelChainDetails(HotelChainDetails hotelChain)
			throws ISellDBException {
		try {
			saveOrUpdate(hotelChain);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in HotelChainDetailsDaoImpl::saveHotelChainDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<HotelChainDetails> findAllHotelChains() throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					HotelChainDetails.class);
			return (List<HotelChainDetails>) criteria.list();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in HotelChainDetailsDaoImpl::findAllHotelChains : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public HotelChainDetails findHotelChainByName(String name)
			throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					HotelChainDetails.class);
			criteria.add(Restrictions.eq("name", name));
			HotelChainDetails hotelChainDetails = (HotelChainDetails) criteria
					.uniqueResult();
			return hotelChainDetails;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in HotelChainDetailsDaoImpl::findHotelChainByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public void deleteHotelChainByName(String name) throws ISellDBException {
		try {
			Query query = getSession().createSQLQuery(
					"delete from HotelChainDetails where name = :name");
			query.setString("name", name);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in HotelChainDetailsDaoImpl::deleteHotelChainByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
