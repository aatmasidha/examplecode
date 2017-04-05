package com.ai.sample.db.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.sample.common.dto.RolesDTO;
import com.ai.sample.db.service.RolesService;



public class RolesDBTest  extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(RolesDBTest.class);
	
	@Autowired
	RolesService rolesService;
	
	@Test
//	@Rollback(false)
	public void testGetUsersForMeetingWithStatus() throws Exception{
		try
		{
			RolesDTO roleDTO = new RolesDTO("RM", "Revenue Manager");
			rolesService.saveRole(roleDTO);
			logger.debug("RoleDTO : " + roleDTO.toString());
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}
