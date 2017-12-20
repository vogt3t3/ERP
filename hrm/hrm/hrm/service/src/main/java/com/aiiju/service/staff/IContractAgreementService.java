package com.aiiju.service.staff;

import java.util.Map;

import com.aiiju.bean.oa.staff.ContractAgreement;
/**
 * 合同Service
 * 
 * @author 维斯
 *	2016年10月20日	 下午6:21:36
 */
public interface IContractAgreementService {
	/**
	 * 删除
	 * @author 维斯
	 * @param map
	 * @return
	 * 2016年10月20日  下午6:27:10
	 */
    int deleteById(Map<String,Object> map);
    /**
     * 添加
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:07
     */
    int addContractAgreement(Map<String,Object> map);
    /**
     * 	查询结果集 按条件 分页
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:05
     */
    Map<String, Object> getContractAgreements(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:27:02
     */
    ContractAgreement getContractAgreementById(Map<String,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param map
     * @return
     * 2016年10月20日  下午6:26:59
     */
    int updateContractAgreement(Map<String,Object> map);
}
