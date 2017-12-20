package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.LanguageAbilityLog;
import com.aiiju.dao.oa.staff.ILanguageAbilityLogDao;
import com.aiiju.service.staff.ILanguageAbilityLogService;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.alibaba.fastjson.JSONObject;
@Service("languageAbilityLogService")
@Transactional
public class LanguageAbilityLogServiceImpl implements
		ILanguageAbilityLogService {

	@Autowired
	private ILanguageAbilityLogDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addLanguageAbilityLog(Map<String, Object> map) {
		String obj=map.get("languageAbilityLog").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		LanguageAbilityLog languageAbilityLog = JSONObject.parseObject(obj, LanguageAbilityLog.class);
		languageAbilityLog.setUserId(Long.parseLong(userId));
		languageAbilityLog.setCompanyId(Long.parseLong(companyId));
		//查询是否重复
		Map<String, Object> userLanguage = new HashMap<String, Object>();
		userLanguage.put("userId", userId);
		userLanguage.put("companyId", companyId);
		List<LanguageAbilityLog> list =dao.selectByExample(userLanguage);
		for (LanguageAbilityLog language : list) {
			if(languageAbilityLog.getLanguage().trim().equals(language.getLanguage().trim())){
				throw new BizException(BizExceptionMessage.Language_IS_EXIST);
			}
		}
		
		
		return dao.insert(languageAbilityLog);
	}

	@Override
	public Map<String, Object> getLanguageAbilityLogs(Map<String, Object> map) {
		Map<String, Object> languageAbilityLogs = new HashMap<String, Object>();
		languageAbilityLogs.put("languageAbilityLogs", dao.selectByExample(map));
		return languageAbilityLogs;
	}

	@Override
	public LanguageAbilityLog getLanguageAbilityLogById(
			Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateLanguageAbilityLog(Map<String, Object> map) {
		String obj=map.get("languageAbilityLog").toString();
		LanguageAbilityLog languageAbilityLog = JSONObject.parseObject(obj, LanguageAbilityLog.class);
		return dao.updateByExample(languageAbilityLog);
	}

}
