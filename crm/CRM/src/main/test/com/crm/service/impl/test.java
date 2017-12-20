package com.crm.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crm.model.Cst_linkman_t;
import com.crm.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class test extends AbstractJUnit4SpringContextTests {

	@Resource(name = "clientService")
	private ClientService clientService;

	@Test
	public void test() {
		Cst_linkman_t c = clientService.find(5, Cst_linkman_t.class);
		
	}

}
