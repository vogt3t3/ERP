package com.ajy.web.timer;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.aiiju.service.salary.ISpwSalaryChangeService;

/**
 * 
 * @ClassName: SalaryUpdateServiceJob
 * @Description:薪资更新定时器
 * @author BZ
 * @date 2016年9月9日 上午11:02:23 
 *
 */
@Controller("salaryUpdateServiceJob")
public class SalaryUpdateServiceJob {
	
	private static Logger logger = LoggerFactory.getLogger(SalaryUpdateServiceJob.class);
	
	@Resource
	private ISpwSalaryChangeService spwSalaryChangeService;
	
	public void salaryUpdate(){
		System.currentTimeMillis();
		logger.info("----------------------薪资定时器START:"+new Date(System.currentTimeMillis())+"--------------------------------------");
		spwSalaryChangeService.updateSalary();
		logger.info("----------------------薪资定时器END:"+new Date(System.currentTimeMillis())+"--------------------------------------");
	}
}
