package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.aiiju.bean.common.BasicEnum;
import com.aiiju.bean.oa.staff.ProfessionSkillQualification;
import com.aiiju.dao.oa.staff.IProfessionSkillQualificationDao;
import com.aiiju.service.staff.IProfessionSkillQualificationService;
import com.alibaba.fastjson.JSONObject;
@Service("professionSkillQualificationService")
@Transactional
public class ProfessionSkillQualificationServiceImpl implements
		IProfessionSkillQualificationService {

	@Autowired
	private IProfessionSkillQualificationDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addProfessionSkillQualification(Map<String, Object> map) {
		String obj=map.get("professionSkillQualification").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		ProfessionSkillQualification qualification = JSONObject.parseObject(obj, ProfessionSkillQualification.class);
		qualification.setUserId(Long.parseLong(userId));
		qualification.setCompanyId(Long.parseLong(companyId));
		return dao.insert(qualification);
	}
	@Autowired
	private JedisPool jedisPool;
	@Override
	public Map<String, Object> getProfessionSkillQualifications(Map<String, Object> map) {
		Map<String, Object> professionSkillQualifications = new HashMap<String, Object>();
		List<ProfessionSkillQualification>  list=dao.selectByExample(map);
		Jedis redis=jedisPool.getResource();
		for (ProfessionSkillQualification prof : list) {
			if(prof.getQualificationName()!=null){
				String name=redis.hget("qualificationList",prof.getQualificationName());
				BasicEnum be= JSONObject.parseObject(name,BasicEnum.class); 
				if(be!=null){
					prof.setQualificationName(be.getName());
				}
			}
		}
		professionSkillQualifications.put("professionSkillQualifications",list);
		redis.close();
		return professionSkillQualifications;
	}

	@Override
	public ProfessionSkillQualification getProfessionSkillQualificationById(
			Map<String, Object> map) {
		Jedis redis=jedisPool.getResource();
		ProfessionSkillQualification qualification = dao.selectByUserId(map);
		String name=redis.hget("qualificationList",qualification.getQualificationName());
		BasicEnum be= JSONObject.parseObject(name,BasicEnum.class); 
		qualification.setQualificationName(be.getName());
		redis.close();
		return qualification;
	}

	@Override
	public int updateProfessionSkillQualification(Map<String, Object> map) {
		String obj=map.get("professionSkillQualification").toString();
		ProfessionSkillQualification qualification = JSONObject.parseObject(obj, ProfessionSkillQualification.class);
		return dao.updateByExample (qualification);
	}

}
