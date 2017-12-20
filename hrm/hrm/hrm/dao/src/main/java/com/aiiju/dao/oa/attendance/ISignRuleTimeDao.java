package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRuleTime;

public interface ISignRuleTimeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRuleTime record);

    int insertSelective(SignRuleTime record);

    SignRuleTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRuleTime record);

    int updateByPrimaryKey(SignRuleTime record);

	List<SignRuleTime> selectListByParams(Map<String, Object> param);

	int deleteByCompanyIdAndRuleId(Map<String, Object> map);

	int updateByMap(Map<String, Object> map);
}