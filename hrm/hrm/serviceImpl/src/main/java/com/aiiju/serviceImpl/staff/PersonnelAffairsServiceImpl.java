package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.PersonnelAffairs;
import com.aiiju.dao.oa.staff.IPersonnelAffairsDao;
import com.aiiju.service.staff.IPersonnelAffairsService;
import com.alibaba.fastjson.JSONObject;
@Service("personnelAffairsService")
@Transactional
public class PersonnelAffairsServiceImpl implements IPersonnelAffairsService {

	@Autowired
	private IPersonnelAffairsDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addPersonnelAffairs(Map<String, Object> map) {
		String obj=map.get("personnelAffairs").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		PersonnelAffairs affairs = JSONObject.parseObject(obj, PersonnelAffairs.class);
		affairs.setUserId(Long.parseLong(userId));
		affairs.setCompanyId(Long.parseLong(companyId));
		return dao.insert(affairs);
	}

	@Override
	public Map<String, Object> getPersonnelAffairss(Map<String, Object> map) {
		Map<String, Object> personnelAffairss = new HashMap<String, Object>();
		List<PersonnelAffairs> list= dao.selectByExample(map);
		for (PersonnelAffairs personnelAffairs : list) {
			if(personnelAffairs.getType()!=null){
				personnelAffairs.setTypeName(personnelAffairs.getType().toString());
			}
			if(personnelAffairs.getStatus()!=null){
				personnelAffairs.setStatusName(personnelAffairs.getStatus().toString());
			}
		}
		
		personnelAffairss.put("personnelAffairss", list);
		return personnelAffairss;
	}

	@Override
	public PersonnelAffairs getPersonnelAffairsById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updatePersonnelAffairs(Map<String, Object> map) {
		String obj=map.get("personnelAffairs").toString();
		PersonnelAffairs affairs = JSONObject.parseObject(obj, PersonnelAffairs.class);
		affairs.setTypeName(affairs.getType().toString());
		affairs.setStatusName(affairs.getStatus().toString());
		return dao.updateByExample(affairs);
	}

}
