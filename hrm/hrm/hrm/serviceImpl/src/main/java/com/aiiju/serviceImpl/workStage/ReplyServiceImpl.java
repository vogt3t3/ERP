package com.aiiju.serviceImpl.workStage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.workStage.FlowInfo;
import com.aiiju.bean.oa.workStage.ReceivedReply;
import com.aiiju.bean.oa.workStage.Reply;
import com.aiiju.bean.oa.workStage.ReplyLog;
import com.aiiju.bean.oa.workStage.TaskInfo;
import com.aiiju.bean.oa.workStage.WorkReport;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IFlowInfoDao;
import com.aiiju.dao.oa.workStage.IMsgDao;
import com.aiiju.dao.oa.workStage.IReceivedReplyDao;
import com.aiiju.dao.oa.workStage.IReplyDao;
import com.aiiju.dao.oa.workStage.IReplyLogDao;
import com.aiiju.dao.oa.workStage.ITaskInfoDao;
import com.aiiju.dao.oa.workStage.IWorkReportDao;
import com.aiiju.service.workStage.IReplyService;
import com.aiiju.util.business.PYUtil;
import com.aiiju.util.business.PYUtil.Type;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.enums.SceneTypeEnum;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.pushMessage.PushUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @ClassName: ReplyServiceImpl
 * @Description: 回复业务实现层
 * @author 哪吒
 * @date 2017年1月3日 上午9:41:04
 *
 */

@Service("replyService")
public class ReplyServiceImpl implements IReplyService {

	private static Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);

	@Autowired
	private IReplyDao replyDao;

	@Autowired
	private IReplyLogDao replyLogDao;

	@Autowired
	private IMsgDao msgDao;
	
	@Autowired
	private ITaskInfoDao taskInfoDao;
	
	@Autowired
	private IFlowInfoDao flowInfoDao;
	
	@Autowired
	private IWorkReportDao workReportDao;
	
	@Autowired
	private ICommonPageDao cpDao;
	@Autowired
	private IReceivedReplyDao receivedReplyDao;
	@Autowired
	private IUserDao userDao;

	@Override
	public Map<String, Object> getUnreadReplyCount(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取未读回复的数量
		int totalCount = 0;
		try {
			totalCount = replyLogDao.getUnreadReplyCount(params);
		} catch (Exception e) {
			logger.info("获取未读回复的总数失败:" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取未读回复的总数失败");
		}
		
		resultMap.put("result", totalCount);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Map<String, Object> getReplyList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Reply> replies = new ArrayList<Reply>();
		// 获取用户收到的回复列表总数
		int totalCount = 0;
		try {
			totalCount = replyLogDao.getReplyListCount(params);
		} catch (Exception e) {
			logger.info("获取用户收到的回复列表总数失败:" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取用户收到的回复列表总数失败");
		}
		// 此处每页默认显示10条数据（需求定义的）,页数由前端传
		params.put("totalCount", totalCount);
		if (StringUtils.equals("", String.valueOf(params.get("pageSize")))) {
			params.put("pageSize", "10");
		}
		Pagination page = new Pagination(totalCount, String.valueOf(params.get("pageNum")),
				String.valueOf(params.get("pageSize")));

		if (totalCount > 0) {
			params.put("startPos", page.getStartPos());
			try {
				replies = replyDao.getReplyList(params);
			} catch (Exception e) {
				logger.info("获取用户收到的回复列表失败:" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "获取用户收到的回复列表失败");
			}
			
			for (Reply reply: replies) {
				// TODO 设置被回复的内容
				if (null == reply.getReplyPid() || 0 == reply.getReplyPid().intValue()) {
					// TODO 获取业务内容
					if (StringUtils.equals("1", reply.getSceneType().toString())) { // 工作汇报
						// 缺一个根据id获取工作汇报的方法
						params.put("id", reply.getPid());
						WorkReport workReport =  workReportDao.getWorkReportById(params);
//						Object obj = workReport.getContent();
//						Map<String, Object> totalContent = (Map<String, Object>) obj;
//						String content = (String) totalContent.get("play_con");
						Object object = JSON.parse(workReport.getContent().toString());
						Map<String, Object> map = (Map<String, Object>) object;
						reply.setReceiveContent(String.valueOf(map.get("play_con")));
					} else if (StringUtils.equals("2", reply.getSceneType().toString())) { // 任务
						TaskInfo taskInfo = taskInfoDao.selectTaskInfoId(reply.getPid().intValue());
						reply.setReceiveContent(taskInfo.getContent());
					} else if (StringUtils.equals("3", reply.getSceneType().toString())) { // 审批
						params.put("flowId", reply.getPid());
						FlowInfo flowInfo = flowInfoDao.getFlowInfoByParams(params);
						reply.setReceiveContent(flowInfo.getContent());
					} else {
						throw new BizException(BizExceptionMessage.PARAM_ERROR, "无此业务场景");
					}
				} else {
					// TODO 获取被回复内容
					params.put("replyPid", reply.getReplyPid());
					Reply receiveReply = replyDao.getReplyById(params);
					reply.setReceiveContent(receiveReply.getReplyCon());
				}
				// 设置用户名简拼和用户头像
				Map<String,Object> fileParam = new HashMap<String,Object>();
				fileParam.put("fId",reply.getUserId());
				fileParam.put("businessId",5);//5 = 头像
				List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
				if(fil.size() > 0){
					reply.setLogo(fil.get(0).getUrl());
				}else{
					PYUtil pyUtil = new PYUtil();
					try {
						String pyName = pyUtil.toPinYin(reply.getUserName().substring(reply.getUserName().length()-1, reply.getUserName().length()), "", Type.UPPERCASE);
						reply.setLastLetter(pyName.substring(0,1));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
						throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
					}
				}
			}
		}

		// 更新所有的状态为已读（zds_read_log表和zds_msg表is_read字段），因为此处的操作，所以整个方法加事务控制
//		updateStatus(params);

		resultMap.put("result", replies);
		resultMap.put("page", page);
		return resultMap;
	}
	
	@Transactional
	@Override
	public Map<String, Object> batchUpdateReadStatus(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 批量更新状态
		String replyIds = params.get("replyIds")==null?null:params.get("replyIds").toString();
		String userId = params.get("userId")==null?null:params.get("userId").toString();
		
		// replyStatus :修改回复的状态标记   1.已读   2.删除  3.撤回
		String replyStatus = params.get("replyStatus")==null?null:params.get("replyStatus").toString();
		// 需要修改的回复ID
		String[] replyIdArray = null;
		if(StringUtils.isEmpty(userId)){
			throw new BizException("1", "缺少userId");
		}
		if(StringUtils.isEmpty(replyStatus)){
			throw new BizException("1", "缺少需要修改的回复状态参数replyStatus");
		}
		//如果没传 replyIdArray 则修改全部
		try {
			if(!StringUtils.isEmpty(replyIds)){
				replyIdArray = replyIds.split(",");
				params.put("replyIdArray", replyIdArray);
			}
			
			
			replyLogDao.batchUpdateReplyLogStatus(params);
			if(!replyStatus.equals("3")){
				msgDao.batchUpdateMsg(params);
			}
		} catch (Exception e){
			throw new BizException(BizExceptionMessage.DB_ERROR, "更新状态失败");
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getReplyByPid(Map<String, Object> params) { // 请求参数：pid，sceneType
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Reply> replies = new ArrayList<Reply>();
		// 根据companyId,pid获取回复记录列表
		try {
			replies = replyDao.getReplyListByPid(params);
		} catch (Exception e) {
			logger.info("获取回复记录列表失败:" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "获取回复记录列表失败");
		}
		
		for (Reply reply: replies) {
			Map<String,Object> fileParamFoReply = new HashMap<String,Object>();
			fileParamFoReply.put("fId",reply.getUserId());
			fileParamFoReply.put("businessId",5);//5 = 头像
			List<FileInfo> fil = cpDao.getFileInfoList(fileParamFoReply);
			if(fil.size() > 0){
				reply.setLogo(fil.get(0).getUrl());
			}else{
				PYUtil pyUtil = new PYUtil();
				try {
					String pyName = pyUtil.toPinYin(reply.getUserName().substring(reply.getUserName().length()-1, reply.getUserName().length()), "", Type.UPPERCASE);
					reply.setLastLetter(pyName.substring(0,1));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
				}
			}
		}
		
		// TODO 调用其他模块的数据访问层
		String sceneType = String.valueOf(params.get("sceneType"));
		if (StringUtils.isNotEmpty(sceneType)) {
			if (StringUtils.equals("1", sceneType)) { // 工作汇报
				// 缺一个根据id获取工作汇报的方法
				params.put("id", params.get("pid"));
				WorkReport workReport =  workReportDao.getWorkReportById(params);
				Object obj = JSON.parse(workReport.getContent().toString());
				workReport.setContent(obj);
				workReport.setSceneType(Integer.valueOf(sceneType));
				Map<String,Object> fileParam = new HashMap<String,Object>();
				fileParam.put("fId",workReport.getUserId());
				fileParam.put("businessId",5);//5 = 头像
				List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
				if(fil.size() > 0){
					workReport.setLogo(fil.get(0).getUrl());
				}else{
					PYUtil pyUtil = new PYUtil();
					try {
						String pyName = pyUtil.toPinYin(workReport.getUserName().substring(workReport.getUserName().length()-1, workReport.getUserName().length()), "", Type.UPPERCASE);
						workReport.setLastLetter(pyName.substring(0,1));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
						throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
					}
				}
				resultMap.put("detail", workReport);
			} else if (StringUtils.equals("2", sceneType)) { // 任务
				TaskInfo taskInfo = taskInfoDao.selectTaskInfoId(Integer.parseInt(String.valueOf(params.get("pid"))));
				taskInfo.setPersons(taskInfo.getSendToPersonName());
				taskInfo.setSceneType(Integer.valueOf(sceneType));
				Map<String,Object> fileParam = new HashMap<String,Object>();
				fileParam.put("fId",taskInfo.getUserId());
				fileParam.put("businessId",5);//5 = 头像
				List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
				if(fil.size() > 0){
					taskInfo.setLogo(fil.get(0).getUrl());
				}else{
					PYUtil pyUtil = new PYUtil();
					try {
						String pyName = pyUtil.toPinYin(taskInfo.getUserName().substring(taskInfo.getUserName().length()-1, taskInfo.getUserName().length()), "", Type.UPPERCASE);
						taskInfo.setLastLetter(pyName.substring(0,1));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
						throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
					}
				}
				resultMap.put("detail", taskInfo);
			} else if (StringUtils.equals("3", sceneType)) { // 审批
				params.put("flowId", params.get("pid"));
				FlowInfo flowInfo = flowInfoDao.getFlowInfoByParams(params);
				flowInfo.setSceneType(Integer.valueOf(sceneType));
				Map<String,Object> fileParam = new HashMap<String,Object>();
				fileParam.put("fId",flowInfo.getUserId());
				fileParam.put("businessId",5);//5 = 头像
				List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
				if(fil.size() > 0){
					flowInfo.setLogo(fil.get(0).getUrl());
				}else{
					PYUtil pyUtil = new PYUtil();
					try {
						String pyName = pyUtil.toPinYin(flowInfo.getUserName().substring(flowInfo.getUserName().length()-1, flowInfo.getUserName().length()), "", Type.UPPERCASE);
						flowInfo.setLastLetter(pyName.substring(0,1));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
						throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
					}
				}
				resultMap.put("detail", flowInfo);
			} else {
				throw new BizException(BizExceptionMessage.PARAM_ERROR, "无此业务场景");
			}
		} else {
			throw new BizException(BizExceptionMessage.PARAM_ERROR, "请求参数sceneType为空");
		}

		resultMap.put("result", replies);
		return resultMap;
	}

	@Override
	public Map<String, Object> getReplyListByScene(Map<String, Object> params) { // 请求参数：id(汇报，任务，审批),sceneType(1-汇报，2-任务；3-审批)
		Map<String, Object> resultMap = new HashMap<String, Object>();
		params.put("pid", params.get("id"));
		List<Reply> replies = new ArrayList<Reply>();
		try {
			replies = replyDao.getReplyListByPid(params);
		} catch (Exception e) {
			logger.info("根据业务id获取回复记录列表失败:" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "根据业务id获取回复记录列表失败");
		}
		
		for (Reply reply: replies) {
			Map<String,Object> fileParam = new HashMap<String,Object>();
			fileParam.put("fId",reply.getUserId());
			fileParam.put("businessId",5);//5 = 头像
			List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
			if(fil.size() > 0){
				reply.setLogo(fil.get(0).getUrl());
			}else{
				PYUtil pyUtil = new PYUtil();
				try {
					String pyName = pyUtil.toPinYin(reply.getUserName().substring(reply.getUserName().length()-1, reply.getUserName().length()), "", Type.UPPERCASE);
					reply.setLastLetter(pyName.substring(0,1));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
				}
			}
		}
		String commUser = "{ id:\""+params.get("userId")+"\",name:\""+params.get("userName")+"\"}";
		JSONObject commUserObject = JSONObject.parseObject(commUser);
		resultMap.put("result", replies);
		resultMap.put("commUser", commUserObject);
		 
		
		
		return resultMap;
	}

	// 这个接口数据由各自业务模块自己调用dao层组装，暂时保留，后续如果确定不用此处开发可以删掉
	@Override
	public Map<String, Object> getReplyCountByScene(Map<String, Object> params) { // 请求参数：id(汇报，任务，审批),sceneType(1-汇报，2-任务；3-审批)
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}

	@Transactional
	@Override
	public Map<String, Object> addReply(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 1、zds_reply新增数据，没什么需要注意的，参数传进来就好
		addReplyInfo(params);
		// 2、zds_reply_log新增数据
		addReplyLogInfo(params);
		// 3、zds_msg新增数据
		addMsgInfo(params);
		
		String pushTitle =null;
		String pushUserIds=params.get("receiveIds").toString().substring(1, params.get("receiveIds").toString().length()-1).replace(" ", "");
		int phpType = 2 ;
		//说明对回复发起回复
		if(params.get("replyPid")!=null&&!params.get("replyPid").toString().trim().equals("")
											&&!params.get("replyPid").toString().equals("0")){
			Map<String,Object> userMap = new HashMap<>();
			userMap.put("id",params.get("receiveUid"));
			userMap.put("companyId",params.get("companyId"));
			User user = userDao.seleteById(userMap);
			 pushTitle = params.get("userName").toString()+"对"+user.getName()+"的回复发表了回复。";
			if(params.get("sceneType").toString().equals("2")){
				phpType =6 ;
			}
			//对情景直接回复
		}else {
			Map<String,Object> userMap = new HashMap<>();
			userMap.put("id",params.get("pidUid"));
			userMap.put("companyId",params.get("companyId"));
			User user = userDao.seleteById(userMap);
			pushTitle = params.get("userName").toString()+"对"+user.getName()+"的"+
			(params.get("sceneType").toString().equals("1")?"工作汇报":"任务")+"发表了回复。";
			if(params.get("sceneType").toString().equals("2")){
				phpType =6 ;
			}
		}
				
		
		
		params.put("pushTitle",pushTitle);
		params.put("pushUserIds",pushUserIds);
		PushUtil.sendNoticesByPhp(params,5,phpType);
		
		
		
		
		
		
		return resultMap;
	}

	private void addReplyInfo(Map<String, Object> params) {
		// 如果是任意用户对该业务的直接回复，那么reply_id为0或为空均可；如果是对回复内容的回复，那么这两个是必须有确定值的
		Reply reply = new Reply();
		String replyPid = String.valueOf(params.get("replyPid"));
		String receiveUid = String.valueOf(params.get("receiveUid"));
		if (StringUtils.isEmpty(replyPid)) {
			replyPid = "0";
		}
		if (StringUtils.equals("0", replyPid)) { // 此处根据replyPid来确定receiveUid的值，是因为receiveUid的值在zds_reply_log中要用到
			receiveUid = "0";
		}
		reply.setCompanyId(Long.valueOf(String.valueOf(params.get("companyId"))));
		reply.setSceneType(Integer.valueOf(String.valueOf(params.get("sceneType"))));
		reply.setUserId(Long.valueOf(String.valueOf(params.get("userId"))));
		reply.setReplyPid(Long.valueOf(replyPid));
		reply.setPid(Long.valueOf(String.valueOf(params.get("pid"))));
		reply.setPidUid(Long.valueOf(String.valueOf(params.get("pidUid"))));
		reply.setReceiveUid(Long.valueOf(receiveUid));
		reply.setReplyCon(String.valueOf(params.get("replyCon")));
		reply.setIsAgree(0);

		try {
			replyDao.addReplyInfo(reply);
			params.put("replyId", reply.getId());
		} catch (Exception e) {
			logger.info("新增回复详细失败：" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR, "新增回复详细失败");
		}
	}

	private void addReplyLogInfo(Map<String, Object> params) {
		// 根据pid和pid_uid查询user_id
		List<Reply> replies = null;
		try {
			replies = replyDao.getReplyListByPid(params);
		} catch (Exception e) {
			logger.info("根据pid查询用户ID失败：" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		// 组装zds_reply_log新增时需要的receive_id
		List<Long> receiveIds = new ArrayList<Long>();
		if (!StringUtils.equals(String.valueOf(params.get("userId")), String.valueOf(params.get("pidUid")))) { // 发送者不是汇报/任务/审批的发起人
			receiveIds.add(Long.valueOf(String.valueOf(params.get("pidUid"))));
		}
		if (null != replies && !replies.isEmpty()) {
			replies.stream().filter(reply -> !reply.getUserId().equals(Long.valueOf(String.valueOf(params.get("userId")))))
					.map(Reply::getUserId).distinct().forEach(receiveId -> receiveIds.add(receiveId));
		}
		params.put("receiveIds", receiveIds.stream().distinct().collect(Collectors.toList()));
		// zds_reply_log批量新增
		if (null != receiveIds && !receiveIds.isEmpty()) {
			try {
				replyLogDao.batchAddReplyLogInfo(params);
			} catch (Exception e) {
				logger.info("批量新增回复日志记录失败：" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "批量新增回复日志记录失败");
			}
		}
	}

	private void addMsgInfo(Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<Long> receiveIds = (List<Long>) params.get("receiveIds");
		if (null != receiveIds && !receiveIds.isEmpty()) {
			// 批量新增zds_msg中数据，author_id即当前登录用户的user_id,user_id是receiveIds集合，scene_id即replyId
			params.put("authorId", params.get("userId"));
			params.put("sceneId", params.get("replyId"));
			// 根据线上新增数据观察，回复业务中sub_type为3
			params.put("subType", 3);
			try {
				msgDao.batchAddMsgInfo(params);
			} catch (Exception e) {
				logger.info("批量新增消息日志记录失败：" + e.getMessage());
				throw new BizException(BizExceptionMessage.DB_ERROR, "批量新增消息日志记录失败");
			}
		}
	}

	@Transactional
	@Override
	public Map<String, Object> cancelReply(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 更新zds_reply和zds_reply_log的is_withdraw字段
		try {
			params.put("isWithdraw", 1);
			replyDao.updateReplyStatusById(params);
			params.put("replyId", params.get("id"));
			replyLogDao.updateReplyLogStatusByReplyId(params);
		} catch (Exception e) {
			logger.info("更新撤回状态失败：" + e.getMessage());
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		return resultMap;
	}
		
	@Override
	
	public Map<String, Object> getReceivedReplyList(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String pageNum = params.get("pageNum")==null?null:params.get("pageNum").toString();
		String pageSize = params.get("pageSize")==null?null:params.get("pageSize").toString();
		Pagination pt =null;
		int count ;
		List<ReceivedReply> receivedReplyList = null;
		
		try {
			
		
			if(!StringUtils.isEmpty(pageNum)||!StringUtils.isEmpty(pageSize)){
				count = receivedReplyDao.getReceivedReplyListCount(params);
				pt = new Pagination(count, pageNum, pageSize);
				// 添加条件
				params.put("pageSize", pt.getPageSize());
				params.put("startRow", pt.getStartPos());
			}
			 receivedReplyList = receivedReplyDao.getReceivedReplyList(params);
			for (ReceivedReply receivedReply : receivedReplyList) {
				
				
				//该回复是对情景下的回复发起回复，那么把SceneInfo设置null
				//否则，把BeReplyInfo设置为NULL  因为数据库默认回复的回复的ID为0 ，转JSON还是会有该对象
				if(receivedReply.getBeReplyInfo().getId()==null||receivedReply.getBeReplyInfo().getId()==0l){
					receivedReply.setBeReplyInfo(null);
					
					receivedReply.setCreateTime(DateUtil.relativeDateFormat(receivedReply.getCreateTime(), 1));
					if(receivedReply.getSceneType()==1){//工作汇报
						receivedReply.setInfoType(SceneTypeEnum.getValue(receivedReply.getSceneInfo().getSubType()));
						String content = receivedReply.getSceneInfo().getContent();
						JSONObject parseObject = JSONObject.parseObject(content);
						String string = parseObject.getString("sum_con");
						receivedReply.getSceneInfo().setContent(string);
					}
					else if(receivedReply.getSceneType()==3){
						if(receivedReply.getSceneInfo().getSubType()==2){
						receivedReply.setInfoType("请假申请");
						}else {
							receivedReply.setInfoType("报销申请");
							
							//因APP要求，区分请假和报销
							if(params.get("from")!=null&&params.get("from").toString().equals("APP")){
								receivedReply.setSceneType(4);
							}
						}
					}else{
						receivedReply.setInfoType("任务");
					}
				}else{
					receivedReply.setSceneInfo(null);
					receivedReply.setInfoType("回复");
				}
			}
			resultMap.put("receivedReplyList", receivedReplyList);
			resultMap.put("pt", pt);
		} catch (Exception e){
			throw new BizException("2", "查询失败");
		}
		
		
		return resultMap;
	}
	
	
	public static void main(String[] args) {
		List<Long>  l = new ArrayList<>();
		l.add(213l);
		l.add(23l);
		l.add(22213l);
		System.out.println(l.get(2));
		String string = l.toString();
		System.out.println(string);
		String substring = string.substring(1, string.length()-1).replace(" ", "");
		System.out.println(substring);
		String[] split = substring.split(",");
		for (String string2 : split) {
			System.out.println(string2);
		}
	}
	
}
