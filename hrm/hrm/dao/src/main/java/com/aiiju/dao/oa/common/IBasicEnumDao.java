package com.aiiju.dao.oa.common;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.common.BasicEnum;
import com.aiiju.bean.common.City;
import com.aiiju.bean.common.Specialty;
import com.aiiju.bean.common.Univs;
/**
 * @Description: 基础枚举类Dao
 * @author 琪琪
 * @date 2016年8月9日 上午11:02:23 
 */
public interface IBasicEnumDao {
	
	/**
	 * 根据参数查询枚举表数据
	 * @param params
	 * @return
	 */
	public List<BasicEnum> getBasicEnumListByParams(Map<String,Object> params);
	/**
	 * 获取城市列表
	 * @author 维斯
	 * 2016年12月7日   上午9:35:57
	 */
	List<City> getCitys(Map<String,Object> params);

	/**
	 * 获取专业及技能
	 * @author 维斯
	 * 2016年12月7日   上午9:37:57
	 */
	List<Specialty> getSpecialty(Map<String,Object> params);
	/**
	 * 获取学校
	 * @author 维斯
	 * 2016年12月7日   上午9:38:30
	 */
	List<Univs> getUnivs(Map<String,Object> params);
}