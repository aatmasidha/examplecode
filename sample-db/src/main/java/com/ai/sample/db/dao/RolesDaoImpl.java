package com.ai.sample.db.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.user.Roles;

@Repository("rolesDao")
public class RolesDaoImpl extends AbstractDao implements RolesDao{

	static final Logger LOGGER = Logger.getLogger(RolesDaoImpl.class);
	public void saveRole(Roles role)  throws JDBCException, SQLException {
			persist(role);
	}

	@SuppressWarnings("unchecked")
	public List<Roles> findAllRoles()  throws JDBCException, SQLException {
		final Criteria criteria = getSession().createCriteria(Roles.class);
		return (List<Roles>) criteria.list();
	}

	public void deleteRoleByName(String roleName) throws JDBCException, SQLException {
		Query query = getSession().createSQLQuery("delete from Roles where code = :code");
		query.setString("code", roleName);
		query.executeUpdate();
	}

	public Roles findRoleByCode(String roleName)  throws JDBCException, SQLException {
		final Criteria criteria = getSession().createCriteria(Roles.class);
		criteria.add(Restrictions.eq("code",roleName));
		Roles role = (Roles) criteria.uniqueResult();
		return  role;
	}
}
