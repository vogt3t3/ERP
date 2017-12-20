package com.aiiju.util.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aiiju.util.exception.BizException;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * ajUC 通用抽取调用
 * @author 琦玉
 *
 * 2016年11月4日   下午4:47:17
 */
public class AjucGetCommonValue {

	
	/**
	 * 通过AJUC获得管理员ID
	 * @param params
	 * @param 
	 */
	
	
	public static String ajucGetManagerId(Map<String,Object> params){
		List<Object> paramArray = new ArrayList<Object>();
		String managerId;
		paramArray.add(Long.valueOf(params.get("companyId").toString()));//公司id
		
		//因为所有工作汇报都需要给管理员，先用AJUC找管理员ID，然后排重加到后发送人的属性就可以了
		try {
			String userMenus = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","getAdminUser",paramArray);
			JSONObject json = JSONObject.parseObject(userMenus);
			String code = json.getString("code");
			if(!"0".equals(code)){//表示失败
				throw new BizException("500", "获取用户菜单远程调用接口失败,请稍后再试");
			}
			String data = json.get("data").toString();
			//得到管理员ID（大系统数据结构）
			JSONObject managerJson = JSONObject.parseObject(data);
			
			 managerId = managerJson.get("id").toString();
		} catch (Exception e) {
			throw new BizException("500", "获取用户菜单远程调用接口失败,请稍后再试");
		}
		
		return managerId;
	}

}
