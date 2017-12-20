package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.ReportRelation;
import com.aiiju.dao.oa.staff.IReportRelationDao;
import com.aiiju.service.staff.IReportRelationService;
import com.alibaba.fastjson.JSONObject;
@Service("reportRelationService")
@Transactional
public class ReportRelationServiceImpl implements IReportRelationService {

	@Autowired
	private IReportRelationDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addReportRelation(Map<String, Object> map) {
		String obj=map.get("reportRelation").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		ReportRelation relation = JSONObject.parseObject(obj, ReportRelation.class);
		relation.setFromUserId(Long.parseLong(userId));
		relation.setCompanyId(Long.parseLong(companyId));
		return dao.insert(relation);
	}

	@Override
	public Map<String, Object> getReportRelations(Map<String, Object> map) {
		Map<String, Object> reportRelations = new HashMap<String, Object>();
		reportRelations.put("reportRelations", dao.selectByExample(map));
		return reportRelations;
	}

	@Override
	public ReportRelation getReportRelationById(Map<String, Object> map) {
		return dao.selectByUserId(map);
	}

	@Override
	public int updateReportRelation(Map<String, Object> map) {
		String obj=map.get("reportRelation").toString();
		ReportRelation relation = JSONObject.parseObject(obj, ReportRelation.class);
		return dao.updateByExample(relation);
	}

}
