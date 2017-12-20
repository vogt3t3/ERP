package com.aiiju.dao.oa.salary;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.salary.SpwSalaryPay;

public interface ISpwSalaryPayDao {

	/**
	 * 通过pid删除
	 * @author 
	 * @param id
	 * @return
	 * 2016年10月18日  上午10:09:10
	 */
    int deleteById(Long pid);
    /**
     * 批量添加
     * @author 
     * @param list
     * @return
     * 2016年10月18日  上午10:09:07
     */
    int inserts(List<SpwSalaryPay> list);
    /**
     * 按条件查询
     * @author 
     * @param example
     * @return
     * 2016年10月18日  上午10:09:04
     */
    List<SpwSalaryPay> selectByExample(Map<String,Object> map);
    /**
     * 通过id查询
     * @author 
     * @param id
     * @return
     * 2016年10月18日  上午10:09:01
     */
    SpwSalaryPay selectById(Map<String ,Object> map);
    /**
     * 按参数更新
     * @author 
     * @param record
     * @return
     * 2016年10月18日  上午10:08:56
     */
    int updateByExample(SpwSalaryPay record);

    /**
     * 根据条件（pid）获取薪酬列表
     * @param params
     * @return
     */
	List<SpwSalaryPay> selectSalaryInfoByPid(Map<String, Object> params);
	
	/**
	 * 根据pid批量更新状态（is_send,is_mail_send,is_del）字段
	 * @param params
	 */
	void updateStatusByPid(Map<String, Object> params);
	
	/**
	 * 根据Id查询用户工资条数据
	 * @param params
	 * @return
	 */
	SpwSalaryPay selectSalaryPayInfoDynamics(Map<String, Object> params);
	
	/**
	 * 获取薪资列表数据量
	 * @param params
	 * @return
	 */
	public int getSalaryInfoCountByParams(Map<String, Object> params);
	
	/**
	 * 获取我的薪资数据
	 * @param params
	 * @return
	 */
	public SpwSalaryPay getMySalaryPay(Map<String, Object> params);
	
	/**
	 * 是否存在二级密码  -1:不存在
	 * @param params
	 * @return
	 */
	public String getSecondPwd(Map<String, Object> params);
	
	/**
	 * 根据PID更新薪酬明细表的撤回状态
	 * @param params
	 */
	public void updatesalaryPayByPid(Map<String,Object> params);
}
