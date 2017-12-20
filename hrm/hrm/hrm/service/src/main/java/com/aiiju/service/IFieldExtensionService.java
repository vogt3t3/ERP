package com.aiiju.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: IFieldExtension
 * @Description: 字段扩展 接口
 * @author BZ
 * @date 2016年10月18日 上午11:02:23 
 *
 */
@Repository
public interface IFieldExtensionService {
	/**
	 * 修改字段扩展信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateFieldExtensionInfo(Map<String,Object> params);
	
	/**
	 * 
	 * @Title: getWorkOverTimeLogsByParams 
	 * @Description: 获取字段扩展 列表
	 * @param params
	 * @return 
	 * @throws
	 */
	public Map<String, Object> getFieldExtensionInfo(Map<String, Object> params);

}
