package com.aiiju.dao.oa.duty;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.duty.CompanyDutyInfo;
/**
 * @Description: 公司职务Dao
 * @author 琪琪
 * @date 2016年8月9日 上午11:02:23 
 */
public interface ICompanyDutyInfoDao {
	/**
	 * 添加职务
	 * @param params
	 * @return
	 */
	int addDutyInfo(Object obj);
	/**
	 * 修改职务
	 * @param params
	 * @return
	 */
	int updateDutyInfo(Object o);
	/**
	 * 删除职务
	 * @param params
	 * @return
	 */
	int delDutyInfo(Map<String,Object> params);
	
	/**
	 * 获取职务列表
	 * @param params
	 * @return
	 */
	List<CompanyDutyInfo> getDutyListByParams(Map<String, Object> params);
	/**
	 * 获取职务列表条数
	 * @param params
	 * @return
	 */
	int getDutyListCountByParams(Map<String, Object> params);
	/**
	 * 根据Id查询职务对象
	 * @param id
	 * @return
	 */
	CompanyDutyInfo getDutyInfoById(Long id);
	
	/**
	 * 批量新增职务数据（存在就更新，不存在就新增）
	 * @param list
	 */
	void batchAddOrUpdateDutyInfo(List<CompanyDutyInfo> list);
	
}