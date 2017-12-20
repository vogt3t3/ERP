package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRuleTimeHistory;

public interface ISignRuleTimeHistoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRuleTimeHistory record);

    int insertSelective(SignRuleTimeHistory record);

    SignRuleTimeHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRuleTimeHistory record);

    int updateByPrimaryKey(SignRuleTimeHistory record);

	List<SignRuleTimeHistory> selectListByParams(Map<String, Object> param);
}