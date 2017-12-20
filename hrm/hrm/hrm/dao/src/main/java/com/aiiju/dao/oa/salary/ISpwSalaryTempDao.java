package com.aiiju.dao.oa.salary;

import java.util.List;
import java.util.Map;
import com.aiiju.bean.oa.salary.SpwSalaryTemp;

/**
 * 工资单模板DAO
 * @author BZ
 *
 */
public interface ISpwSalaryTempDao {

	//存储勾选字段作为工资单模板
	public void insertTemp(List<SpwSalaryTemp> sstList);
	
	//根据条件查询工资单模板
	public List<SpwSalaryTemp> getSalaryTemp(Map<String,Object> param);
}
