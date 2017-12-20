package com.aiiju.dao.oa.duty;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.duty.PositionTypeInfo;
/**
 * @description 职务类别/职务等级Dao
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 */
@Repository
public interface IPositionTypeInfoDao {
	/**
	 * 添加职等/职务类别 1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int addDutyLevel(Object obj);
	/**
	 * 修改职等/职务类别  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int updateDutyLevel(Object obj);
	/**
	 * 删除职等/职务类别  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	int delDutyLevel(Map<String,Object> params);
	
	/**
	 * 获取职等/职务类别列表  1职务类别 2 职等
	 * @param params
	 * @return
	 */
	List<PositionTypeInfo> getDutyLevelListByParams(Map<String, Object> params);
	/**
	 * 根据Id查询职等类型对象
	 * @param id
	 * @return
	 */
	PositionTypeInfo getPositionTypeInfoById(Long id);


}