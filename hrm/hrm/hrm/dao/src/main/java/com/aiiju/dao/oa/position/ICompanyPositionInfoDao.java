package com.aiiju.dao.oa.position;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.position.CompanyPositionInfo;
/**
 * @Description: 公司职务Dao
 * @author 琪琪
 * @date 2016年8月9日 上午11:02:23 
 */
public interface ICompanyPositionInfoDao {
	/**
	 * 添加职位
	 * @param params
	 * @return
	 */
	int addPositionInfo(Object obj);
	/**
	 * 修改职位
	 * @param params
	 * @return
	 */
	int updatePositionInfo(Object obj);
	/**
	 * 删除职位
	 * @param params
	 * @return
	 */
	int delPositionInfo(Map<String,Object> params);
	
	/**
	 * 获取职位列表
	 * @param params
	 * @return
	 */
	 List<CompanyPositionInfo> getPositionInfoListByParams(Map<String, Object> params);
	
	 /**
	 * 获取职位列表数据量
	 * @param params
	 * @return
	 */
	 Integer getPositionInfoListCountByParams(Map<String, Object> params);
	/**
	 * 根据I的获取职位对象
	 * @param id
	 * @return
	 */
	CompanyPositionInfo getPositionInfoById(Long id);
	/**
	 * 通过名字获取id 名称和公司组合确定唯一
	 * @author 维斯
	 *
	 * 2016年11月10日   下午4:23:58
	 */
	CompanyPositionInfo getPositionInfoByName(CompanyPositionInfo info);
	
	/**
	 * 批量新增职位信息（已存在则更新）
	 * @param list
	 */
	void batchAddPositionInfo(Map<String, Object> params);
}