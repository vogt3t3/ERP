package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRuleDept;

public interface ISignRuleDeptDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRuleDept record);

    int insertSelective(SignRuleDept record);

    SignRuleDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRuleDept record);

    int updateByPrimaryKey(SignRuleDept record);

	List<SignRuleDept> selectListByParams(Map<String, Object> params);

	int deleteByCompanyIdAndRuleId(Map<String, Object> map);
}