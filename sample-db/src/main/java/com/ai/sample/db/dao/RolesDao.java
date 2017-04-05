package com.ai.sample.db.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.JDBCException;

import com.ai.sample.db.model.user.Roles;

public interface RolesDao {

	void saveRole(Roles role) throws JDBCException, SQLException ;
	
	List<Roles> findAllRoles() throws JDBCException, SQLException;
	
	Roles findRoleByCode(String ssn) throws JDBCException, SQLException;
	
	void deleteRoleByName(String roleName) throws JDBCException, SQLException;
}
