package com.aiiju.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: ITemplateSettingService
 * @Description: 模板设置
 * @author BZ
 * @date 2016年10月18日 上午11:02:23 
 *
 */
@Repository
public interface ITemplateSettingService {
	/**
	 * 更新模板设置是否启用
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateTemplateShow(Map<String,Object> params);
	
	/**
	 * 
	 * @Title: queryTemplateList 
	 * @Description: 获取模板列表
	 * @param params
	 * @return 
	 * @throws
	 */
	public Map<String, Object> queryTemplateList(Map<String, Object> params);

}
