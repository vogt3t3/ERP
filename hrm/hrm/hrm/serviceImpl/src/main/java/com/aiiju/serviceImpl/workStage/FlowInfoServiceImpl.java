package com.aiiju.serviceImpl.workStage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.workStage.FlowInfo;
import com.aiiju.bean.oa.workStage.FlowReimburse;
import com.aiiju.bean.oa.workStage.FlowVacation;
import com.aiiju.bean.oa.workStage.HistoryLog;
import com.aiiju.bean.oa.workStage.Reply;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IFlowInfoDao;
import com.aiiju.dao.oa.workStage.IFlowReimburseDao;
import com.aiiju.dao.oa.workStage.IFlowVacationDao;
import com.aiiju.dao.oa.workStage.IHistoryLogDao;
import com.aiiju.dao.oa.workStage.IReplyDao;
import com.aiiju.dao.oa.workStage.IReplyLogDao;
import com.aiiju.service.workStage.IFlowInfoService;
import com.aiiju.serviceImpl.CommonPageService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.enums.FeeTypeEnum;
import com.aiiju.util.enums.LeaveTypeEnum;
import com.aiiju.util.enums.ReimburseEnum;
import com.aiiju.util.enums.SceneTypeEnum;
import com.aiiju.util.enums.StepEnum;
import com.aiiju.util.enums.VacationEnum;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.pushMessage.PushUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("flowInfoService")
public class FlowInfoServiceImpl extends CommonPageService implements IFlowInfoService {

	/**
	 * 日志log打印
	 */
	private static Logger logger = LoggerFactory.getLogger(FlowInfoServiceImpl.class);

	@Autowired
	private IFlowInfoDao flowInfoDao;
	@Autowired
	private IFlowVacationDao flowVacationDao;
	@Autowired
	private IFlowReimburseDao flowReimburseDao;
	@Autowired
	private IHistoryLogDao historyLogDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IReplyDao replyDao;
	@Autowired
	private IReplyLogDao replyLogDao;
	@Autowired
	private IDepartmentDao departmentDao;
	@Autowired
	private ICommonPageDao commonPageDao;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String,Object> addFlowInfo(Map<String, Object> params) {
		Map<String,Object> resultMap= new HashMap<>();
		
		//参数转换
		FlowInfo flowInfo=JSONObject.parseObject(params.get("flowInfo").toString(),FlowInfo.class);
		flowInfo.setCompanyId(Long.parseLong(params.get("companyId").toString()));
		flowInfo.setUserId(Long.parseLong(params.get("userId").toString()));
		String sendToPersons=flowInfo.getSendToPerson();
		String[] sendToPersonArr=sendToPersons.split(",");
		//审批表添加一条记录
		flowInfoDao.addFlowInfo(flowInfo);
		//发送给哪些人了，在zds_history_log里面添加多条记录
		List<HistoryLog> logs=new ArrayList<HistoryLog>();
		//添加第一个审核人
		for(int i=0;i<sendToPersonArr.length;i++){
			String id=sendToPersonArr[i];
			if(!StringUtils.isEmpty(id)&&!id.equals(",")){
				HistoryLog log=new HistoryLog(Long.parseLong(id),flowInfo.getCompanyId(),flowInfo.getId(),3);
				logs.add(log);
				break;
			}
			
		}
		historyLogDao.addHistoryLog(logs);
		//请假表或者报销表添加一条记录n
		int type=flowInfo.getFlowType();
		if(type==2){//请假
			List<FlowVacation> flowVacationList=JSONArray.parseArray(params.get("multiple").toString(),FlowVacation.class);
			if(flowVacationList!=null&&!flowVacationList.isEmpty()){
				for(FlowVacation fv:flowVacationList){
					fv.setCompanyId(flowInfo.getCompanyId());
					fv.setUserId(flowInfo.getUserId());
					fv.setFlowId(flowInfo.getId());
					flowVacationDao.addFlowVacation(fv);
				}
			}
			
		}else if(type==3){//报销
			List<FlowReimburse> flowReimburseList=JSONArray.parseArray(params.get("multiple").toString(),FlowReimburse.class);
			if(flowReimburseList!=null&&!flowReimburseList.isEmpty()){
				for(FlowReimburse fr:flowReimburseList){
					fr.setCompanyId(flowInfo.getCompanyId());
					fr.setUserId(flowInfo.getUserId());
					fr.setFlowId(flowInfo.getId());
					flowReimburseDao.addFlowReimburse(fr);
				}
			}
			
		}
		//消息推送
		// TODO Auto-generated method stub
		String pushTitle =  params.get("userName").toString()+"向您发起了审批("+
				(type==2?"请假":"报销")
				+")。";
		params.put("pushTitle",pushTitle);
		params.put("pushUserIds",logs.get(0).getReceiveUid().toString());
		PushUtil.sendNoticesByPhp(params,3,1);
		
		resultMap.put("id", flowInfo.getId());
		resultMap.put("sceneType", 3);
		resultMap.put("flowTypeId", type);
		
		return resultMap;
	}
	@Override
	public int withdrawFlowInfo(Map<String, Object> params) {
		//参数转换
		//FlowInfo flowInfo=JSONObject.parseObject(params.get("flowInfo").toString(),FlowInfo.class);
		Long flowId=Long.parseLong(params.get("id").toString());
		FlowInfo fi=new FlowInfo();
		fi.setId(flowId);
		fi.setIsWithdraw((byte)1);
		fi.setStep((byte)4);
		//修改审批，set isWithDraw=1
		flowInfoDao.updateFlowInfo(fi);
		return 0;
	}
	@Override
	public Map<String,Object> getFlowInfoList(Map<String, Object> params) {
		Map<String,Object> result=new HashMap<String, Object>();
		String flowId=params.get("flowId")==null?null:params.get("flowId").toString();
		Pagination pt=null;
		if(StringUtils.isEmpty(flowId)){
			//查询列表
			//flowType 2 请假 3 审批
			//type 1我收到的 2 我发出的 3待我处理的
			int type=Integer.parseInt(params.get("type").toString());
			params.put("isDel", 0);
			if(type!=2){	
			//type==1||type==3 参数：receiveUid companyId isDel=0 
				params.put("receiveUid", params.get("userId"));
				params.put("userId",null);
				params.put("isWithdraw", 0);
				
			}
			if(type==3){
				params.put("isDeal",0);
			}
			//获取审核列表总条数
			int num=flowInfoDao.getFlowInfoCounts(params);
			int pageNum=Integer.parseInt(params.get("pageNum").toString());
			int pageSize=Integer.parseInt(params.get("pageSize").toString());
			pt=new Pagination(num,pageNum,pageSize);
			params.put("pageSize", pageSize);
			params.put("startRow", pt.getStartPos());
		}
		//获取审核列表
		List<FlowInfo> flowInfoList=flowInfoDao.getFlowInfoList(params);
//		String fileUrl=PropertiesUtil.getPropertyByKey("file_url");
		if(flowInfoList!=null&&!flowInfoList.isEmpty()){
			for(FlowInfo fi:flowInfoList){
				User user = fi.getUser();
				//增加app端所需字段title和stepText
				if(fi.getFlowType()==2){
					if(user!=null){
						fi.setTitle(user.getName()+"的请假申请");
					}else{
						fi.setTitle("请假申请");
					}
				}else if(fi.getFlowType()==3){
					if(user!=null){
						fi.setTitle(user.getName()+"的报销申请");
					}else{
						fi.setTitle("报销申请");
					}
				}
				if(StepEnum.PENDING.getValue().intValue()==fi.getStep()){
					fi.setStepText(StepEnum.PENDING.getDesc());
				}else if(StepEnum.PROCESSING.getValue().intValue()==fi.getStep()){
					fi.setStepText(StepEnum.PROCESSING.getDesc());
				}else if(StepEnum.AGREE.getValue().intValue()==fi.getStep()){
					fi.setStepText(StepEnum.AGREE.getDesc());
				}else if(StepEnum.DISAGREE.getValue().intValue()==fi.getStep()){
					fi.setStepText(StepEnum.DISAGREE.getDesc());
				}else {
					fi.setStepText("已撤销");
				}
				result.clear();
				fi.setCreateTime(DateUtil.relativeDateFormat(fi.getCreateTime(),1));
				result.put("companyId", fi.getCompanyId());
				result.put("pid",fi.getId());
				result.put("sceneType", 3);
				int count=replyDao.getReplyListCountByPid(result);
				fi.setReplyNum(count);
				List<FlowVacation> vList=fi.getVacationList();
				if(vList!=null&&!vList.isEmpty()){
					for(FlowVacation fv:vList){
						if(!StringUtils.isEmpty(fv.getVacationType())){
							fv.setVacationTypeStr(VacationEnum.getValue(fv.getVacationType().toString()));
						}
						
					}
				}
				List<FlowReimburse> rList=fi.getReimburseList();
				if(rList!=null&&!rList.isEmpty()){
					for(FlowReimburse fr:rList){
						if(!StringUtils.isEmpty(fr.getFeeType())){
							fr.setFeeTypeStr(ReimburseEnum.getValue(fr.getFeeType().toString()));
						}
					}
				}
				//审批流程列表
				List<Reply> logs=fi.getReplyList();
				if(logs==null||logs.isEmpty()){
					logs=new ArrayList<Reply>();	
				}
				List<Reply> logsShow=new ArrayList<Reply>();//给前端封装的logs
				StringBuilder persons=new StringBuilder();//发送人
				//是否撤回 
				if(fi.getIsWithdraw().intValue()==1){
					Reply endReply=new Reply(3,user.getName(),"已撤回",DateUtil.relativeDateFormat(fi.getUpdateDate(), 1));
					logs.add(endReply);
				}else{
					List<User> splist=fi.getSendToPersonList();
					for(User u:splist){
						persons.append(u.getName()).append(",");
						Reply  reply=new Reply(0,u.getName(),"待审核",null);
						if(logs!=null&&!logs.isEmpty()){
							for(Reply r:logs){
								if(r.getUserId()!=null&&u.getId().intValue()==r.getUserId().intValue()){
									reply.setIsAgree(r.getIsAgree());
									reply.setCreateTime(DateUtil.relativeDateFormat(r.getCreateTime(), 1));
								}
							}
							logsShow.add(reply);
						}else{
							logsShow.add(reply);
						}
						
					}
					String personstr=persons.toString();
					if(personstr.length()>1){
						fi.setPersons(personstr.substring(0, personstr.length()-1));
					}
				}
				Reply startReply=new Reply(0,user.getName(),"发起审核",fi.getCreateTime());
				logsShow.add(0,startReply);
				fi.setReplyList(logsShow);
			}
		}
		result.put("page",pt);
		result.put("result", flowInfoList);
		return result;
	}
	@Override
	public Map<String, Object> getToDoApplicationList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getFlowInfoDetailForApp(Map<String, Object> params) {
		
		//默认审批 操作为0（O：不能审批，1：可以审批）
		String toDeal = "0";
		Map<String, Object> resultMap=new HashMap<String, Object>();
		FlowInfo flowInfo = null;
		//审批的id
		String flowId = params.get("flowId") == null ? null : params.get("flowId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		if(StringUtils.isEmpty(flowId)){
			resultMap.put("message", "参数为空");
		}else{
			//1.获取审批详情基本信息
			flowInfo = flowInfoDao.getFlowInfoByParams(params);
			if(flowInfo!=null){
				StepEnum descOfValue = StepEnum.getDescOfValue(new Integer(flowInfo.getStep()));
				flowInfo.setStepText(descOfValue.getDesc());
				//1.1 查询审批发起者的头像信息
				Map<String,Object> flowSendserMap = new HashMap<String,Object>();
				User flowSendser = flowInfo.getUser();
				flowSendserMap.put("fId",flowSendser.getId());
				flowSendserMap.put("businessId",5);//5表示app端头像
				List<FileInfo> fileList = commonPageDao.getFileInfoList(flowSendserMap);
				if(fileList!=null&&fileList.size()>0){
					flowSendser.setUrl(fileList.get(0).getUrl());
				}else{
					flowSendser.setUrl("");
				}
				
				//1.2查询审批发起者的部门信息
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", flowSendser.getId());
				User userDB = userDao.seleteById(map);
				if(userDB!=null){
					flowSendser.setDeptId(userDB.getDeptId());
					flowSendser.setDeptList(userDB.getDeptList());
				}
				flowInfo.setUser(flowSendser);
				
				//2.根据流程类型分别查询相关信息
				int type = flowInfo.getFlowType().intValue();
				if(type==2){//2.1 若为请假,则查询请假相关的信息
					List<FlowVacation> vacationList = new ArrayList<FlowVacation>();
					FlowVacation vacation = flowVacationDao.getFlowVacationByParams(params);
					vacation.setVacationTypeStr(VacationEnum.getValue(vacation.getVacationType().toString()));
					vacationList.add(vacation);
					flowInfo.setVacationList(vacationList);
				}else if(type==3){//2.2 若为报销,则查询报销相关的信息
					List<FlowReimburse> reimburseList = flowReimburseDao.getFlowReimburseByParams(params);
					for (FlowReimburse reimburse : reimburseList) {
						reimburse.setFeeTypeStr((FeeTypeEnum.getDescOfValue(reimburse.getFeeType()).getDesc()));
					}
					flowInfo.setReimburseList(reimburseList);
				}
//				//3.获取审批信息(即回复列表信息)
//				params.put("pid", flowId);
//				params.put("sceneType", SceneTypeEnum.SHENPI.getValue());//审批
//				
//				List<Reply>  replyList = replyDao.getReplyListByPid(params);
//				Map<String,Object> replyerMap = new HashMap<String,Object>();
//				if(replyList!=null&&replyList.size()>0){
//					for (Reply rep : replyList) {
//						//3.1 查询回复者的头像
//						replyerMap.put("fId",rep.getUserId());
//						replyerMap.put("businessId",10);//10表示app端头像
//						List<FileInfo> fileList2 = commonPageDao.getFileInfoList(replyerMap);
//						if(fileList2!=null&&fileList2.size()>0){
//							rep.setLogo(PropertiesUtil.getPropertyByKey("file_url_app")+fileList2.get(0).getUrl());
//						}else{
//							rep.setLogo("");
//						}
//					}
//					flowInfo.setReplyList(replyList);
//				}
				///3.获取审批信息(审批流程)
				//接收人，多个用 , 分隔
				String sendToPerson = flowInfo.getSendToPerson();
				String[] split = sendToPerson.split(",");
				Map<String,Object> m = new HashMap();
				m.put("sceneType","3");
				m.put("refId",flowInfo.getId());
				m.put("companyId",flowInfo.getCompanyId());
				m.put("order","asc");
				m.put("sendToPersonIds", split);
				//得到接收者信息
				List<User> userList = userDao.selectUserListById(m);
				//得到审批的流程记录（当流程卡在多个审批人的中间时，之后的人是没有历史记录的）
				List<HistoryLog> historyLogs = historyLogDao.getHistoryLogs(m);
				List<Reply> replyList = new ArrayList<>();
				for (String uId : split) {
					Reply reply = new Reply();
					for (User user : userList) {
						if(user.getId().toString().equals(uId)){
							reply.setUserId(user.getId());
							reply.setUserName(user.getName());
							reply.setLogo(user.getUserHeadImg()==null?"":user.getUserHeadImg());
							reply.setCreateTime("");
							reply.setIsAgree(0);
							reply.setReplyCon("");
							reply.setAgreeText("待审核");
							break;
						}
					}
					//流程历史上有记录并且isread为1（已处理）先全部设为同意,在最后看审批状态是拒绝时，把历史流程记录的最后一个的流程状态改为拒绝
					for (HistoryLog historyLog : historyLogs) {
						if(historyLog.getReceiveUid().toString().equals(uId)&&historyLog.getIsRead().toString().equals("1")){
							reply.setCreateTime(DateUtil.relativeDateFormat(historyLog.getCreateDate(), 2));
							reply.setIsAgree(1);
							reply.setReplyCon("同意");
							reply.setAgreeText("已同意");
							break; 
						}
					}
					replyList.add(reply);
				}
				//当审批状态为拒绝时，吧最后一人的流程状态改为拒绝
				if(flowInfo.getStep().toString().equals("3")){
					Reply reply = replyList.get(historyLogs.size()-1);
					reply.setIsAgree(2);
					reply.setReplyCon("拒绝");
					reply.setAgreeText("已拒绝");
				}
				
				
				//把审批发起人加到集合头
				Reply reply = new Reply();
				reply.setUserId(flowInfo.getUser().getId());
				reply.setUserName(flowInfo.getUser().getName());
				reply.setLogo(flowInfo.getUser().getUrl()==null?"":flowInfo.getUser().getUrl());
				reply.setCreateTime(DateUtil.relativeDateFormat(flowInfo.getCreateTime(), 2));
				reply.setIsAgree(0);
				reply.setReplyCon("");
				reply.setAgreeText("发起审核");
				replyList.add(0, reply);
				
				flowInfo.setReplyList(replyList);
				if(flowInfo.getIsWithdraw().toString().equals("1")){
					
					Reply withdrawreply = new Reply();
					withdrawreply.setUserId(flowInfo.getUser().getId());
					withdrawreply.setUserName(flowInfo.getUser().getName());
					withdrawreply.setLogo(flowInfo.getUser().getUrl()==null?"":flowInfo.getUser().getUrl());
					withdrawreply.setCreateTime(DateUtil.relativeDateFormat(flowInfo.getUpdateDate(), 2));
					withdrawreply.setIsAgree(3);
					withdrawreply.setReplyCon("撤销");
					withdrawreply.setAgreeText("已撤销");
					replyList.add(withdrawreply);
				}
				
				
				
				//4.查询这个审批相关的附件
				if(!StringUtils.isEmpty(flowInfo.getFile())){
					params.put("id", flowInfo.getFile());
					
					List<FileInfo> fileLists = commonPageDao.getFileInfoList(params);
					if(fileLists!=null&&!fileLists.isEmpty()){
						for(FileInfo fi:fileLists){
							int index=fi.getUrl().lastIndexOf("/");
							 fi.setThumbUrl(fi.getUrl().substring(0,index+1)+"Thumb_"+fi.getUrl().substring(index+1));
						}
					}
					flowInfo.setFileLists(fileLists);
				}
				//5.整个审批的业务场景类型
				flowInfo.setSceneType(SceneTypeEnum.SHENPI.getValue());
				
				
				//6.判断当前用户是否可以操作审批动作
				//历史记录有几条相当于，相当于审批在第几个执行者了（先不管审批的状态）
				int userNum = historyLogs.size();
				//去接受者数组找到第几个执行者的ID，然后跟当前用户比较，如果是把toDeal为1（可以操作）
				String executorId = split[userNum-1];
				if(executorId.equals(userId)&&(flowInfo.getStep().toString().equals("0")
								||flowInfo.getStep().toString().equals("1"))){
						
					toDeal="1";
					
				}
			}
		}
		
		
		
		resultMap.put("result", flowInfo);
		resultMap.put("toDeal", toDeal);
		return resultMap;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int dealFlowInfo(Map<String, Object> params) {
		//获取当前用户
		Long userId=Long.parseLong(params.get("userId").toString()); 
		//如果2拒绝表示整个流程结束 1同意2拒绝 0表示纯的回复
	    int isAgree=Integer.parseInt(params.get("isAgree").toString());
	    //获取审批流程对象
	    params.put("flowId", params.get("pid"));
	    FlowInfo flowInfo=flowInfoDao.getFlowInfoByParams(params);
	    //获取审批信息的发送对象
	    String sendToPerson=flowInfo.getSendToPerson();
	   //获取当前用户下标
	    String[] arr=sendToPerson.split(",");
	    int index= getCurUserIndex(arr,userId);
	    //审批流程处理模块
	    //如果2拒绝表示整个流程结束 1同意2拒绝 0表示纯的回复
	    String string = params.get("replyCon")==null?"":params.get("replyCon").toString();
	    if(isAgree!=0){
	    	Byte step=(byte)(isAgree+1);
	    	HistoryLog log=null;
	    	//若拒绝整个业务结束 判断当前用户是否是当前最后一个发送人
	    	//更新historyLog is_read 表示已处理
	    	 log=new HistoryLog();
	    	log.setRefId(flowInfo.getId());
	    	log.setIsRead(1);
	    	log.setCompanyId(flowInfo.getCompanyId());
	    	log.setSceneType(Integer.parseInt(params.get("sceneType").toString()));
	    	historyLogDao.updateHistoryLog(log);
	    	if(string==null)string="已拒绝";
	    	
	    	if(isAgree==1&&!userId.toString().equals(arr[arr.length-1].toString())){
	    		step=1;//处理中
	    		String nextSend=arr[index+1];
	    		
		    	
	    		//添加一条history_log
	    		List<HistoryLog> logs=new ArrayList<HistoryLog>();
	    		log=new HistoryLog(Long.parseLong(nextSend),flowInfo.getCompanyId(),flowInfo.getId(),3);
	    		logs.add(log);
	    		historyLogDao.addHistoryLog(logs);
	    	    //TODO 添加一条消息推送
	    		if(string==null) string="已同意";
	    	}
	    	//流程步骤更新 step 0 待处理，1处理中，2已同意，3已拒绝 4.已撤销
	    	flowInfo.setStep(step);
	    	flowInfoDao.updateFlowInfo(flowInfo);
 	   
	    }
	    
	    //回复消息处理模块
	    //创建一条回复信息
	    String receiveUid = params.get("receiveUid")==null?"0":
	    						params.get("receiveUid").toString().trim().equals("")?"0":params.get("receiveUid").toString();
    	Reply reply=new Reply(flowInfo.getCompanyId(), 3, userId, Long.parseLong(params.get("pid").toString()), 
    							Long.parseLong(receiveUid),string, isAgree, flowInfo.getUserId());
    	
    	Object obj = params.get("replyPid");
    	if(!StringUtils.isEmpty(obj)){
    		reply.setReplyPid(Long.parseLong(obj.toString().trim()));
    	}else {
    		reply.setReplyPid(0l);
		}
    	replyDao.addReplyInfo(reply);
    	//回复的发送者：创建审批的人以及zds_history_log 有的人
    	
    	
//    	List<Long> receiveIds=new ArrayList<Long>();
//    	receiveIds.add(flowInfo.getUserId());
//    	params.put("refId",flowInfo.getId());
//    	for(int i=0;i<index;i++){
//    		receiveIds.add(Long.parseLong(arr[i]));
//    	}
//    	params.put("receiveIds", receiveIds);
//    	params.put("replyId", reply.getId());
//    	replyLogDao.batchAddReplyLogInfo(params);
    	
    	
    	List<Reply> replies = null;
		try {
			replies = replyDao.getReplyListByPid(params);
		} catch (Exception e) {
			logger.info("根据pid查询用户ID失败：" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		// 组装zds_reply_log新增时需要的receive_id
		List<Long> receiveIds = new ArrayList<Long>();
		if (!org.apache.commons.lang3.StringUtils.equals(String.valueOf(params.get("userId")), String.valueOf(flowInfo.getUserId()))) { // 发送者不是汇报/任务/审批的发起人
			receiveIds.add(flowInfo.getUserId());
		}
		if (null != replies && !replies.isEmpty()) {
			replies.stream().filter(rep -> !rep.getUserId().equals(Long.valueOf(String.valueOf(params.get("userId")))))
					.map(Reply::getUserId).distinct().forEach(receiveId -> receiveIds.add(receiveId));
		}
		params.put("receiveIds", receiveIds.stream().distinct().collect(Collectors.toList()));
		// zds_reply_log批量新增
		if (null != receiveIds && !receiveIds.isEmpty()) {
			try {
				params.put("replyId",reply.getId());
				replyLogDao.batchAddReplyLogInfo(params);
			} catch (Exception e) {
				logger.info("批量新增回复日志记录失败：" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "批量新增回复日志记录失败");
			}
		}
		String pushTitle=null;
		String pushUserIds=null;
		int  sceneTypeId =5 ;
		int  phpType =6;
		//实时消息推
		if(isAgree!=0){
			pushTitle =  params.get("userName").toString()+(isAgree==1?"同意":"拒绝")+"了您的审批("+
				(flowInfo.getFlowType()==(byte)2?"请假":"报销")+")。";
			pushUserIds=flowInfo.getUserId().toString();
		}else{
			if(reply.getReceiveUid().toString().equals("0")){
				Map<String,Object> userMap = new HashMap<>();
				userMap.put("id",flowInfo.getUserId());
				userMap.put("companyId",flowInfo.getCompanyId());
				User user = userDao.seleteById(userMap);
				pushTitle = params.get("userName").toString()+"对"+user.getName()+"的审批("+
				((flowInfo.getFlowType()==(byte)2?"请假":"报销"))+"发表了回复。";
				 pushUserIds = receiveIds.toString().substring(1, receiveIds.toString().length()-1).replace(" ","");
			
			}else{
				Map<String,Object> userMap = new HashMap<>();
				userMap.put("id",reply.getReceiveUid());
				userMap.put("companyId",flowInfo.getCompanyId());
				User user = userDao.seleteById(userMap);
				pushTitle = params.get("userName").toString()+"对"+user.getName()+"的回复发表了回复。";
				pushUserIds = receiveIds.toString().substring(1, receiveIds.toString().length()-1).replace(" ","");
			}
		}
		params.put("pushTitle",pushTitle);
		params.put("pushUserIds",pushUserIds);
		PushUtil.sendNoticesByPhp(params,sceneTypeId,phpType);
		
		
		if(isAgree==1&&!userId.toString().equals(arr[arr.length-1].toString())){
			
			Map<String,Object> userMap = new HashMap<>();
			userMap.put("id",flowInfo.getUserId());
			userMap.put("companyId",flowInfo.getCompanyId());
			User user = userDao.seleteById(userMap);
			
			 pushTitle =  user.getName()+"向您发起了审批("+
					(flowInfo.getFlowType()==(byte)2?"请假":"报销")+")。";
			 pushUserIds = arr[index+1];;
			 sceneTypeId =3 ;
			 phpType =1;
			 params.put("pushTitle",pushTitle);
			 params.put("pushUserIds",pushUserIds);
			 PushUtil.sendNoticesByPhp(params,sceneTypeId,phpType);
		
		}
		
		return 0;
	}
	/**
	 * 返回某字符串下标
	 * @param sendToPerson 以逗号分隔
	 * @param userId 跟某一字符串比较
	 * @return
	 */
	public int getCurUserIndex(String[] arr,Long userId){
		    int result=-1;
		    if(arr!=null&&arr.length>0){
		    	 for(int i=0;i<arr.length;i++){
				    	if(arr[i].equals(userId.toString())){
				    		result=i;
				    	}
				    }	
		    }
		    return result;
	}
	@Override
	public List<Map<String,String>> getFlowInfoEnums(Map<String, Object> params) {
		List<Map<String,String>> list=null;
		//flowType 2是请假 3是报销
		Integer flowType=Integer.parseInt(params.get("flowType").toString());
		if(flowType.intValue()==2){
			list=VacationEnum.getVacationEnumLists();
		}else if(flowType.intValue()==3){
			list=ReimburseEnum.getReimburseEnumList();
		}
		return list;
	}
	
	@Override
	public Map<String, Object> getFlowReimburseListByParams(Map<String, Object> params) {
		// 获取报销数据列表
		List<FlowInfo> flowReimburseList = getFlowReimburseListResult(params, 3);
		Pagination page = getPageInfo(params);

		// 组装返回数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", flowReimburseList);
		resultMap.put("page", page);
		return resultMap;
	}
	
	@Override
	public HSSFWorkbook exportFlowReimburseExcel(Map<String, Object> params) {
		// 获取报销数据列表
        List<FlowReimburse> flowReimburseList = getFlowReimburseListExport(params);
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("createDate", "时间");
        headers.put("userName", "姓名");
        headers.put("deptName", "部门");
        headers.put("remark", "报销申请");
        headers.put("feeTypeStr", "类型");
        headers.put("fees", "报销金额");
        headers.put("recipient", "审批人");
        headers.put("stepDesc", "状态");
        HSSFWorkbook book = ExcelUtil.exportExcel("报销表("+DateUtil.getCurDay()+")", headers, flowReimburseList, params);
        return book;
	}
	
	/**
     * 
     * @Title: getFlowReimburseListResult 
     * @Description: 获取报销数据列表
     * @param params
     * @return 
     * @throws
     */
    private List<FlowInfo> getFlowReimburseListResult(Map<String, Object> params, int type) {

    	if (2 == type) {
    		params.put("flowType", 2);
    	} else if (3 == type) {
    		params.put("flowType", 3);
    	}
    	
        List<FlowInfo> list = new ArrayList<FlowInfo>(); // 测试通过后就可以修改了
        Pagination page = null;

        // 请求参数中是否有部门。有，查询部门下的用户并组装数据返回。
        setAllUserId(params);

        @SuppressWarnings("unchecked")
        List<User> allUserList = (List<User>) params.get("allUserList");

        if (null != allUserList && allUserList.size() > 0 && !allUserList.equals("null")) {
            // 获取报销数据总数
            int totalCount;

            totalCount = flowInfoDao.getFlowInfoCountsByParams(params);
            // 获取分页信息
            params.put("totalCount", totalCount);
            page = getPageInfo(params);

            // 分页获取获取报销数据列表()
            if (totalCount > 0) {
				params.put("startPos", String.valueOf(page.getStartPos()));
				params.put("pageSize", String.valueOf(page.getPageSize()));
                list = flowInfoDao.getFlowInfoListByParams(params);
            }

            if (null != list && list.size() > 0) { // 筛选的结果不为空
                params.put("flowReimburseList", list);
                // 组装报销数据
                for (FlowInfo fr : list) {
                    // 报销类型枚举
                    // String feeTypeDesc =
                    // FeeTypeEnum.getDescOfValue(fr.getFeeType()).getDesc();
                    // fr.setFeeTypeDesc(feeTypeDesc);
                    // 状态枚举
                    String stepDesc = StepEnum.getDescOfValue(fr.getStep().intValue()).getDesc();
                    fr.setStepDesc(stepDesc);
                }
            }
        }

        return list;
    }
    
    private List<FlowReimburse> getFlowReimburseListExport(Map<String, Object> params) {

        List<FlowReimburse> list = new ArrayList<FlowReimburse>();

        // 请求参数中是否有部门。有，查询部门下的用户并组装数据返回。
        setAllUserId(params);
        @SuppressWarnings("unchecked")
        List<User> allUserList = (List<User>) params.get("allUserList");
        if (null != allUserList && allUserList.size() > 0 && !allUserList.equals("null")) {
            // 获取报销数据总数
            int totalCount;
            totalCount = flowReimburseDao.getTotalNumByParams(params);
            // 获取分页信息
            params.put("totalCount", totalCount);

            if (totalCount > 0) {
                params.put("startPos", null);
                params.put("pageSize", null);
                list = flowReimburseDao.getFlowReimburseListByParams(params);
            }

            if (null != list && list.size() > 0) { // 筛选的结果不为空
            	params.put("flowReimburseList", list);

                // 以下代码是获取接收人信息，获取所有的send_to_person
                StringBuilder sendToPersons = new StringBuilder();
                list.forEach(flowReimburse -> sendToPersons.append(flowReimburse.getSendToPerson() + ","));
                // 根据user_id查询所有用户
                String sendToPersonIds = "";
                if (null != sendToPersons && sendToPersons.length() > 0) {
                    sendToPersonIds = sendToPersons.substring(0, sendToPersons.length() - 1);
                    sendToPersonIds = sendToPersonIds.replace("null", "");
                    sendToPersonIds = sendToPersonIds.replace("NaN", "");
                    params.put("sendToPersonIds", sendToPersonIds.split(","));
                }
                
                // 获取接收人信息
                List<User> recipientInfos = userDao.selectUserListById(params);
                // 将接收人姓名List转换为map(Java8支持用stream和 lambda能够实现List<V>转换为Map<K, V>)
                Map<Long, String> recipientInfoMap = recipientInfos.stream()
                        .collect(Collectors.toMap(User::getId, User::getName));
                // 组装报销数据
                for (FlowReimburse fr : list) {
                	// 将收件人姓名组装到list中去
                    String legalSentToPerson = fr.getSendToPerson().replace("NaN", "").replace("null", "");
                    fr.setSendToPerson(legalSentToPerson);
                    String[] sendToPersonArray = fr.getSendToPerson().split(",");
                    if (null != sendToPersonArray && sendToPersonArray.length > 0) {
                        String recipientName = "";
                        for (String str : sendToPersonArray) {
                            if (!StringUtils.isEmpty(str)) {
                                recipientName += recipientInfoMap.get(Long.parseLong(str)) + ",";
                            }
                        }
                        if (null != recipientName && recipientName.length() > 0) {
                            recipientName = recipientName.substring(0, recipientName.length() - 1);
                        }
                        fr.setRecipient(recipientName);
                    }
                    // 报销类型枚举
                    String feeTypeDesc = FeeTypeEnum.getDescOfValue(fr.getFeeType()).getDesc();
                    fr.setFeeTypeStr(feeTypeDesc);
                    // 状态枚举
                    String stepDesc = StepEnum.getDescOfValue(fr.getStep()).getDesc();
                    fr.setStepDesc(stepDesc);
                    // 日期处理一下
                    fr.setCreateDate(fr.getCreateDate());
                }
            }
        }

        return list;
    }
	
    private void setAllUserId(Map<String, Object> params) {
        Object sendToDept = params.get("sendToDept") == null ? null : params.get("sendToDept").toString();
        Object sendToPerson = params.get("sendToPerson") == null ? null : params.get("sendToPerson").toString();
        String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
        Gson gson = new Gson();
        List<User> allUserList = new ArrayList<User>();
        Map<String, Object> requestParam = new HashMap<String, Object>();
        if (null != sendToDept && null != companyId) { // 如果不为空， 则根据请求参数查询其子部门，最终返回符合条件的所有(最底层)子部门
            // 前端给出的部门节点
            List<Department> deptPojos = gson.fromJson(sendToDept.toString(), new TypeToken<List<Department>>(){}.getType());
            // 根据传入查询部门，返回该部门及所有子部门
            List<Department> allDepts = null;
            // 根据companyId获取所有的部门
            try {
            	requestParam.put("companyId", companyId);
            	allDepts = departmentDao.getDepartmentList(requestParam);
            } catch (Exception e) {
            	throw new BizException(BizExceptionMessage.DB_ERROR, "根据companyId获取所有部门失败");
            }
            List<Department> subDeptIdList = new ArrayList<Department>();
            List<Department> showDepts = new ArrayList<Department>();
            DeptUtil.getAllDepts(deptPojos, allDepts, showDepts);
    		for(Department dept : showDepts){
    			Department department = new Department();
    			department.setId(dept.getId());
    			subDeptIdList.add(department);
    		}
    		
    		if (!subDeptIdList.isEmpty()) {
    			requestParam.put("deptIds", subDeptIdList);
    			allUserList = userDao.selectByExample(requestParam);
    		}
        }
        // 搜索条件中存在send_to_person
        if (null != sendToPerson && !sendToPerson.equals("[]")) {
            // 前端给出的人员节点
            List<User> specialUserPojos = gson.fromJson(sendToPerson.toString(), new TypeToken<List<User>>(){}.getType());
            allUserList.addAll(specialUserPojos);
        }
        
        if (null != allUserList && allUserList.size() > 0) {
            params.put("allUserList", allUserList);
        } else {
            params.put("allUserList", null);
        }
    }
    
    /**
     * 
     * @Title: getPageInfo 
     * @Description: 获取分页信息
     * @param params
     * @return 
     * @throws
     */
    private Pagination getPageInfo(Map<String, Object> params) {
        Pagination page = null;

        // 获取分页信息
        Integer totalCount = (Integer) params.get("totalCount");
        if (null == totalCount) {
            totalCount = 0;
        }
        String pageNum = (String) params.get("pageNum");
        String pageSize = (String) params.get("pageSize");
        page = new Pagination(totalCount, pageNum, pageSize);

        return page;
    }

	@Override
	public Map<String, Object> getFlowVacationListByParams(Map<String, Object> params) {
		// 获取请假数据列表
		List<FlowInfo> leaveReportList = getFlowReimburseListResult(params, 2);
		Pagination page = getPageInfo(params);

		// 组装返回数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", leaveReportList);
		resultMap.put("page", page);
		return resultMap;
	}
	
	@Override
	public HSSFWorkbook exportFlowVacationExcel(Map<String, Object> params) {
		// 获取报销数据列表
        List<FlowVacation> flowVacationList = getFlowVacationListExport(params);
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("createDate", "时间");
        headers.put("userName", "姓名");
        headers.put("deptName", "部门");
        headers.put("vacationTypeStr", "请假申请");
        headers.put("period", "请假天数");
        headers.put("startDate", "开始时间");
		headers.put("endDate", "结束时间");
        headers.put("recipient", "审批人");
        headers.put("stepDesc", "状态");
        HSSFWorkbook book = ExcelUtil.exportExcel("请假表("+DateUtil.getCurDay()+")", headers, flowVacationList, params);
        return book;
	}
	
	private List<FlowVacation> getFlowVacationListExport(Map<String, Object> params) {

        List<FlowVacation> list = new ArrayList<FlowVacation>();

        // 请求参数中是否有部门。有，查询部门下的用户并组装数据返回。
        setAllUserId(params);
        @SuppressWarnings("unchecked")
        List<User> allUserList = (List<User>) params.get("allUserList");
        if (null != allUserList && allUserList.size() > 0 && !allUserList.equals("null")) {
            // 获取报销数据总数
            int totalCount;
            totalCount = flowVacationDao.getTotalNumByParams(params);
            // 获取分页信息
            params.put("totalCount", totalCount);

            if (totalCount > 0) {
                params.put("startPos", null);
                params.put("pageSize", null);
                list = flowVacationDao.getFlowVacationListByParams(params);
            }

            if (null != list && list.size() > 0) { // 筛选的结果不为空
            	params.put("flowVacationList", list);

                // 以下代码是获取接收人信息，获取所有的send_to_person
                StringBuilder sendToPersons = new StringBuilder();
                list.forEach(flowVacation -> sendToPersons.append(flowVacation.getSendToPerson() + ","));
                // 根据user_id查询所有用户
                String sendToPersonIds = "";
                if (null != sendToPersons && sendToPersons.length() > 0) {
                    sendToPersonIds = sendToPersons.substring(0, sendToPersons.length() - 1);
                    sendToPersonIds = sendToPersonIds.replace("null", "");
                    sendToPersonIds = sendToPersonIds.replace("NaN", "");
                    params.put("sendToPersonIds", sendToPersonIds.split(","));
                }
                
                // 获取接收人信息
                List<User> recipientInfos = userDao.selectUserListById(params);
                // 将接收人姓名List转换为map(Java8支持用stream和 lambda能够实现List<V>转换为Map<K, V>)
                Map<Long, String> recipientInfoMap = recipientInfos.stream()
                        .collect(Collectors.toMap(User::getId, User::getName));
                // 组装报销数据
                for (FlowVacation fr : list) {
                	// 将收件人姓名组装到list中去
                    String legalSentToPerson = fr.getSendToPerson().replace("NaN", "").replace("null", "");
                    fr.setSendToPerson(legalSentToPerson);
                    String[] sendToPersonArray = fr.getSendToPerson().split(",");
                    if (null != sendToPersonArray && sendToPersonArray.length > 0) {
                        String recipientName = "";
                        for (String str : sendToPersonArray) {
                            if (!StringUtils.isEmpty(str)) {
                                recipientName += recipientInfoMap.get(Long.parseLong(str)) + ",";
                            }
                        }
                        if (null != recipientName && recipientName.length() > 0) {
                            recipientName = recipientName.substring(0, recipientName.length() - 1);
                        }
                        fr.setRecipient(recipientName);
                    }
                    // 报销类型枚举
                    String vacationTypeDesc = LeaveTypeEnum.getDescOfValue(fr.getVacationType()).getDesc();
                    fr.setVacationTypeStr(vacationTypeDesc);
                    // 状态枚举
                    String stepDesc = StepEnum.getDescOfValue(fr.getStep()).getDesc();
                    fr.setStepDesc(stepDesc);
                    // 日期处理一下
                    fr.setCreateDate(fr.getCreateDate());
                }
            }
        }

        return list;
    }

	public static void main(String[] args) {
		List<Long> l = new ArrayList<>();
		l.add(23l);
		l.add(23l);
		l.add(23l);
		System.out.println(l);
		String string = l.toString();
		 String substring = string.substring(0,string.length()-1);
		 String [] s= {string};
		 String [] s2= {substring};
		System.out.println(string);
		System.out.println(substring);
		for (String string2 : s) {
			System.out.println(string2);
		}
		for (String string2 : s2) {
			System.out.println(string2);
		}
		
		String i = "xx,xx,xx";
	
	}
}
