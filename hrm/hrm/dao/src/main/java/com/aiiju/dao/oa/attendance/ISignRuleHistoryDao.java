package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRuleHistory;

public interface ISignRuleHistoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRuleHistory record);

    int insertSelective(SignRuleHistory record);

    SignRuleHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRuleHistory record);

    int updateByPrimaryKey(SignRuleHistory record);

	List<SignRuleHistory> selectListByMap(Map<String, Object> param);
}