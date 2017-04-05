package com.ai.sample.db.dao.property.mapping;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.constants.EnumConstants.ExecutionStatusCodeConstant;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.configuration.RoomTypeMasterDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.transaction.OtherTransactionalDetailsDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingRatesByDayDao;
import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;

@Repository("propertyRoomTypeMappingDao")
public class PropertyRoomTypeMappingDaoImpl extends AbstractDao implements
PropertyRoomTypeMappingDao {

	static final Logger LOGGER = Logger
			.getLogger(PropertyRoomTypeMappingDaoImpl.class);

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	RoomTypeMasterDao roomTypeMasterDao;

	@Autowired
	RateShoppingRatesByDayDao rateShoppingRatesByDayDao;
	
	@Autowired
	OtherTransactionalDetailsDao otherTransactionalDetailsDao;
		
	@Override
	public void saveOrUpdateRoomType(
			PropertyRoomTypeMapping propertyRoomTypeMapping)
					throws ISellDBException {
		LOGGER.debug("Inside PropertyRoomTypeMappingDaoImpl::saveOrUpdateRoomType");
		try {
			saveOrUpdate(propertyRoomTypeMapping);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception while saving property room type information: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception while saving property room type information: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		LOGGER.debug("Exit PropertyRoomTypeMappingDaoImpl::saveOrUpdateRoomType");
	}

	@Override
	public PropertyRoomTypeMapping findRoomTypeByNameForProperty(
			String roomTypeName, PropertyDetails propertyDetails)
					throws ISellDBException {
		try {
			LOGGER.debug("Inside PropertyRoomTypeMappingDaoImpl::findRoomTypeByNameForProperty");
			final Criteria criteria = getSession().createCriteria(
					PropertyRoomTypeMapping.class);
			roomTypeName = roomTypeName.trim();

			if (roomTypeName == null || roomTypeName.length() <= 0) {
				// It is observed that for some of the room types
				// we do not get room type for some of the transcations

				roomTypeName = "Hotel";
			}
			criteria.add(Restrictions.eq("name", roomTypeName));
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.setCacheable(true);
			PropertyRoomTypeMapping roomType = (PropertyRoomTypeMapping) criteria
					.uniqueResult();
		/*	if (roomType == null) {
				LOGGER.debug("Exit PropertyRoomTypeMappingDaoImpl::findRoomTypeByNameForProperty");
				throw new ISellDBException(1,
						"Can not store null room type for property"
								+ propertyDetails.toString());
			}*/
			return roomType;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in findRoomTypeByNameForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<PropertyRoomTypeMapping> findRoomTypesMappedToMasterRoomType(
			String masterRoomType) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyRoomTypeMapping> findAllRoomTypes()
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyRoomTypeMapping> findAllPropertyRoomTypes(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		try {
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDto);
			if (propertyDetails != null) {
				final Criteria criteria = getSession().createCriteria(
						PropertyRoomTypeMapping.class);
				criteria.add(Restrictions
						.eq("propertyDetails", propertyDetails));
				List<PropertyRoomTypeMapping> propertyRoomTypeMappingList = (List<PropertyRoomTypeMapping>) criteria
						.list();
				LOGGER.debug("Exit PropertyRoomTypeMappingDaoImpl::findRoomTypeByNameForProperty");
				return propertyRoomTypeMappingList;
			} else {
				LOGGER.error("Property Room type mapping for property: "
						+ propertyDetailsDto.toString());
				throw new ISellDBException(1,
						"Property Room type mapping for property: "
								+ propertyDetailsDto.toString());
			}
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingDaoImpl::findAllPropertyRoomTypes "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<PropertyRoomTypeMapping> findAllPropertyRoomTypes(
			PropertyDetails propertyDetails) throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					PropertyRoomTypeMapping.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			List<PropertyRoomTypeMapping> propertyRoomTypeMappingList = (List<PropertyRoomTypeMapping>) criteria
					.list();
			LOGGER.debug("Exit PropertyRoomTypeMappingDaoImpl::findRoomTypeByNameForProperty");
			return propertyRoomTypeMappingList;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingDaoImpl::findAllPropertyRoomTypes "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deletePropertyRoomTypeByName(String roomTypeName,
			PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException {
		try {
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDTO);
			
			PropertyRoomTypeMapping propertyRoomTypeMapping = findRoomTypeByNameForProperty(roomTypeName, propertyDetails);

			rateShoppingRatesByDayDao.deleteRatesByPropertyRoomTypeMapping(propertyRoomTypeMapping);

			Query query = getSession().createQuery(
					"delete from PropertyRoomTypeMapping where name = :name and propertydetailsid = :propertydetailsid");
			query.setString("name", roomTypeName);
			query.setParameter("propertydetailsid", propertyDetails);
			int returnValue = query.executeUpdate();
			LOGGER.debug("Delete return value is: " + returnValue);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingServiceImpl::PropertyRoomTypeMappingServiceImpl : "
					+ e.getCause());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void updatePropertyRoomTypes(
			PropertyRoomTypeMapping propertyRoomTypeMapping,
			PropertyRoomTypeMappingDTO propertyRoomTypeMappingNewValuesDTO)
					throws ISellDBException {

		try {
			RoomTypeMaster roomTypeMaster = propertyRoomTypeMapping
					.getRoomTypeMaster();
			String materRoomTypeName = propertyRoomTypeMappingNewValuesDTO
					.getMasterRoomTypeDTO().getMasterRoomType();
			if (!roomTypeMaster.getName().equalsIgnoreCase(materRoomTypeName)) {
				roomTypeMaster = roomTypeMasterDao
						.findRoomTypeByName(materRoomTypeName);
			}

			if (roomTypeMaster == null) {
				LOGGER.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Master Roomtype to map to property is missing: "
						+ roomTypeMaster);
				throw new ISellDBException(
						ExecutionStatusCodeConstant.DATAERROR.getCode(),
						"PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Master Roomtype to map to property is missing: "
								+ roomTypeMaster);
			}

			String updateQuery = "update property_room_type_mapping set isdefault = "
					+ propertyRoomTypeMappingNewValuesDTO.isDefault()
					+ " ,numrooms = "
					+ propertyRoomTypeMappingNewValuesDTO.getNumRooms()
					+ " ,name = "
					+ "\'"
					+ propertyRoomTypeMappingNewValuesDTO
					.getPropertyRoomTypeName()
					+ "\'"
					+ " ,roomtypemasterid = "
					+ roomTypeMaster.getId()
					+ " where id = " + propertyRoomTypeMapping.getId();
			Query query = getSession().createSQLQuery(updateQuery);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingDaoImpl::updatePropertyRoomTypes : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deletePropertyRoomTypeByName(
			PropertyRoomTypeMappingDTO propertyRoomTypeMappingDTO)
					throws ISellDBException {
		try {
			PropertyRoomTypeMapping propertyRoomTypeMapping = findRoomTypeById(propertyRoomTypeMappingDTO.getId());

			rateShoppingRatesByDayDao.deleteRatesByPropertyRoomTypeMapping(propertyRoomTypeMapping);

			Query query = getSession().createQuery(
					"delete from PropertyRoomTypeMapping where id = :id");
			query.setInteger("id", propertyRoomTypeMappingDTO.getId());
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingServiceImpl::PropertyRoomTypeMappingServiceImpl : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}

	@Override
	public PropertyRoomTypeMapping findRoomTypeById(int id) throws ISellDBException{
		try
		{
			final Criteria criteria = getSession().createCriteria(
					PropertyRoomTypeMapping.class);
			criteria.add(Restrictions.eq("id", id));
			PropertyRoomTypeMapping roomType = (PropertyRoomTypeMapping) criteria
					.uniqueResult();
			return roomType;
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingServiceImpl::findRoomTypeById : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteRoomTypesForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			otherTransactionalDetailsDao.deleteRoomTypeTransactionDetailsForProperty(propertyDetails);
			Query query = getSession().createQuery(
					"delete from PropertyRoomTypeMapping where propertydetailsid = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyRoomTypeMappingServiceImpl::deleteRoomTypesForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
