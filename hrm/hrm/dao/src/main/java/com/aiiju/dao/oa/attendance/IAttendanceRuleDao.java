package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.AttendanceRule;

public interface IAttendanceRuleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AttendanceRule record);

    int insertSelective(AttendanceRule record);

    AttendanceRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AttendanceRule record);

    int updateByPrimaryKey(AttendanceRule record);

	List<AttendanceRule> selectAttendanceRuleList(Map<String, Object> params);
}