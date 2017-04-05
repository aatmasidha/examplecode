package com.ai.sample.db.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.db.configuration.HibernateConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateConfiguration.class, loader=AnnotationConfigContextLoader.class)
@Transactional
@ActiveProfiles("test")

public class BaseTestConfiguration {
	

	@Autowired
	private Environment env;
	
	
	@Test
	public void testEnvironmentObject(){
		
		assertNotNull(env);
	}
	
	@Test
	public void testRetrieveProperties(){
		assertNotNull(env.getProperty("jdbc.driverClassName"));
	}
	

}
