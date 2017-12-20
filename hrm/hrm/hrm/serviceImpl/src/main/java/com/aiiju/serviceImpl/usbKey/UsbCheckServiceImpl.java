package com.aiiju.serviceImpl.usbKey;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.usbKey.UsbKey;
import com.aiiju.bean.oa.usbKey.UserUsbKey;
import com.aiiju.dao.oa.usbKey.IUsbKeyDao;
import com.aiiju.dao.oa.usbKey.IUserUsbKeyDao;
import com.aiiju.service.usbKey.IUsbCheckService;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.ExceptionUtil;
import com.alibaba.fastjson.JSONObject;
@Service("usbCheckService")
public class UsbCheckServiceImpl implements IUsbCheckService {

	@Autowired
	 private IUserUsbKeyDao userUsbKeyDao;
	@Autowired
	private IUsbKeyDao usbKeyDao;
	
	
	
	private static Logger logger = LoggerFactory.getLogger(UsbCheckServiceImpl.class);
	
	@Override
	public void userCheckActivation(Map<String, Object> params) throws Exception {
		
		UserUsbKey userUsbKey = null;
		
		// lockId : USB软件内部标识的ID
		String lockId = params.get("lockId")==null?null:params.get("lockId").toString();
		
		//USB验证必须传递 lockId 
		if(StringUtils.isEmpty(lockId)){
			throw new BizException("3", "请插入U盾！ ");
		}
		
		try {
			//判断关联表中该账号是否有激活USB
			userUsbKey = userUsbKeyDao.selectUserUsbKey(params);
		
			if(userUsbKey==null){
				//判断关联表中账号公司是否有激活USB(主要给带权限的用户判断，因管理员已激活USB ,一个公司可公用USB)
				Map<String,Object> m = new HashMap();
				m.put("companyId", params.get("companyId"));
				if(userUsbKeyDao.selectUserUsbKey(m)==null){
						throw new BizException("1","未激活UsbKey");
				}else {
					//公司已激活USB，但是权限的用户还没激活二级密码，数据库没有数据
					throw new BizException("2"," 未激活二级密码");
				}
			} else {
				//权限的用户只激活二级密码 ，验证管理员是否走完二级密码激活
				if((StringUtils.isEmpty(userUsbKey.getSecondPassword())||userUsbKey.getSecondPassword().length()!=6)){
					throw new BizException("2","账号未激活二级密码");
				}
				//插入的USBKEY与激活的账号的USBkey不匹配
				else if(!lockId.equalsIgnoreCase(userUsbKey.getUsbKey().getLockId())){
					throw new BizException("3","请插入正确的UsbKey，序列号尾号后4位为 "+userUsbKey.getUsbKey().
													getSerialNumber().substring(userUsbKey.getUsbKey().getSerialNumber().length()-4));
				}
			}
		} catch (Exception e) {
			ExceptionUtil.throwExceptionUtil(e);
		}
	}
	
	@Override
	public void userActivationUSBKey(Map<String, Object> params) throws Exception {
		
		UsbKey usbKey = null;
		
		// serialNumber : USB序列号
		String serialNumber = params.get("serialNumber")==null?null:params.get("serialNumber").toString();
		// activationCode : USB激活码
		String activationCode = params.get("activationCode")==null?null:params.get("activationCode").toString();
		// lockId : USB软件内部标识的ID
		String lockId = params.get("lockId")==null?null:params.get("lockId").toString();
		
		
		if(StringUtils.isEmpty(serialNumber)||StringUtils.isEmpty(activationCode)){
			throw new BizException("3","请输入序列号和激活码");
		}else if (StringUtils.isEmpty(lockId)) {
				throw new BizException("3", "请插入U盾！ ");
		}
		
		try{
			//该公司已激活USB（1个公司对应一个USB）
			Map<String,Object> m = new HashMap();
			m.put("companyId", params.get("companyId"));
			if(userUsbKeyDao.selectUserUsbKey(m)!=null){
				throw new BizException("3","已激活UsbKey");
			}
			usbKey = usbKeyDao.selectUsbKey(params);
			if(usbKey==null){
				throw new BizException("3","请输入正确的序列号和激活码");	
			}
			
			
			//通过usbKeyId去user_usbkey表中查找是否被使用
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usbKeyId",usbKey.getId());
			if(userUsbKeyDao.selectUserUsbKey(map)!=null){
				throw new BizException("3","该UsbKey已被使用");	
			}
			//关联表添加数据
			params.put("usbKeyId",usbKey.getId());
			String jsonString = JSONObject.toJSONString(params);
			UserUsbKey parseObject = JSONObject.parseObject(jsonString, UserUsbKey.class);
			
			userUsbKeyDao.addUserUsbKey(parseObject);
		
		}catch (Exception e) {
			ExceptionUtil.throwExceptionUtil(e);
		}

	}
	@Override
	public void userActivationSecondPassword(Map<String, Object> params) throws Exception {
		
		UserUsbKey userUsbKey = null;
		
		String secondPassword = params.get("secondPassword")==null?null:params.get("secondPassword").toString();
				
		
		if(StringUtils.isEmpty(secondPassword)){
			throw new BizException("3", "无效的二级密码");
		}
		Pattern pattern = Pattern.compile("^[0-9]{6}$");
        Matcher isNum = pattern.matcher(secondPassword);
        if(!isNum.matches()){
        	throw new BizException("3", "无效的二级密码");
        }

		try{
			userUsbKey=userUsbKeyDao.selectUserUsbKey(params);
			//带权限的普通用户，需要新增数据，并把usbKeyId设置为公司的USBID；管理员只修改数据
			if(userUsbKey!=null){
				//如果密码不为null且长度=6说明二级密码已激活
				if(userUsbKey.getSecondPassword()!=null&&userUsbKey.getSecondPassword().length()==6){
					throw new BizException("3", "该账号已激活二级密码");
				}
				
				String jsonString = JSONObject.toJSONString(params);
				UserUsbKey parseObject = JSONObject.parseObject(jsonString, UserUsbKey.class);
				userUsbKeyDao.updateUserUsbKey(parseObject);
			}else { 
				Map<String,Object> m = new HashMap();
				m.put("companyId", params.get("companyId"));
				
				UserUsbKey companyIduserUsbKey =	userUsbKeyDao.selectUserUsbKey(m);
				//说明公司没激活USB
				if(companyIduserUsbKey==null){
					throw new BizException("1", "请先激活UsbKey");
				}else{
				//带权限的管理激活二级密码,并把usbKeyId设置为公司的USBID
					params.put("usbKeyId", companyIduserUsbKey.getUsbKeyId());
					String jsonString = JSONObject.toJSONString(params);
					UserUsbKey parseObject = JSONObject.parseObject(jsonString, UserUsbKey.class);
					userUsbKeyDao.addUserUsbKey(parseObject);
				}
			}
		
		
		}catch (Exception e) {
			ExceptionUtil.throwExceptionUtil(e);
		}
	}
	
	@Override
	public void userCheckSecondPassword(Map<String, Object> params) throws Exception {
		
		UserUsbKey userUsbKey = null;
		
		String secondPassword = params.get("secondPassword")==null?null:params.get("secondPassword").toString();
				
		if(StringUtils.isEmpty(secondPassword)){
			throw new BizException("3", "无效的二级密码");
		}
		Pattern pattern = Pattern.compile("^[0-9]{6}$");
        Matcher isNum = pattern.matcher(secondPassword);
        if(!isNum.matches()){
        	throw new BizException("3", "无效的二级密码");
        }
        
        try{
        	userUsbKey=userUsbKeyDao.selectUserUsbKey(params);
        	
        	//管理员
			if(userUsbKey!=null){
				 if(userUsbKey.getSecondPassword()==null||userUsbKey.getSecondPassword().length()!=6){
					throw new BizException("2", "账号未激活二级密码");
				}else if(!userUsbKey.getSecondPassword().equals(secondPassword)){
					throw new BizException("3", "请输入正确的二级密码");
				}
			}else{
				Map<String,Object> m = new HashMap();
				m.put("companyId", params.get("companyId"));
				UserUsbKey companyIduserUsbKey =	userUsbKeyDao.selectUserUsbKey(m);
				
				 if(companyIduserUsbKey ==null){
						throw new BizException("1", "请先激活UsbKey");
					}else {
						throw new BizException("2", "账号未激活二级密码");
					}
			}
			
		}catch (Exception e) {
			ExceptionUtil.throwExceptionUtil(e);
		}
		
	}
	
	

}
