package com.aiiju.dao.oa.staff;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.staff.ContractAgreement;
/**
 * 合同dao
 * 
 * @author 维斯
 *	2016年10月19日	 上午10:42:11
 */
public interface IContractAgreementDao {
	/**
	 * 通过id删除
	 * @author 维斯
	 * @param id
	 * @return
	 * 2016年10月19日  上午10:43:00
	 */
    int deleteById(Long id);
    /**
     * 通过员工id删除
     * @author 维斯
     *
     * 2016年11月18日   下午1:56:15
     */
    int deleteByUserId(String[] ids);
    /**
     * 添加
     * @author 维斯
     * @param record
     * @return
     * 2016年10月19日  上午10:43:18
     */
    int insert(ContractAgreement record);
    /**
     * 查询结果集
     * @author 维斯
     * @param example
     * @return
     * 2016年10月19日  上午10:43:14
     */
    List<ContractAgreement> selectByExample(Map<String ,Object> map);
    /**
     * 通过id查询
     * @author 维斯
     * @param id
     * @return
     * 2016年10月19日  上午10:43:11
     */
    ContractAgreement selectByUserId(Map<String ,Object> map);
    /**
     * 更新
     * @author 维斯
     * @param record
     * @return
     * 2016年10月19日  上午10:43:08
     */
    int updateByExample(ContractAgreement record);

}