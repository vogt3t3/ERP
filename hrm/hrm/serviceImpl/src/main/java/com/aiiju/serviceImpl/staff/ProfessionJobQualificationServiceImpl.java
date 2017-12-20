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
import com.aiiju.bean.oa.staff.ProfessionJobQualification;
import com.aiiju.dao.oa.staff.IProfessionJobQualificationDao;
import com.aiiju.service.staff.IProfessionJobQualificationService;
import com.alibaba.fastjson.JSONObject;
@Service("professionJobQualificationService")
@Transactional
public class ProfessionJobQualificationServiceImpl implements
		IProfessionJobQualificationService {

	@Autowired
	private IProfessionJobQualificationDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addProfessionJobQualification(Map<String, Object> map) {
		String obj=map.get("professionJobQualification").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		ProfessionJobQualification jobQualification = JSONObject.parseObject(obj, ProfessionJobQualification.class);
		jobQualification.setUserId(Long.parseLong(userId));
		jobQualification.setCompanyId(Long.parseLong(companyId));
		return dao.insert(jobQualification);
	}

	@Autowired
	private JedisPool jedisPool;
	@Override
	public Map<String, Object> getProfessionJobQualifications(Map<String, Object> map) {
		Map<String, Object> professionJobQualifications = new HashMap<String, Object>();
		List<ProfessionJobQualification>  list =dao.selectByExample(map);
		Jedis redis=jedisPool.getResource();
		for (ProfessionJobQualification professionJobQualification : list) {
			if(professionJobQualification.getCertificateName()!=null){
			String name=redis.hget("qualificationList",professionJobQualification.getCertificateName());
			BasicEnum be= JSONObject.parseObject(name,BasicEnum.class); 
				if(be!=null){
					professionJobQualification.setCertificateName(be.getName());
				}
			}
		}
		
		professionJobQualifications.put("professionJobQualifications", list);
		redis.close();
		return professionJobQualifications;
	}

	@Override
	public ProfessionJobQualification getProfessionJobQualificationById(
			Map<String, Object> map) {
		Jedis redis=jedisPool.getResource();
		ProfessionJobQualification jobQualification=dao.selectByUserId(map);
		String name=redis.hget("qualificationList",jobQualification.getCertificateName());
		BasicEnum be= JSONObject.parseObject(name,BasicEnum.class); 
		jobQualification.setCertificateName(be.getName());
		redis.close();
		return jobQualification;
	}

	@Override
	public int updateProfessionJobQualification(Map<String, Object> map) {
		String obj=map.get("professionJobQualification").toString();
		ProfessionJobQualification jobQualification = JSONObject.parseObject(obj, ProfessionJobQualification.class);
		return dao.updateByExample(jobQualification);
	}

}
