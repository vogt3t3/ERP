package com.aiiju.dao.oa.register;

import java.util.Map;

/**
 * 公司dao
 * @author qiqi
 * @date 2016-12-28 11:11:11
 */
public interface ICompanyDao {
	/**
	 * 添加公司
	 * @param company
	 * @return
	 */
    Long addCompany(Map<String, Object> params);

}