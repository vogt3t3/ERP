package com.aiiju.dao.oa.attendance;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.attendance.SignRecord;
import com.aiiju.bean.oa.attendance.SignRecordKey;

public interface ISignRecordDao {
    int deleteByPrimaryKey(SignRecordKey key);

    int insert(SignRecord record);

    int insertSelective(SignRecord record);

    SignRecord selectByPrimaryKey(SignRecordKey key);

    int updateByPrimaryKeySelective(SignRecord record);

    int updateByPrimaryKey(SignRecord record);

	List<SignRecord> selectRecordsByParams(Map<String, Object> map);
}