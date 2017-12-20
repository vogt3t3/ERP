package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.SocietyRelations;
import com.aiiju.dao.oa.staff.ISocietyRelationsDao;
import com.aiiju.service.staff.ISocietyRelationsService;
import com.alibaba.fastjson.JSONObject;
@Service("societyRelationsService")
@Transactional
public class SocietyRelationsServiceImpl implements ISocietyRelationsService {

	@Autowired
	private ISocietyRelationsDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addSocietyRelations(Map<String, Object> map) {
		String obj=map.get("societyRelations").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		SocietyRelations relations = JSONObject.parseObject(obj, SocietyRelations.class);
		relations.setUserId(Long.parseLong(userId));
		relations.setCompanyId(Long.parseLong(companyId));
		return dao.insert(relations);
	}

	@Override
	public Map<String, Object> getSocietyRelations(Map<String, Object> map) {
		Map<String, Object> societyRelations = new HashMap<String, Object>();
		societyRelations.put("societyRelationss", dao.selectByExample(map));
		return societyRelations;
	}

	@Override
	public SocietyRelations getSocietyRelationsById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateSocietyRelations(Map<String, Object> map) {
		String obj=map.get("societyRelations").toString();
		SocietyRelations relations = JSONObject.parseObject(obj, SocietyRelations.class);
		return dao.updateByExample(relations);
	}

}
