package com.aiiju.serviceImpl.staff;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.service.staff.IBuyService;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.Tools;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Service("buyService")

public class BuyServiceImpl  implements IBuyService {

	private static Logger logger = LoggerFactory.getLogger(BuyServiceImpl.class);

	
	@Autowired
	private IDepartmentDao deptDao;
	@Override
	public String addDeliveryAddress(Map<String, Object> map) {
		//地区
		String region =map.get("region")==null?null:map.get("region").toString();
		//地址
		String address =map.get("address")==null?null:map.get("address").toString();
		//收货人
		String contact =map.get("contact")==null?null:map.get("contact").toString();
		//收货人手机号
		String contactPhone =map.get("contactPhone")==null?null:map.get("contactPhone").toString();
		String companyId =map.get("companyId")==null?null:map.get("companyId").toString();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException("1","缺少公司ID！");
		}
		if(StringUtils.isEmpty(region)||StringUtils.isEmpty(address)||
						StringUtils.isEmpty(contact)||StringUtils.isEmpty(contactPhone)){
			throw new BizException("1","请填写必填项！");
		}
		String regex = null;
		boolean isMatch = false;
		regex="^[1]{1}[0-9]{10}$";
		 isMatch = Tools.regularCheck(regex, contactPhone);
		if(!isMatch){
			throw new BizException("1","请填写正确的手机号");
		}
		regex="^.{2,20}$";
		 isMatch = Tools.regularCheck(regex, contact);
		if(!isMatch){
			throw new BizException("1","请填有效长的收货人名字");
		} 
		regex="^.{5,120}$";
		 isMatch = Tools.regularCheck(regex, address);
		if(!isMatch){
			throw new BizException("1","请填请填有效长的地址");
		} 
		List<Object> paramArray = new ArrayList<Object>();
		Map<String,Object> m = new HashMap<>();
		paramArray.add(companyId);
		paramArray.add(1);//表示PHP保存HRM的收货信息
		m.put("region",region);
		m.put("address",address);
		m.put("contact_phone",contactPhone);
		m.put("contact",contact);
		paramArray.add(m);
		String fromAjuc =null;
		try {
			 fromAjuc = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcAddress", "createAddress", paramArray);
		} catch (Exception e) {
			throw new BizException("1", "新增信息失败！");
		}
		JSONObject json = JSONObject.parseObject(fromAjuc);
		if(!"0".equals(json.getString("code"))){
			throw new BizException("1", "新增信息失败！");
		}
		
		return null;
	}

	@Override
	public Map<String,Object> myOrder(Map<String, Object> map) {
		//该功能的服务地点PHP没有,后端写死
		Map<String,Object> resultMap = new HashMap<>();
		String companyId =map.get("companyId")==null?null:map.get("companyId").toString();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException("1","缺少公司ID！");
		}
		List<Object> paramArray = new ArrayList<Object>();
		paramArray.add(companyId);
		paramArray.add(1);
		paramArray.add(500);
		
		String fromAjuc = null;
		try {
			
			 fromAjuc = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcHrmOrder", "getCompanyHrmOrders", paramArray);
			
		} catch (Exception e) {
			throw new BizException("1", "获取我的订单失败！");
		}
		JSONObject json = JSONObject.parseObject(fromAjuc);
		if(!"0".equals(json.getString("code"))){
			throw new BizException("1", "获取我的订单失败！");
		}
		String  string = json.getString("data");
		JSONArray parseArray = JSONObject.parseObject(string).getJSONArray("list");
		//加入服务点
		resultMap.put("servicePoint", "杭州爱聚科技有限公司");
		resultMap.put("orders", parseArray);
		return resultMap;
	}

	@Override
	public Map<String,Object> userInfo(Map<String, Object> map) {
		String companyId =map.get("companyId")==null?null:map.get("companyId").toString();
		Map<String,Object> resultMap = new HashMap<>();
		if(StringUtils.isEmpty(companyId)){
			throw new BizException("1","缺少公司ID！");
		}
		
		String userAjuc =null;
		String adressAjuc =null;
		List<Object> userArray = new ArrayList<Object>();
		userArray.add(companyId);
		try {
			userAjuc =HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcHrmVip", "getVipInfo", userArray);
			JSONObject userjson = JSONObject.parseObject(userAjuc);
			if(!"0".equals(userjson.getString("code"))){
				throw new BizException("1", "获取用户信息失败！");
			}
			if(userjson.getString("data")!=null){
				 JSONObject jsonObject = userjson.getJSONObject("data");
				 String endDate = jsonObject.getString("end_date");
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				 Date adf=sdf.parse(endDate);
				 long currentTimeMillis = System.currentTimeMillis();
				 if(currentTimeMillis>adf.getTime()){
					 resultMap.put("expirationDate","");
					 resultMap.put("userType","");
				 }else{
					 resultMap.put("expirationDate",endDate);
					 resultMap.put("userType","HR付费版");
				 }
			}else {
				resultMap.put("userType","");
				resultMap.put("expirationDate","");
			}
			 List<Object> paramArray = new ArrayList<Object>();
			 paramArray.add(companyId);
			 paramArray.add(1);//当前页
			 paramArray.add(500);//页大小
			//调用AJUC获得收货地址
			 adressAjuc = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcAddress", "getCompanyAddresses", paramArray);
			 JSONObject adressjson = JSONObject.parseObject(adressAjuc);
			 if(!"0".equals(adressjson.getString("code"))){
					throw new BizException("1", "获取用户地址失败！");
				}
			 resultMap.put("userAdress","");
			 if(adressjson.getString("data")!=null){
				 JSONArray jsonArray = adressjson.getJSONArray("data");
				 if(jsonArray!=null&&jsonArray.size()>0){
					 JSONObject parseObject = JSONObject.parseObject(jsonArray.get(jsonArray.size()-1).toString());
					 String adress =parseObject.getString("region")+parseObject.getString("address");
					 resultMap.put("userAdress",adress);
				 }
			 }
		} catch (Exception e1) {
			throw new BizException("1", "获取用户信息失败！");
		}
		Department dept = deptDao.selectDepartmentByCompanyId(map);
		resultMap.put("companyName", dept.getName());
		
		return resultMap;
	}

	

	
}