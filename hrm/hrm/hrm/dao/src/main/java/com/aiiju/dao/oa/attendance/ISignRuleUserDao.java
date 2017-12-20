package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRuleUser;

public interface ISignRuleUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRuleUser record);

    int insertSelective(SignRuleUser record);

    SignRuleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRuleUser record);

    int updateByPrimaryKey(SignRuleUser record);

	List<SignRuleUser> selectListByParams(Map<String, Object> params);

	int deleteByCompanyIdAndRuleId(Map<String, Object> map);
}