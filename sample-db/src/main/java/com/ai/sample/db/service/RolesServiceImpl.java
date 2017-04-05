package com.ai.sample.db.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.RolesDTO;
import com.ai.sample.db.dao.RolesDao;
import com.ai.sample.db.model.user.Roles;

@Service("rolesService")
@Transactional
public class RolesServiceImpl implements RolesService{

	@Autowired
	private RolesDao dao;
	
	@Rollback(false)
	public void saveRole(RolesDTO roleDTO) throws JDBCException, SQLException {
		Roles role = new Roles();
		role.setCode(roleDTO.getCode());
		role.setDescription(roleDTO.getDescription());
		dao.saveRole(role);
	}

	public List<RolesDTO> findAllRoles() throws JDBCException, SQLException {
		List<RolesDTO> allRolesDTOList = new ArrayList<RolesDTO>();
		List<Roles> allRolesList = dao.findAllRoles();
		for( Roles role : allRolesList )
		{
			RolesDTO roleDTO = new RolesDTO(role.getId(), role.getCode(), role.getDescription());
			allRolesDTOList.add(roleDTO);
		}
		return allRolesDTOList;
	}

	public RolesDTO findRoleByCode(String roleName) throws JDBCException, SQLException {
		Roles role = dao.findRoleByCode(roleName);
		RolesDTO roleDTO = new RolesDTO(role.getId(), role.getCode(), role.getDescription());
		return roleDTO;
	}

	public void deleteRoleByName(String roleName) throws JDBCException, SQLException {
		dao.deleteRoleByName(roleName);
	}
}
