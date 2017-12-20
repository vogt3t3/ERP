package com.aiiju.serviceImpl.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.ContractAgreement;
import com.aiiju.dao.oa.staff.IContractAgreementDao;
import com.aiiju.service.staff.IContractAgreementService;
import com.aiiju.util.enums.Contract;
import com.alibaba.fastjson.JSONObject;
@Service("contractAgreementService")
@Transactional
public class ContractAgreementServiceImpl implements IContractAgreementService {

	@Autowired
	private IContractAgreementDao dao;
	@Override
	public int deleteById(Map<String, Object> map) {
		String id = map.get("id").toString();
		return dao.deleteById(Long.parseLong(id));
	}

	@Override
	public int addContractAgreement(Map<String, Object> map) {
		String obj=map.get("contractAgreement").toString();
		String userId = map.get("empId").toString();
		String companyId=map.get("companyId").toString();
		ContractAgreement agreement = JSONObject.parseObject(obj, ContractAgreement.class);
		agreement.setUserId(Long.parseLong(userId));
		agreement.setCompanyId(Long.parseLong(companyId));
		return dao.insert(agreement);
	}

	@Override
	public Map<String, Object> getContractAgreements(Map<String, Object> map) {
		Map<String, Object> contractAgreements = new HashMap<String, Object>();
		List<ContractAgreement> list=dao.selectByExample(map);
		for (ContractAgreement agreement : list) {
			if(agreement.getContractType()!=null){
				agreement.setContractTypeName(Contract.getName(agreement.getContractType().toString()));
			}
			if(agreement.getLimitType()!=null){
				agreement.setLimitTypeName(agreement.getLimitType().toString());
			}
			if(agreement.getSignType()!=null){
				agreement.setSignTypeName(agreement.getSignType().toString());
			}
		}
		contractAgreements.put("contractAgreements",list );
		return contractAgreements;
	}

	@Override
	public ContractAgreement getContractAgreementById(
			Map<String, Object> map) {
		ContractAgreement agreement=dao.selectByUserId(map);
		agreement.setContractTypeName(Contract.getName(agreement.getContractType().toString()));
		agreement.setLimitTypeName(agreement.getLimitType().toString());
		agreement.setSignTypeName(agreement.getSignType().toString());
		return agreement;
	}

	@Override
	public int updateContractAgreement(Map<String, Object> map) {
		String obj=map.get("contractAgreement").toString();
		ContractAgreement agreement = JSONObject.parseObject(obj, ContractAgreement.class);
		return dao.updateByExample(agreement);
	}

}
