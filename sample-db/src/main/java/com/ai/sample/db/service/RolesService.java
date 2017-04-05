package com.ai.sample.db.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.JDBCException;

import com.ai.sample.common.dto.RolesDTO;

public interface RolesService {

	public void saveRole(RolesDTO role) throws JDBCException, SQLException;
	
	public List<RolesDTO> findAllRoles() throws JDBCException, SQLException;
	
	public RolesDTO findRoleByCode(String roleName) throws JDBCException, SQLException;
	
	public void deleteRoleByName(String roleName) throws JDBCException, SQLException;
}
