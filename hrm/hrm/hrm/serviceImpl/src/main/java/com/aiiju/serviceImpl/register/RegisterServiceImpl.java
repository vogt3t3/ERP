package com.aiiju.serviceImpl.register;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.register.RegisterUser;
import com.aiiju.bean.oa.staff.InviteUser;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.register.ICompanyDao;
import com.aiiju.dao.oa.register.IRegisterUserDao;
import com.aiiju.dao.oa.staff.IInviteUserDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.register.IRegisterService;
import com.aiiju.util.common.MD5;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.RandomCodeUtil;
import com.aiiju.util.common.Result;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.http.APIRequestUtil;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.aiiju.util.redis.JedisUtil;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

@Service("registerService")
public class RegisterServiceImpl implements IRegisterService {
	/**
	 * 日志log打印
	 */
	private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	@Autowired
    private ICompanyDao companyDao;
	
	@Autowired
    private IRegisterUserDao registerUserDao;
	
	@Autowired
    private IUserDao userDao;
	@Autowired
	private IInviteUserDao inviteUserDao;
	
	@Autowired
    private IDepartmentDao deptDao;
	
	@Autowired
	private IUserDao dao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> register(Map<String, Object> params){
		    Map<String, Object> result=new HashMap<String, Object>();
			RegisterUser registerUser=registerUserDao.getRegisterUserByParams(params);
			User user=new User();
			if(registerUser!=null){
				throw new BizException(BizExceptionMessage.FAILURE,"手机号码已注册");
			}else{
				try {
				//注册公司 公司名 手机号  companyName phone,并且添加以公司名为顶级节点的部门名
				Map<String,Object> resultData=addCompany(params);
				Long companyId=Long.parseLong(resultData.get("companyId").toString());
				Long deptId=Long.parseLong(resultData.get("deptId").toString());
				//注册用户 注册用户名 手机号 密码  registerName phone password
				params.put("password", MD5.md5(params.get("password").toString()+"dsb"));
				
				Long loginUserId=registerUserDao.addRegisterUser(params);
				loginUserId=Long.parseLong(params.get("id").toString());
				//注册员工
				//由于oa_user表现在改成了主键不自增,因此主键id用大系统用户的赋值与其主键保持一致
				user.setId(loginUserId);
				
				user.setLoginUserId(loginUserId);
				user.setCompanyId(companyId);
				user.setDeptId(deptId);
				user.setName(params.get("userName").toString());
				user.setNick(params.get("userName").toString());
				user.setPhone(params.get("phone").toString());
				user.setIsAdmin((byte)1);
				userDao.insert(user);
				result.put("user", user);
				} catch (Exception e) {
					throw new BizException(BizExceptionMessage.FAILURE,"注册失败");
				}
			}
         
	return result;
	}

	@Override
	public Map<String, Object> login(Map<String, Object> params) {
		//根据手机号码获取登录用户
		RegisterUser registerUser=registerUserDao.getRegisterUserByParams(params);
		User user=null;
		if(registerUser==null){
			throw new BizException(BizExceptionMessage.PHONE_IS_NONE,"手机号未注册");
		}else{
			if(params.get("password")!=null&&!MD5.md5(params.get("password").toString()+"dsb").equals(registerUser.getPassword())){
				throw new BizException(BizExceptionMessage.PHONE_IS_NONE,"密码错误");
			}else{
				params.clear();
				params.put("loginUserId", registerUser.getId());
				List<User> list=userDao.selectByExample(params);
				
				if (list!=null&&!list.isEmpty()) {
					user=list.get(0);
					params.clear();
					params.put("empId", user.getId());
					params.put("inviteStatus", 0);
					List<InviteUser> inviteUserList=inviteUserDao.selectByInviteUsers(params);
					params.clear();
					params.put("user", user);
					params.put("inviteUserList", inviteUserList);
				}
			};
		}
		
		return params;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> addCompany(Map<String, Object> params){
		Map<String,Object> condition=new HashMap<String, Object>();
		//参数companyName  phone
		Long companyId=companyDao.addCompany(params);
		companyId=Long.parseLong(params.get("id").toString());
		String name=params.get("companyName").toString();
		//添加以公司名为顶级节点的部门
		condition.put("name",name);
		condition.put("shortName",name);
		condition.put("parentDeptId",0);
		condition.put("companyId",companyId);
		int deptId=deptDao.addDepartment(condition);
		deptId=Integer.parseInt(condition.get("id").toString());
		condition.clear();
		condition.put("companyId",companyId);
		condition.put("deptId", deptId);
		
		if(params.get("userId")!=null){
			User u=new User();
			u.setId(Long.parseLong(params.get("userId").toString()));
			u.setRegisterCompanyId(companyId);
			u.setDeptId((long)deptId);
			u.setStatus(1+"");
			userDao.updateByExample(u);
		}
		return condition;
	}

	@Override
	public Map<String, Object> getInitUserPwd(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String phone = (String) params.get("phone");
		// 获取验证码
		JedisUtil jedisUtil = JedisUtil.getInstance();
		String checkCodeFromRedis =jedisUtil.get(phone);
		if (null != checkCodeFromRedis) {
			if (!StringUtils.equals(checkCodeFromRedis, (String) params.get("checkCode"))) { // 验证失败
				throw new BizException(BizExceptionMessage.CHECKCODE_ERROR);
			} else { // 验证码正确
				// 修改用户密码
				String newPwd=MD5.md5(params.get("newPwd").toString()+"dsb");
				params.put("newPwd",newPwd );
				registerUserDao.updateRegisterUser(params);
				// 删除验证码信息
				jedisUtil.del(phone);
			}
		} else {
			throw new BizException(BizExceptionMessage.VCODE_IS_DISABLE);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateUserPwd(Map<String, Object> params) {
		/*Object obj = params.get("from");//请求来源PC,APP(非必填,但app端调用时必传)
		if(obj!=null&&"APP".equals(obj.toString())){
			//用参数userId去user表查询改id对应的手机号
			Object object = params.get("loginUserId");
			String loginUserId = object==null?null:object.toString();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("loginUserId", loginUserId);
			List<User> list = userDao.selectByExample(map);
			User loginUser = null;
			if(list!=null&&list.size()>0){
				 loginUser = list.get(0);
				 if(loginUser!=null&&loginUser.getLoginUserId().toString().equals(loginUserId)){
					//判断用户原始密码是否输入正确
					//oldPwd不能为空
					String oldPwd=MD5.md5(params.get("oldPwd").toString()+"dsb");
					RegisterUser ru=registerUserDao.getRegisterUserByParams(params);//只需要参数phone
					if(!oldPwd.equals(ru.getPassword())){
						 throw new BizException(BizExceptionMessage.FAILURE,"用户初始密码不正确");
					}
					String newPwd=MD5.md5(params.get("newPwd").toString()+"dsb");
					params.put("newPwd",newPwd );
					registerUserDao.updateRegisterUser(params);
				 }
			}
			
		}else{//以下代码为修改接口之前的所有逻辑
			//判断用户原始密码是否输入正确
			//oldPwd不能为空
			String oldPwd=MD5.md5(params.get("oldPwd").toString()+"dsb");
			RegisterUser ru=registerUserDao.getRegisterUserByParams(params);//只需要参数phone
			if(!oldPwd.equals(ru.getPassword())){
				 throw new BizException(BizExceptionMessage.FAILURE,"用户初始密码不正确");
			}
			String newPwd=MD5.md5(params.get("newPwd").toString()+"dsb");
			params.put("newPwd",newPwd );
			registerUserDao.updateRegisterUser(params);
		}*/
		return null;
	}

	@Override
	public Map<String, Object> createLoginSessionId(Map<String, Object> params) {
		//创建唯一的sessionId
		String new_sign ="param" + params.get("params") + "hrm"+System.currentTimeMillis();
		String sessionId = MD5.md5(new_sign);
		Map<String, Object> session=new HashMap<String, Object>();
		session.put("sessionId", sessionId);
		session.put("status", 0);
		//存储到redis,两分钟之内有效
		JedisUtil.getInstance().set(sessionId, JSONObject.toJSONString(session),120);
		params.clear();
		params.put("session",session);
		//返回给前端url
		return params;
	}

	@Override
	public Map<String, Object> agreeLoginBySessionId(Map<String, Object> params) {
		//sessionId,userId,companyId
		String sessionStr=JedisUtil.getInstance().get(params.get("sessionId").toString());
		
		if(!StringUtils.isEmpty(sessionStr)){
			Map<String,Object> session=JSONObject.parseObject(sessionStr, Map.class);
			session.put("status", 1);
			List<User> list=userDao.selectByExample(params);
			if(list!=null&&!list.isEmpty()){
				session.put("user", list.get(0));
			}
			JedisUtil.getInstance().set(session.get("sessionId").toString(), JSONObject.toJSONString(session),120);
			return session;
		}else{
			throw new BizException(BizExceptionMessage.SYS_ERROR,"二维码过期，请重新扫码");
		}
	}

	@Override
	public Map<String, Object> refuseLoginBySessionId(Map<String, Object> params) {
		//sessionId
			String sessionStr=JedisUtil.getInstance().get(params.get("sessionId").toString());
			
			if(!StringUtils.isEmpty(sessionStr)){
				Map<String,Object> session=JSONObject.parseObject(sessionStr, Map.class);
				session.put("status", 2);
				JedisUtil.getInstance().set(session.get("sessionId").toString(), JSONObject.toJSONString(session),120);
				return session;
			}else{
				throw new BizException(BizExceptionMessage.SYS_ERROR,"二维码过期，请重新扫码");
			}
	}

	@Override
	public Map<String, Object> flushSession(Map<String, Object> params) {
		String redirect=PropertiesUtil.getPropertyByKey("session.redirect.url");
		//sessionId
		String sessionStr=JedisUtil.getInstance().get(params.get("sessionId").toString());
		if(!StringUtils.isEmpty(sessionStr)){
			Map<String,Object> session=JSONObject.parseObject(sessionStr, Map.class);
			if(session.get("status").toString().equals("1")){
				session.put("redirect", redirect);
				User user=(User)JSONObject.parseObject(session.get("user").toString(),User.class);
				params.clear();
				params.put("empId", user.getId());
				params.put("inviteStatus", 0);
				List<InviteUser> inviteUserList=inviteUserDao.selectByInviteUsers(params);
				session.put("inviteUserList", inviteUserList);
			}
			return session;
		}else{
			throw new BizException(BizExceptionMessage.SYS_ERROR,"二维码过期，请重新扫码");
		}
		
	}

	@Override
	public Map<String, Object> sendCheckCode(Map<String, Object> params) {
		//flag 0注册  1忘记密码 2修改绑定的手机号
		String flag=params.get("flag").toString();
		//查看手机号码是否已注册；
		Map<String,Object> m = new HashMap<>();
		m.put("phone", params.get("phone"));
		List<User> userList = dao.selectNewByExample(params);
		if(flag.equals("1")){//忘记密码
			if(userList==null&&userList.size()==0){
				throw new BizException(BizExceptionMessage.PHONE_IS_NONE,"手机号码未注册");
			}else{
				sendSMS(params);
			}
		}else if(flag.equals("0")){//注册
			if(userList!=null&&userList.size()>0){
				throw new BizException(BizExceptionMessage.PHONE_IS_REGISTER,"手机号码已注册");
			}else{
				sendSMS(params);
			}
		}else if(flag.equals("2")){//修改绑定的手机号
			if(userList!=null&&userList.size()>0){
				throw new BizException(BizExceptionMessage.PHONE_IS_REPEATED,"该手机号已注册");
			}else{
				sendSMS(params);
			}
		}
	
		//查看
		return null;
	}

	@Override
	public Map<String, Object> validateCheckCode(Map<String, Object> params) {
		// redis中取出验证码
			String checkCodeFromRedis = JedisUtil.getInstance().get(params.get("phone").toString());
			
			if (StringUtils.isNotEmpty(checkCodeFromRedis)) {
				if (!StringUtils.equals(checkCodeFromRedis, (String) params.get("checkCode"))) { // 验证码错误
					throw new BizException(BizExceptionMessage.CHECKCODE_ERROR);
				}
			} else {
				// 无效的checkCode
				throw new BizException(BizExceptionMessage.VCODE_IS_DISABLE);
			}
		return null;
	}
	
	/**
	 * 发送短信
	 * 
	 * @param params
	 */
	private void sendSMS(Map<String, Object> params) {
		String phone = (String) params.get("phone");
		// 生成6位数字组成的验证码
		String vcode = RandomCodeUtil.getValidationCode(6);
		String message = MessageFormat.format(PropertiesUtil.getPropertyByKey("sms.msg.register.content"), vcode);
		params.put("message", message);
		params.put("type", "tz");
		// // 第三方短信平台发送验证码短信，返回发送状态信息
		Result result = APIRequestUtil.getResultOfSMS(params, "2");

		if (StringUtils.equals("0", result.getCode())) { // 发送短信成功
			JedisUtil jedisUtil = JedisUtil.getInstance();
			jedisUtil.set(phone, vcode, 10 * 60);
		} else {
			throw new BizException(BizExceptionMessage.GET_VCODE_ERROR);
		}
	}
	
	@Override
	@Transactional
	public Map<String, Object> updatePhone(Map<String, Object> params) {
		
		String userId = params.get("userId")==null?null:params.get("userId").toString();
		String companyId= params.get("companyId")==null?null:params.get("companyId").toString();
		String newPhone= params.get("newPhone")==null?null:params.get("newPhone").toString();
		
		if(StringUtils.isEmpty(userId)||StringUtils.isEmpty(newPhone)){
			throw new BizException("3", "缺少参数");
		}
			Map<String,Object> map = new HashMap<>();
			map.put("phone", newPhone);
			List<User> userList = dao.selectByExample(map);
			if(userList.size()>0){
				throw new BizException("3","手机号以存在!");
			}
		//去redis取验证码，然后效验
		String checkCodeFromRedis = JedisUtil.getInstance().get(newPhone);
		if (StringUtils.isNotEmpty(checkCodeFromRedis)) {
			if (!StringUtils.equals(checkCodeFromRedis, (String) params.get("checkCode"))) { // 验证码错误
				throw new BizException(BizExceptionMessage.CHECKCODE_ERROR);
			}
		} else {
			// 无效的checkCode
			throw new BizException(BizExceptionMessage.VCODE_IS_DISABLE);
		}
		
		List<Object> paramArray = new ArrayList<Object>();
		paramArray.add(Integer.valueOf(companyId));
		paramArray.add(Integer.valueOf(userId));
		paramArray.add(newPhone);
		
		try {
			//调用AJUC 修改用户手机
			String value = HrmHttpClientUtil.getDataFromNewAjuc(PropertiesUtil.getPropertyByKey("ajuc.new"), "RpcSpecialUser","changePhone",paramArray);
			
			JSONObject json = JSONObject.parseObject(value);
			String code = json.getString("code");
			if(!"0".equals(code)){
				//表示失败
				throw new BizException("3","修改失败！");
			}
			
			
			User updateUser = new User();
			updateUser.setId(Long.valueOf(userId));
			updateUser.setPhone(newPhone);
			
			dao.updateByExample(updateUser);
			
				
		} catch (Exception e) {
			throw new BizException("3",e.getMessage());
		}
		
		
		return null;
	}
	
	
}
