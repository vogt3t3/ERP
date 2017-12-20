package com.aiiju.serviceImpl.signRule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.workStage.SignInRulePojo;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IMessagePushLogsDao;
import com.aiiju.dao.oa.workStage.ISignInOutDao;
import com.aiiju.service.signRule.ISignInOutService;
import com.aiiju.util.common.Constant;

/**
 * 这个实现类里的接口为前人所开发(且已离职),里面的接口全部弃用掉
 * @ClassName: SignInOutServiceImpl 
 * @Description: ISignInOutService 实现类
 * @author 琪琪
 * @date 2016年9月9日 上午11:02:53 
 *
 */
@Service("signInOutService")
public class SignInOutServiceImpl implements ISignInOutService{
	private static Logger  log=LoggerFactory.getLogger(SignInOutServiceImpl.class);
	@Autowired
	private ISignInOutDao signInOutDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IMessagePushLogsDao messagePushLogsDao;
	@Override
	public List<SignInRulePojo> getSignRuleByParams(int weekDay) {
		return signInOutDao.getSignRuleByParams(weekDay);
	}
	@Override
	public void sendSpecialUserByRuleId(int ruleId,int alertTime,int type) {
		//获取部门下的所有用户
		List<Department> ruleDepts=signInOutDao.getDeptsByRuleId(ruleId);
		List<User> userList = new ArrayList<User>();
		//需要提醒的大系统用户
		String loginUserIds=Constant.EMPTY_STR;
		//用户条件map
		Map<String, Object> map=new HashMap<String, Object>();
		//遍历使用的规则部门并且获取用户
		if(ruleDepts!=null&&!ruleDepts.isEmpty()){
			List<Integer> deptIds=new ArrayList<Integer>();
			for(Department dept:ruleDepts){
				deptIds.add(dept.getId());
				map.put("deptIds", deptIds);
				userList=userDao.selectByExample(map);
				int defaultUserId=61;
				if(userList!=null&&!userList.isEmpty()){
					//考勤消息默第一个人为发起者
					defaultUserId=Integer.parseInt(userList.get(0).getId().toString());
					for(int i=0;i<userList.size();i++){
						User user=userList.get(i);
						String loginUserId=user.getLoginUserId().toString();
						if(i==userList.size()-1){
							loginUserIds+=loginUserId;
						}else{
							loginUserIds+=loginUserId+",";
						}
						
					}	
					
					String notices="还有"+alertTime+"分钟就要上班了，别忘记打卡哦";
					if(type==Constant.NUM_ONE){
						notices="还有"+alertTime+"分钟就要下班了，别忘记打卡哦";
					}
					/*if(!StringUtils.isEmpty(loginUserIds)){
							//获取真正不重复的userIds 推送消息
							SpecialUserUtil.sendMessageByPhpAPI(dept.getCompanyId().toString(), notices,loginUserIds ,6);
							//发送openfire消息
							SpecialUserUtil.sendIMMessageByPhpAPI(dept.getCompanyId().toString(), "[考勤]"+notices,defaultUserId,loginUserIds ,7);
							args.put("title", notices);
							insertMessagePushLogs(loginUserIds,args);
					}*/
					
				}
			}
			
		}
				
		 
		
	}
	public void insertMessagePushLogs(String realUserIds,Map<String, Object> params){
		//将发送的消息插入到数据库
		if(!StringUtils.isEmpty(realUserIds)){
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			String[] userIds=realUserIds.split(",");
			for(String userId:userIds){
				Map<String, Object>  map=new HashMap<String, Object>();
				map.put("userId", (Integer.parseInt(userId)));
				//map.put("visitId",params.get(Constant.VISIT_ID));
				map.put("relateId",-1);
				map.put("title",params.get("title"));
				map.put("content",Constant.EMPTY_STR);
				map.put("type",2);//1是公告2是考勤
				list.add(map);
			}
			try {
				messagePushLogsDao.insertMessagePushLogs(list);	
			} catch (Exception e) {
				e.printStackTrace();
				log.error("考勤时插入推送消息记录报错："+e.getMessage());
				//new BizException(BizExceptionMessage.DB_ERROR);
			}
			
		}
	}
	
}
