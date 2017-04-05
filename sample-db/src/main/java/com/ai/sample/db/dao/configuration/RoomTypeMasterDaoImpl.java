package com.ai.sample.db.dao.configuration;

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
import com.ai.sample.db.model.configuration.RoomTypeMaster;

@Repository("roomTypeMasterDao")
public class RoomTypeMasterDaoImpl extends AbstractDao implements RoomTypeMasterDao{

	static final Logger LOGGER = Logger.getLogger(RoomTypeMasterDaoImpl.class);

	@Override
	public RoomTypeMaster findRoomTypeByName(String roomTypeName) throws ISellDBException {
		try
		{
			LOGGER.debug("Inside RoomTypeMasterDaoImpl::findRoomTypeByName");
			final Criteria criteria = getSession().createCriteria(RoomTypeMaster.class);
			roomTypeName = roomTypeName.trim();
			criteria.add(Restrictions.eq("name",roomTypeName));
			RoomTypeMaster roomType = (RoomTypeMaster) criteria.uniqueResult();
			LOGGER.debug("Exit RoomTypeMasterDaoImpl::findRoomTypeByName");
			return  roomType;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RoomTypeMasterDaoImpl::findRoomTypeByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void saveOrUpdateRoomType(RoomTypeMaster roomType)
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside RoomTypeMasterDaoImpl::saveOrUpdateRoomType");
			saveOrUpdate(roomType);
			LOGGER.debug("Exit RoomTypeMasterDaoImpl::saveOrUpdateRoomType");
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RoomTypeMasterDaoImpl::saveOrUpdateRoomType : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomTypeMaster> findAllMasterRoomTypes()
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside RoomTypeMasterDaoImpl::findAllMasterRoomTypes");
			final Criteria criteria = getSession().createCriteria(RoomTypeMaster.class);
			LOGGER.debug("Exit RoomTypeMasterDaoImpl::findAllMasterRoomTypes");
			return (List<RoomTypeMaster>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RoomTypeMasterDaoImpl::findAllMasterRoomTypes : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteMasterRoomTypeByName(String roomType)
			throws ISellDBException {
		try
		{
		LOGGER.debug("Inside RoomTypeMasterDaoImpl::deleteMasterRoomTypeByName");
		Query query = getSession().createSQLQuery("delete from RoomTypeMaster where name = :name");
		query.setString("name", roomType);
		query.executeUpdate();
		LOGGER.debug("Exit RoomTypeMasterDaoImpl::deleteMasterRoomTypeByName");
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RoomTypeMasterDaoImpl::deleteMasterRoomTypeByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
