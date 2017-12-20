package com.aiiju.service;
import java.util.List;
import java.util.Map;

import com.aiiju.bean.common.BasicEnum;
import com.aiiju.bean.common.BasicEnumTree;
import com.aiiju.bean.common.City;
/**
 * 枚举类
 * @author qiqi
 * @date 2016-10-26 11:11:11
 */
public interface IBasicEnumService {
	/**
	 * 获取基础枚举列表
	 * @param params
	 * @return
	 */
	public  List<BasicEnum> getBasicEnumListByParams(Map<String,Object> params);
	/**
	 * 基础数据树
	 * @param params
	 * @return
	 */
	public  List<BasicEnumTree> getBasicEnumListTreeByPrams(Map<String, Object> params);
	/**
	 * 将基本数据存储到redis
	 * @return
	 */
	public  void storeBasicEnums(Map<String, Object> params);
	
	/**
	 *获取省市区
	 * @author 维斯
	 * @param params
	 * @return
	 * 2016年12月7日   下午3:06:53
	 */
	List<City> getCitys(Map<String, Object> params);
}
