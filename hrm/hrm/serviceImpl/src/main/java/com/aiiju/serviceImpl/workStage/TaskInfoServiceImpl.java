package com.aiiju.serviceImpl.workStage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.workStage.HistoryLog;
import com.aiiju.bean.oa.workStage.TaskInfo;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IHistoryLogDao;
import com.aiiju.dao.oa.workStage.IReplyDao;
import com.aiiju.dao.oa.workStage.ITaskInfoDao;
import com.aiiju.service.workStage.ITaskInfoService;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.enums.LogStatusStep;
import com.aiiju.util.enums.SceneTypeEnum;
import com.aiiju.util.enums.TaskStep;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.http.StringUtils;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.pushMessage.PushUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Service("taskInfoService")
@Transactional
public class TaskInfoServiceImpl implements ITaskInfoService {

	private static Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
	@Autowired
	private ITaskInfoDao dao;
	@Autowired
	private IHistoryLogDao historyDao;
	@Autowired
	private IReplyDao replyDao;
	@Autowired
	private IUserDao userDao;
	@Override
	public Map<String, Object> getTaskInfos(Map<String, Object> map) {
		
		logger.info("=============任务列表start===============");
		Map<String, Object> taskMap= new HashMap<String, Object>();
		//任务发布人
		String userId=map.get("userId").toString();
		//公司
		String companyId=map.get("companyId").toString();
		if(map.get("pageNum")==null&&map.get("pageSize")==null){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"分页为空");
		}
		if(map.get("type")==null){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"参数为空");
		}
		int pageNum = Integer.parseInt(map.get("pageNum").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		String type=map.get("type").toString();
		List<TaskInfo> myTaskList = null;
		Pagination pt=null;
		
		if("2".equals(type)){
			logger.info("=============我发送的任务列表start===============");
			int count = dao.countTask(map);// 查询总数
			pt = new Pagination(count, pageNum, pageSize);
			// 添加条件
			map.put("pageSize", pageSize);
			map.put("startRow", pt.getStartPos());
			//获取自己发布的任务
			map.put("userId", userId);
			myTaskList=dao.getTaskInfoByMe(map);
		}else if ("1".equals(type)) {
			logger.info("=============我收到的任务列表start===============");
			//获取收到的任务
			Map<String, Object> getTask= new HashMap<String, Object>();
//			if(map.get("from")!=null&&map.get("from").toString().equals("APP")){
//				getTask.put("from","APP");
//			}
			getTask.put("userId", map.get("userId").toString());
			getTask.put("isWithdraw", 0);//撤回的不显示
			getTask.put("receiveUid", userId);
			getTask.put("companyId", companyId);
			getTask.put("type", 1);
			int count = dao.countTaskInfoByMe(getTask);// 查询总数
			pt = new Pagination(count, pageNum, pageSize);
			// 添加条件
			getTask.put("pageSize", pageSize);
			getTask.put("startRow", pt.getStartPos());

			myTaskList=dao.selectTaskInfoByMe(getTask);
			
		
		}else if ("3".equals(type)) {
			logger.info("=============待我执行的任务列表start===============");
			//获取待执行任务(处理中的)
			Map<String, Object> tasking= new HashMap<String, Object>();
			tasking.put("type", 3);
			tasking.put("receiveUid", userId);
			tasking.put("companyId", companyId);
			tasking.put("isWithdraw", 0);//撤回的不显示
			int count = dao.countTaskInfoByMe(tasking);// 查询总数
			pt = new Pagination(count, pageNum, pageSize);
			// 添加条件
			tasking.put("pageSize", pageSize);
			tasking.put("startRow", pt.getStartPos());
			myTaskList=dao.selectTaskInfoByMe(tasking);
		}
		if(myTaskList==null||myTaskList.isEmpty()){
			//throw new BizException(BizExceptionMessage.TASK_IS_NULL,"没有任务");
			taskMap.put("task", myTaskList);
			taskMap.put("page", pt);
		}else{
			Map<String, Object> pid= new HashMap<String, Object>();
			pid.put("sceneType", 2);
			pid.put("companyId", companyId);
			Map<String, Object> replyCount= new HashMap<String, Object>();
			logger.info("=============回复总数start"+myTaskList.size()+"===============");
			
			// 获取用户收到的回复总个数(以一个列表集的形式返回)
			List<TaskInfo> replyNums = dao.getTaskReplyNum(map);
			for (TaskInfo taskInfo : myTaskList) {
				pid.put("pid", taskInfo.getId());
				if(replyNums!=null&&replyNums.size()>0){
					for (TaskInfo taskInfo2 : replyNums) {
						if(taskInfo.getId().intValue()==taskInfo2.getId().intValue()){
							taskInfo.setReplyNum(taskInfo2.getReplyNum());
							continue;
						}
					}
				}
				//计算回复总数
				replyCount.put(taskInfo.getId().toString(), replyDao.getReplyListCountByPid(pid));
				
				//判断是过期
				taskInfo.setIsExpired(DateUtil.getTimeMillis(taskInfo.getExpireDate(), null)>=(System.currentTimeMillis() )?"0":"1");
				//判断是否在处理中。然后如果超过结束时间吧Step改为4，因为定时任务是每天1点进行，可能会有时间的误差
				taskInfo.setStep((taskInfo.getStep()!=1&&taskInfo.getStep()!=0)?taskInfo.getStep():
										(DateUtil.getTimeMillis(taskInfo.getExpireDate(), null)>=(System.currentTimeMillis())?taskInfo.getStep():
											Integer.parseInt(TaskStep.OVERDUE.getValue())));
			}
			taskMap.put("task", myTaskList);
			taskMap.put("replyCount", replyCount);
			taskMap.put("page", pt);
		}
		logger.info("=============查询结束===============");
		return taskMap;
	}

	@Override
	@Transactional
	public boolean withdrawTaskInfos(Map<String, Object> map) {
		logger.info("=============撤回任务start===============");
		String id = map.get("id")==null?null:map.get("id").toString();
		String userId = map.get("userId")==null?null:map.get("userId").toString();
//		String taskStep = map.get("taskStep")==null?null:map.get("taskStep").toString();
//		//任务的发起者id
//		String taskSenderId = map.get("taskSenderId")==null?null:map.get("taskSenderId").toString();
		//任务id
		if(StringUtils.isBlank(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务id为空");
		}
//		if(StringUtils.isBlank(taskStep)){
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务状态为空");
//		}
//		//1.只有任务的发起者才可以撤回
//		if(!userId.equals(taskSenderId)){
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"对不起,您不是任务的发起者,无法进行此操作");
//		}
		//2.判断任务的状态,如果为已完成或已过期则不允许撤回
//		if(TaskStep.COMPLETE.getValue().equals(taskStep)){
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务已完成,不支持撤回");
//		}else if(TaskStep.OVERDUE.getValue().equals(taskStep)){
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务已过期,不支持撤回");
//		}
		TaskInfo record = new TaskInfo();
		record.setId(Integer.parseInt(map.get("id").toString()));
		record.setStep(Integer.parseInt(TaskStep.DISAGREE.getValue()));
		record.setUserId(Long.valueOf(userId));
		record.setIsWithdraw(1);
		Map<String,Object> updateMap = new HashMap<>();
		updateMap.put("id",id);
		updateMap.put("step",Integer.parseInt(TaskStep.DISAGREE.getValue()));
		updateMap.put("isWithdraw",1);
		updateMap.put("userIdCon",userId);//发起人作条件
		//当SQL中UPDATE条件找到记录,并有修改返回值，说明当前用户可以撤回任务
		int modifiedValue = dao.updateTaskInfo(updateMap);//更新为撤回
		if(modifiedValue<=0){
			throw new BizException("1","撤回失败");
		}
		//将记录表设为删除
		HistoryLog historyLog = new HistoryLog();
		historyLog.setRefId(Long.parseLong(map.get("id").toString()));
		historyLog.setIsDel(1);
		historyDao.updateHistoryLog(historyLog);
		logger.info("=============撤回任务end===============");
		return true;
	}

	@Override
	@Transactional
	public Map<String, Object> addTaskInfos(Map<String, Object> map) {
		logger.info("=============添加任务start===============");
		HashMap<String, Object> newmap = new HashMap<>();
		
		
		if(map.get("taskInfo")==null){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"参数为空");
		}
		//任务发布人
		String userId=map.get("userId").toString();
		//公司
		String companyId=map.get("companyId").toString();
		//任务
		String taskInfos=map.get("taskInfo").toString();
		TaskInfo taskInfo=JSONObject.parseObject(taskInfos, TaskInfo.class);
		if(taskInfo.getSendToPerson().toString().equals(userId)){
			throw new BizException(BizExceptionMessage.TASK_IS_EXIST,"不能给自己发布任务");
		}	
		taskInfo.setCompanyId(Long.parseLong(companyId));
		taskInfo.setUserId(Long.parseLong(userId));
		//任务的初始状态为：0:待处理
		taskInfo.setStep(Integer.parseInt(TaskStep.PENDING.getValue()));
		dao.addTaskInfo(taskInfo);
		
		//插入任务执行记录
		List<HistoryLog> historyLogList= new ArrayList<HistoryLog>();
		HistoryLog historyLog= new HistoryLog();
		historyLog.setCompanyId(Long.parseLong(companyId));
		historyLog.setRefId(Long.parseLong(taskInfo.getId().toString()));
		historyLog.setReceiveUid(taskInfo.getSendToPerson());
		historyLog.setSceneType(2);
		historyLogList.add(historyLog);
			
		historyDao.addHistoryLog(historyLogList);
		//返回任务ID
		 newmap.put("id",taskInfo.getId() );
		logger.info("=============添加任务end===============");
		
		
		String pushTitle =  map.get("userName").toString()+"向您发布了任务。";
		map.put("pushTitle",pushTitle);
		map.put("pushUserIds",taskInfo.getSendToPerson().toString());
		PushUtil.sendNoticesByPhp(map,2,1);
		
		
		return newmap;
	}

	@Override
	@Transactional
	public boolean changeTaskInfos(Map<String, Object> map) {
		logger.info("=============转发任务start===============");
		//empId转发接受任务人的id  id任务id
		if(map.get("id")==null&&map.get("empId")==null){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"参数为空");
		}
		String empId=map.get("empId").toString();
		String userId=map.get("userId").toString();
		
		if(empId.equals(userId)){
			throw new BizException(BizExceptionMessage.TASK_IS_EXIST,"不能重复转发自己");
		}

		Map<String, Object> params = new HashMap<String, Object>();
//		companyId,refId,sendToPersonId这些参数仅作为where条件
		params.put("companyId", map.get("companyId").toString());
		params.put("refId", map.get("id").toString());
		params.put("receiveUid", userId);
		params.put("sceneType", SceneTypeEnum.TASK.getValue());
		params.put("isForwardCon", 0);//作条件
		params.put("isForward", 1);//已转发
		params.put("isRead", 1);
		historyDao.updateTaskHistoryByMap(params);
		
		//2.修改任务表的执行人id  新增by小辉 2017.03.28
		//当SQL中UPDATE条件找到记录,并有修改返回值，说明当前用户可以转发
		Map<String,Object> updateMap = new HashMap<>();
		updateMap.put("id",map.get("id").toString());
		updateMap.put("step",Integer.parseInt(TaskStep.PENDING.getValue()));
		updateMap.put("sendToPerson",empId);
		updateMap.put("sendToPersonCon",userId);//执行人作条件
		updateMap.put("isforward","isforward");//转发标识
		int modifiedValue =dao.updateTaskInfo(updateMap);
		if(modifiedValue<=0){
			throw new BizException("1","转发任务失败");
		}
		
		//3.创建新的一条任务执行人记录    修改by小辉 2017.03.28
		HistoryLog history= new HistoryLog();
		history.setCompanyId(Long.parseLong(map.get("companyId").toString()));
		history.setRefId(Long.parseLong(map.get("id").toString()));
		history.setReceiveUid(Long.parseLong(empId));
		history.setSceneType(SceneTypeEnum.TASK.getValue());//场景类型为 2:任务
		history.setIsForward(0);//未转发
		historyDao.insertHistorySeclective(history);
		logger.info("=============转发任务end===============");
		
		String pushTitle =  map.get("userName").toString()+"把任务转发给了您。";
		map.put("pushTitle",pushTitle);
		map.put("pushUserIds",empId);
		PushUtil.sendNoticesByPhp(map,2,1);
		
		TaskInfo info=dao.selectTaskInfoId(Integer.valueOf(map.get("id").toString()));
		pushTitle =  map.get("userName").toString()+"转发您的任务。";
		map.put("pushTitle",pushTitle);
		map.put("pushUserIds",info.getUserId().toString());
		PushUtil.sendNoticesByPhp(map,2,1);
		
		return true;
	}

	@Override
	@Transactional
	public Map<String, Object> getTaskInfoById(Map<String, Object> map) {
		String from =map.get("from")==null?null:map.get("from").toString();
		String userId =map.get("userId")==null?null:map.get("userId").toString();
		Map<String, Object> details= new HashMap<String, Object>();
		int id = Integer.parseInt(map.get("id").toString());
		//获取任务详情
		TaskInfo info=dao.selectTaskInfoId(id);
		Map<String, Object> taskMap= new HashMap<String, Object>();
		
		taskMap.put("refId", id);
		taskMap.put("sceneType", 2);
		taskMap.put("order", "asc");
		List<HistoryLog> historyLogList=historyDao.getHistoryLogs(taskMap);
		//显示任务进程：已完成  新增by小辉 2017.03.28
		for (HistoryLog historyLog : historyLogList) {
			if(historyLog.getIsForward()==0){//未转发的
				historyLog.setForward("未转发");
				//给APP状态值判断任务是否过期；再判断在任务为未转发的是否已读，已读为处理中，未读为待处理
			String status	= DateUtil.getTimeMillis(info.getExpireDate(), null)>=(System.currentTimeMillis())?
					(historyLog.getIsRead().toString().equals("0")?LogStatusStep.PENDING.getValue():LogStatusStep.PROCESSING.getValue())
					:LogStatusStep.OVERDUE.getValue();
				historyLog.setLogStatus(Integer.valueOf(status));
				historyLog.setCreateDate("");
						
			}else if(historyLog.getIsForward()==1){
				historyLog.setForward("已转发");
				historyLog.setLogStatus(Integer.valueOf(LogStatusStep.FORWARDED.getValue()));
			}else if(historyLog.getIsForward()==2){
				historyLog.setForward("已完成");
				historyLog.setLogStatus(Integer.valueOf(LogStatusStep.COMPLETE.getValue()));
			}
			if(historyLog.getIsRead()==0&&historyLog.getReceiveUid().equals(userId)){
				historyLog.setIsRead(1);
				historyDao.updateHistoryLog(historyLog);//设为已读
			}
		}
		//如果是待处理的任务 需要修改状态(此处逻辑放在查看详情里不合理，经与产品讨论决定新开一个接口修改任务状态为处理中)
//		/*if(TaskStep.PENDING.getValue().equals(type)){
//			if(userId.equals(taskExecutorId)){//只有是执行者时才修改为处理中,其他人查看不修改状态
//				info.setStep(Integer.parseInt(TaskStep.PROCESSING.getValue()));
//				dao.updateTaskInfo(info);
//			}
//		}*/
		//APP点详情把任务改为处理中
		if(("APP").equals(from)&&info.getSendToPerson().toString().equals(userId)&&
				info.getStep().toString().equals(LogStatusStep.PENDING.getValue())){ //防止APP重复点详情，SQL为找到符合的数据修改，这里不抛出异常
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("id",id);
			updateMap.put("step",Integer.parseInt(TaskStep.PROCESSING.getValue()));
			updateMap.put("sendToPersonCon",userId);//执行人作条件
			dao.updateTaskInfo(updateMap);
		}
		//判断是否在处理中。然后如果超过结束时间吧Step改为4，因为定时任务是每天1点进行，可能会有时间的误差
		info.setStep(info.getStep()!=1?info.getStep():
			(DateUtil.getTimeMillis(info.getExpireDate(), null)>=(System.currentTimeMillis())?info.getStep():
				Integer.parseInt(TaskStep.OVERDUE.getValue())));
		
		//发起者的查询信息
		Map<String,Object> m = new HashMap<>();
		m.put("id", info.getUserId()) ;
		User sendUser = userDao.seleteById(m);
		if(sendUser!=null){
			info.setDeptName(sendUser.getDeptName());
			info.setPositionName(sendUser.getPositionName()==null?"":sendUser.getPositionName());
		}
		//APP 需要发起者的信息并放到historyLogList过程中，通过info详情直接转换参数放入首位即可
		if(from!=null&&from.equals("APP")){
			HistoryLog  startLog = new HistoryLog();
			startLog.setReceiveUid(info.getUserId());
			startLog.setUserName(info.getUserName());
			startLog.setReceiveLoginUserID(sendUser.getLoginUserId());
			startLog.setLogStatus(Integer.valueOf(LogStatusStep.START.getValue()));
			startLog.setReceiveLogo(info.getSenderLogo());
			startLog.setCreateDate(info.getCreateTime());
			historyLogList.add(0,startLog);
			
			//当任务已撤销，那么在historyLogList过程中，通过info详情直接转换参数放入末位即可
			if(info.getIsWithdraw()!=null &&info.getIsWithdraw().toString().equals("1")){
				HistoryLog  isWithdrawLog = new HistoryLog();
				isWithdrawLog.setReceiveUid(info.getUserId());
				isWithdrawLog.setUserName(info.getUserName());
				isWithdrawLog.setReceiveLoginUserID(sendUser.getLoginUserId());
				isWithdrawLog.setLogStatus(Integer.valueOf(LogStatusStep.RESCINDED.getValue()));
				isWithdrawLog.setReceiveLogo(info.getSenderLogo());
				isWithdrawLog.setCreateDate(info.getUpdateTime());
				historyLogList.add(isWithdrawLog);
			}	
		}
		Map<String, Object> pid= new HashMap<String, Object>();
		pid.put("sceneType", 2);
		pid.put("companyId", map.get("companyId"));
		pid.put("pid", id);
		
		String [] buttonArray = null;
		ArrayList<String> buttonList =  new ArrayList<String>();
			//查看详情的是执行者
			if(map.get("userId").toString().equals(info.getSendToPerson().toString())){
				
				buttonList.add("reply");
				//当任务不是完成或过期状态，发起这可对任务做完成和转发操作
				if(!info.getStep().equals(Integer.parseInt(TaskStep.OVERDUE.getValue()))&&
						!info.getIsWithdraw().toString().equals("1")&&
						!info.getStep().equals(Integer.parseInt(TaskStep.COMPLETE.getValue()))){
					buttonList.add(0,"finish");
					buttonList.add(1,"forward");
				}
				//查看详情的是发起者
			 }else if(map.get("userId").toString().equals(info.getUserId().toString())){
				//当任务不是完成或过期状态，发起这可对任务做撤销操作
				 buttonList.add("reply");
				if(!info.getStep().equals(Integer.parseInt(TaskStep.OVERDUE.getValue()))&&
						!info.getIsWithdraw().toString().equals("1")&&
						!info.getStep().equals(Integer.parseInt(TaskStep.COMPLETE.getValue()))){
					buttonList.add(0,"withdraw");
				}
			}else{//查看详情的是参与者（已转发）
					buttonList.add("reply");
			}
		buttonArray=(String[]) buttonList.toArray(new String[buttonList.size()]);
		//计算回复总数
		details.put("task",info );
		details.put("replyCount", replyDao.getReplyListCountByPid(pid));
		details.put("historyLogList",historyLogList );
		details.put("buttonArray",buttonArray );
		return details;
	}

	@Override
	@Transactional
	public boolean finishTaskInfos(Map<String, Object> map) {
		String userId = map.get("userId")==null?null:map.get("userId").toString();//当前登录用户
		if(map.get("id")==null){
		 	throw new BizException(BizExceptionMessage.EMPTY_ERROR,"参数为空");
		}

				int id = Integer.parseInt(map.get("id").toString());
				//1.修改任务状态为已完成
				//当SQL中UPDATE条件找到记录,并有修改返回值，说明当前用户可以完成任务
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("id",id);
				updateMap.put("step",Integer.parseInt(TaskStep.COMPLETE.getValue()));
				updateMap.put("sendToPersonCon",userId);//执行人作条件
				int modifiedValue =dao.updateTaskInfo(updateMap);
				if(modifiedValue<=0){
					throw new BizException("1","完成任务失败");
				} 
				
				//2.在任务历史记录表中新增一条任务进程：已完成
				HistoryLog history= new HistoryLog();
				history.setCompanyId(Long.parseLong(map.get("companyId").toString()));
				history.setRefId(new Long(id));
				history.setReceiveUid(Long.parseLong(map.get("userId").toString()));
				history.setSceneType(SceneTypeEnum.TASK.getValue());
				history.setIsRead(1);
				history.setIsForward(2);
				historyDao.insertHistorySeclective(history);
				
				TaskInfo info=dao.selectTaskInfoId(id);
				String pushTitle =  map.get("userName").toString()+"完成了您的任务。";
								
				map.put("pushTitle",pushTitle);
				map.put("pushUserIds",info.getUserId().toString());
				PushUtil.sendNoticesByPhp(map,2,1);
					
				
				
				
		
		return true;
	}


	@Override
	public List<TaskInfo> getTaskInfoListByMap(Map<String, Object> map) {
		return dao.selectTaskInfoListByMap(map);
	}

	@Override
	public boolean updateTaskStep(Map<String, Object> map ) {
		dao.updateTaskInfo(map);
		return true;
	}
	@Override
	public boolean startDealTask(Map<String, Object> map) {
		//任务id
		String taskId = map.get("taskId")==null?null:map.get("taskId").toString();
		String userId = map.get("userId")==null?null:map.get("userId").toString();
		//任务状态
		//String taskStep = map.get("taskStep")==null?null:map.get("taskStep").toString();
		if(StringUtils.isBlank(taskId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务id为空");
		}
		if(StringUtils.isBlank(userId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"当前用户Id为空");
		}
//		if(StringUtils.isBlank(taskStep)){
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"任务状态taskStep为空");
//		}
//		if(TaskStep.PENDING.getValue().equals(taskStep)){
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("id",taskId);
			updateMap.put("step",Integer.parseInt(TaskStep.PROCESSING.getValue()));
			updateMap.put("sendToPersonCon",userId);//执行人作条件
			//当SQL中UPDATE条件找到记录,并有修改返回值，说明当前用户可以开始任务
			int modifiedValue =dao.updateTaskInfo(updateMap);
			if(modifiedValue<=0){
				throw new BizException("1","开始任务失败");
			} 
//		}else{
//			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"只有待处理的任务才可以进行开始操作");
//		}
		return true;
	}

	
	

}
