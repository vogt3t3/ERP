package com.aiiju.serviceImpl.workStage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.bean.oa.workStage.Draft;
import com.aiiju.bean.oa.workStage.HistoryLog;
import com.aiiju.bean.oa.workStage.Like;
import com.aiiju.bean.oa.workStage.Memory;
import com.aiiju.bean.oa.workStage.Reply;
import com.aiiju.bean.oa.workStage.WorkReport;
import com.aiiju.bean.oa.workStage.WorkReportList;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IDraftDao;
import com.aiiju.dao.oa.workStage.IHistoryLogDao;
import com.aiiju.dao.oa.workStage.IMemoryDao;
import com.aiiju.dao.oa.workStage.IMsgDao;
import com.aiiju.dao.oa.workStage.IWorkReportDao;
import com.aiiju.service.workStage.IWorkReportService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.business.PYUtil;
import com.aiiju.util.business.PYUtil.Type;
import com.aiiju.util.common.AjucGetCommonValue;
import com.aiiju.util.common.Constant;
import com.aiiju.util.common.DateUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.exception.ExceptionUtil;
import com.aiiju.util.http.HrmHttpClientUtil;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.pushMessage.PushUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service("workReportService")
public class WorkReportServiceImpl implements IWorkReportService {

	private static Logger logger = LoggerFactory.getLogger(WorkReportServiceImpl.class);

	@Autowired
	private IWorkReportDao wrDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IHistoryLogDao historyDao;
	
	@Autowired
	private IMsgDao msgDao;
	
	@Autowired
	private ICommonPageDao cpDao;
	
	@Autowired
	private IMemoryDao memoryDao;

	@Autowired
	private IDraftDao draftDao;
	/**
     * 工作汇报列表 
     * @param params: 来源from(PC,APP),查询条件content,类型type(我发送的；2-我接收的；3-查询我接收的),分页pageNum,pageSize
     * @return
     */
    @Override
    public Map<String, Object> getWorkReportList(Map<String, Object> params) {
    	logger.info("--------------------工作汇报列表START------------------------------------");
    	String content = params.get("content") == null ? null : params.get("content").toString();
    	String type = params.get("type") == null ? null : params.get("type").toString();
    	String pageNumStr = params.get("pageNum") == null ? null : params.get("pageNum").toString();
    	String pageSizeStr = params.get("pageSize") == null ? null : params.get("pageSize").toString();
    	String from = params.get("from") == null ? null : params.get("from").toString();
    	
    	
    	
    	if(StringUtils.isEmpty(type) || StringUtils.isEmpty(pageNumStr) || StringUtils.isEmpty(pageSizeStr) || StringUtils.isEmpty(from)){
    		throw new BizException(BizExceptionMessage.EMPTY_ERROR);
    	}
    	int pageNum=Integer.parseInt(pageNumStr);
		int pageSize=Integer.parseInt(pageSizeStr);
    	int totalNum = 0;
    	//获取总条数---type:1-我发送的；2-我接收的；3-查询我接收的
    	if("1".equals(type)){
    		totalNum =wrDao.getWorkReportListTotalNum(params);
    	}else if("2".equals(type)){
    		totalNum =wrDao.getReceiveWorkReportListTotalNum(params);
    	}else{
    		if(StringUtils.isEmpty(content)){
    			throw new BizException(BizExceptionMessage.EMPTY_ERROR,"type为3时，content不应该为空");
    		}
    		Map<String,Object> deptParams = new HashMap<String,Object>();
    		deptParams.put("companyId", params.get("companyId").toString());
    		List<Department> deptList = departmentDao.getDepartmentList(deptParams);
    		for(Department dept : deptList){
    			String name = dept.getName();
    			if(name.indexOf(content) == -1){
    				deptList.remove(dept);
    			}
    		}
    		//获取 查询部门 下所有的用户
    		if(deptList.size() > 0){
    			Map<String,Object> userParams = new HashMap<String,Object>();
    			userParams.put("deptIds", deptList);
    			List<User> allUsersList = userDao.selectByExample(userParams);
    			params.put("userLists", allUsersList);
    		}
    		//模糊查询用户
    		Map<String, Object> userParams = new HashMap<String, Object>();
    		userParams.put("content", content);
    		List<User> userListsForUser = userDao.selectByExample(userParams);
    		if(userListsForUser.size() > 0){
    			params.put("userListsForUser", userListsForUser);
    		}
    		totalNum =wrDao.queryReceiveWorkReportListTotalNum(params);
    	}
		
		Pagination pt=new Pagination(totalNum,pageNum,pageSize);
		params.put("pageSize", pageSize+Constant.EMPTY_STR);
		params.put("startRow", pt.getStartPos()+Constant.EMPTY_STR);
		
		//获取未读数
		Integer unReadNum = wrDao.getUnReadNumByUserId(params);
		//获取分页列表
		List<WorkReport> list= new ArrayList<WorkReport>();
		if("1".equals(params.get("type").toString())){
			list = wrDao.getWorkReportList(params);
		}else if("2".equals(params.get("type").toString())){
			list = wrDao.getReceiveWorkReportList(params);
		}else{
			list = wrDao.queryReceiveWorkReportList(params);
		}
		//获取附件列表
		for(WorkReport wr : list){
			Map<String,Object> fileParams = new HashMap<String,Object>();
			Long fId = wr.getId();
			fileParams.put("fId", fId);
			fileParams.put("businessId", 6);
			
			List<FileInfo> fiList = cpDao.getFileInfoList(fileParams);
			wr.setFileInfos(fiList);
		}
		//返回前端结果集
		Map<String, Object> reslutMap=new HashMap<String, Object>();
		if("APP".equals(from)){
			//按需排序 APP排序方式不同
			List<WorkReportList> wrlpList = this.changeDisplay(list,params);
			reslutMap.put("result", wrlpList);
		}else if("PC".equals(from)){
			for(WorkReport wr : list){
				Map<String,Object> m = new HashMap<>();
				m.put("id", wr.getUserId()) ;
				User user = userDao.seleteById(m);
				if(user != null){
					wr.setUserName(user.getName());
					Map<String,Object> fileParam = new HashMap<String,Object>();
					fileParam.put("fId",user.getId());
					fileParam.put("businessId",5);//5 = 头像
					String fileUrl=PropertiesUtil.getPropertyByKey("file_url");
					List<FileInfo> fil = cpDao.getFileInfoList(fileParam);
					if(fil!=null&&!fil.isEmpty()){
						for(FileInfo fi:fil){
							 String urls[]=fi.getUrl().split("/");
							fi.setUrl(fileUrl+urls[urls.length-1]);
						}
					}
					if(fil.size() > 0){
						wr.setLogo(fil.get(0).getUrl());
					}else{
						PYUtil pyUtil = new PYUtil();
						try {
							String pyName = pyUtil.toPinYin(user.getName().substring(user.getName().length()-1, user.getName().length()), "", Type.UPPERCASE);
							wr.setLastLetter(pyName.substring(0,1));
						} catch (BadHanyuPinyinOutputFormatCombination e) {
							e.printStackTrace();
							throw new BizException(BizExceptionMessage.PROP_ERROR,"拼音转换异常");
						}
					}
					wr.setDeptId(user.getDeptId());
					wr.setDeptName(user.getDeptName());
				}
				wr.setContent(JSON.parse(wr.getContent().toString()));
			}
			reslutMap.put("result", list);
		}
		reslutMap.put("unReadNum", unReadNum);
		reslutMap.put("totalNum", totalNum);
		reslutMap.put("page", pt);
		logger.info("--------------------工作汇报列表END------------------------------------");
		
		String commUser = "{ id:\""+params.get("userId")+"\",name:\""+params.get("userName")+"\"}";
		JSONObject commUserObject = JSONObject.parseObject(commUser);
		reslutMap.put("commUser", commUserObject);
		
		
		return reslutMap;
    }
    
    /**
     * 封装展示列表，按照前端所需要的展示顺序封装成对应的LIST
     * @param list 
     * @param params
     * @return
     */
    private List<WorkReportList> changeDisplay(List<WorkReport> list,Map<String, Object> params){
    	List<WorkReportList> wrlpList = new ArrayList<WorkReportList>();
		//将日期取出拼装成LIST
		List<String> timeList = new ArrayList<String>();
		if(list.size()>0){
			for(WorkReport wrp : list){
				Map<String,Object> m = new HashMap<>();
				m.put("id", wrp.getUserId()) ;
				User user = userDao.seleteById(m);
				if(user != null){
					wrp.setUserName(user.getName());
					wrp.setLogo(user.getFileInfo().getUrl());
					wrp.setDeptId(user.getDeptId());
					wrp.setDeptName(user.getDeptName());
				}
				wrp.setContent(JSON.parse(wrp.getContent().toString()));
				timeList.add(wrp.getCreateTime().split(" ")[0]);
			}
			//list的 index 集合    说白了就是节点。
			List<Integer> indexList = new ArrayList<Integer>();
			//判断前一个值和后一个值是否相同，如果不同，生成index节点     ---假设【a,a,b,b,c,d,d,d】生成 【1,3,4】
			for(int i=0;i<timeList.size();i++){
				if(i!=timeList.size()-1){
					if(!timeList.get(i).equals(timeList.get(i+1))){
						indexList.add(i);
					}
				}
			}
			indexList.add(timeList.size()-1);//把结尾补上----【1,3,4,7】
			//将前面截取的几个间隔放入wrlp对象
			for(int i=0;i<indexList.size();i++){
				Integer index1 = indexList.get(i);
				WorkReportList wrlp = new WorkReportList();
				String day = timeList.get(index1);
				if(!day.equals(DateUtil.getDay()) ){
					String dayDesc=DateUtil.formatDate((Date) DateUtil.transFormDate(day,"yyyy-MM-dd"), "M月d日");
					wrlp.setDay(dayDesc);
				}else{
					wrlp.setDay("今天");
				}
				wrlp.setDate(DateUtil.getWeek((Date) DateUtil.transFormDate(timeList.get(index1),"yyyy-MM-dd")));
				List<WorkReport> wrpList = new ArrayList<WorkReport>();
				if(i==0){
					for(int j=0;j<=index1;j++){
						//根据前端要求返回具体时间展示方式
						list.get(j).setCreateTime(DateUtil.relativeDateFormat(list.get(j).getCreateTime(),2));
						wrpList.add(list.get(j));
					}
				}else{
					for(int j=indexList.get(i-1)+1;j<=index1;j++){
						//根据前端要求返回具体时间展示方式
						list.get(j).setCreateTime(DateUtil.relativeDateFormat(list.get(j).getCreateTime(),2));
						wrpList.add(list.get(j));
					}
				}
				wrlp.setList(wrpList);
				
				wrlpList.add(wrlp);
			}
		}
		return wrlpList;
    }


	
	
	
	
    /**
     * 保存工作汇报
     * @param params: 类型workLogType(1:日报2:周报3:月报),content日报内容,fileIds附件IDS,发送人sendToPerson,发送部门sendToDept(逗号隔开),draftId草稿ID
     */
	@Override
	@Transactional
	public Map<String, Object> saveWorkReport(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String workLogType = params.get("workLogType") == null ? null : params.get("workLogType").toString();
		String content = params.get("content") == null ? null : params.get("content").toString();
		String fileIds = params.get("fileIds") == null ? null : params.get("fileIds").toString();
		String sendToPerson = params.get("sendToPerson") == null ? null : params.get("sendToPerson").toString();
		String sendToDept = params.get("sendToDept") == null ? null : params.get("sendToDept").toString();
		String draftId = params.get("draftId") == null ? null : params.get("draftId").toString();
		//来源from(PC,APP)
		
		
		
		String from = params.get("from") == null ? null : params.get("from").toString();
		if("APP".equals(from)){//由于app无法获取到草稿draftId,排除对此id非空的验证
			if(StringUtils.isEmpty(workLogType) || StringUtils.isEmpty(content) || (StringUtils.isEmpty(sendToPerson) && StringUtils.isEmpty(sendToDept))){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR);
			}
		}else{//现在pc前端要求也不需要判断草稿draftId了,同样排除排除对此id非空的验证
			if(StringUtils.isEmpty(workLogType) || StringUtils.isEmpty(content) || (StringUtils.isEmpty(sendToPerson) && StringUtils.isEmpty(sendToDept))){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR);
			}
		}
		
		StringBuffer pushUserIds = new StringBuffer();
	
		
		WorkReport workReport = new WorkReport();
		workReport.setCompanyId(Long.valueOf(params.get("companyId").toString()));
		workReport.setUserId(Long.valueOf(params.get("userId").toString()));
		workReport.setContent(content);
		workReport.setWorkReportType(Integer.valueOf(workLogType));
		workReport.setSendToDept(sendToDept);
		workReport.setSendToPerson(sendToPerson);
		workReport.setIsDel(0);
		workReport.setIsWithdraw(0);
		workReport.setFiles(params.get("fileIds")==null?null:params.get("fileIds").toString());
		wrDao.saveWorkReport(workReport);
		Long id = workReport.getId();
		//假如存在附件,将文件信息修改完整--原来的附件关联草稿，现在关联工作汇报
		if(!StringUtils.isEmpty(fileIds)){
			Map<String,Object> fileParam = new HashMap<String,Object>();
			fileParam.put("fId", id);
			fileParam.put("businessId", 6);
			fileParam.put("ids", fileIds.split(","));
			cpDao.batchUpdateFileInfo(fileParam);
		}
		//获取接收人列表
		List<User> userList = new ArrayList<User>();
		
		if(!StringUtils.isEmpty(sendToDept)){
			
				
			params.put("deptIds",sendToDept);
			String deptChildIds = departmentDao.getDeptChildIds(params);
			
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("deptIdString", deptChildIds.split(","));
			userMap.put("companyId", params.get("companyId"));
			//获取发送部门下 （含下属部门）人员
			
			userList.addAll(userDao.selectByExample(userMap));
			
		}
		
		
		if(!StringUtils.isEmpty(sendToPerson)){
			String[] personArry = sendToPerson.split(",");
			for(String str : personArry){
				
				//排重
				boolean judge= true;
				if(!StringUtils.isEmpty(sendToDept)){
					for(User u:userList){
						if(u.getId().toString().equals(str)){
							judge = false;
							break;
						}
					}
					
				}
				if(judge){
					User user = new User();
					user.setId(Long.valueOf(str));
					userList.add(user);
				
				}
			}
		}
		if(userList ==null || userList.size()==0){
			throw new BizException("300","所选部门员工人数为0");
		}
		//因为所有工作汇报都需要给管理员，先用AJUC找管理员ID，然后排重,不用加到接收人的数据库字段中
			String managerId = AjucGetCommonValue.ajucGetManagerId(params);
			boolean managerJudge= true;
		List<HistoryLog> logs = new ArrayList<HistoryLog>();
		List<Long> userIdList = new ArrayList<Long>();
		for(User user : userList){
			//接收人的ID的LIST
			userIdList.add(user.getId());
			HistoryLog log = new HistoryLog();
			log.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			log.setSceneType(1);
			log.setRefId(id);
			log.setReceiveUid(user.getId());
			logs.add(log);
			
			//推送不发给自己
			if(!user.getId().toString().equals(params.get("userId").toString())){
				pushUserIds.append(user.getId().toString()+",");
			}
			//当没有选择发给管理员时，在情景历史表中，添加发给管理员的数据，在工作汇报接收者字段中，不用添加
			if(user.getId().toString().equals(managerId)){
				managerJudge=false;
			}
		}
		if(managerJudge){
			userIdList.add(Long.valueOf(managerId));
			HistoryLog log = new HistoryLog();
			log.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			log.setSceneType(1);
			log.setRefId(id);
			log.setReceiveUid(Long.valueOf(managerId));
			logs.add(log);
			
			pushUserIds.append(managerId+",");
		}
		historyDao.addHistoryLog(logs);
		//保存成功后，历史联系人修改
		boolean isAddMemory = true;
		Long updateId = Long.valueOf(0);
		//获取原来status=1的记录的ID
		Map<String,Object> memoryParam = new HashMap<String,Object>();
		memoryParam.put("companyId", params.get("companyId"));
		memoryParam.put("userId", params.get("userId"));
		memoryParam.put("sceneType", workLogType);
		List<Memory> memoryList = memoryDao.getMemoryInfoList(memoryParam);
		for(Memory m : memoryList){
			if(sendToPerson.equals(m.getSendToPerson()) && sendToDept.equals(m.getSendToDept())){
				isAddMemory = false;
				updateId = m.getId();
			}
		}
		Memory memory = memoryDao.getCurMemoryInfo(memoryParam);
		if(memory != null){
			Map<String,Object> updateParam = new HashMap<String,Object>();
			updateParam.put("id", memory.getId());
			updateParam.put("status", 0);
			//将原来的status=1的记录改为status=0
			memoryDao.updateMemoryInfoStatus(updateParam);
		}
		if(isAddMemory){//新增的联系人是原有记录中不存在的。
			memoryParam.put("sendToDept", sendToDept);
			memoryParam.put("sendToPerson", sendToPerson);
			//新增一条status=1的记录
			memoryDao.addMemoryInfo(memoryParam);
		}else{//新增的联系人是原有记录中存在的
			Map<String,Object> updateOldParam = new HashMap<String,Object>();
			updateOldParam.put("id", updateId);
			updateOldParam.put("status", 1);
			memoryDao.updateMemoryInfoStatus(updateOldParam);
		}
		Map<String,Object> msgParam = new HashMap<String,Object>();
		msgParam.put("companyId", params.get("companyId").toString());
		msgParam.put("receiveIds", userIdList);
		msgParam.put("sceneId", id);
		msgParam.put("sceneType", 1);
		//TODO 待修改
		msgParam.put("subType", workLogType);
		msgParam.put("authorId", params.get("userId").toString());
		msgDao.batchAddMsgInfo(msgParam);
		//工作汇报保存结束后删除草稿
		Map<String,Object> draftParams = new HashMap<String,Object>();
		/*draftParams.put("id", draftId);
		if(StringUtils.isNotEmpty(draftId)){
			draftDao.deleteDraft(draftParams);
		}*/
		if(!StringUtils.isEmpty(pushUserIds.toString())){
			String pushTitle =  params.get("userName").toString()+"发布了"
							+("1".equals(workLogType)?"日报。":"2".equals(workLogType)?"周报。":"月报。");
			params.put("pushTitle",pushTitle);
			params.put("pushUserIds",pushUserIds.toString().substring(0,pushUserIds.length()-1));
			PushUtil.sendNoticesByPhp(params,1,3);
		
		}
		resultMap.put("id", id);
		return resultMap;
	}
	
	/**
	 * 点赞OR取消点赞
	 * @param params: likeId点赞ID-0为未点赞,否则为点赞ID
	 * 				  id记录ID
	 */
	@Override
	public Map<String,Object> changeLike(Map<String,Object> params){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String id = params.get("id") == null ? null : params.get("id").toString();
		String likeId = params.get("likeId") == null ? null : params.get("likeId").toString();
		String from = params.get("from") == null ? null : params.get("from").toString();
		if("APP".equals(from)){  //APP 无法传递ID值 只用LikeID 确定那个点赞
			if( StringUtils.isEmpty(likeId)){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR);
			}
		}else{
			if(StringUtils.isEmpty(id) || StringUtils.isEmpty(likeId)){
				throw new BizException(BizExceptionMessage.EMPTY_ERROR);
			}
		}
		
		Map<String,Object> likeParams = new HashMap<String,Object>();
		if("0".equals(likeId)){
			Integer sceneLike = wrDao.selectLike(params);
			if(sceneLike >0){
				throw new BizException("300","无法重复点赞");
			}
			
			likeParams.put("sceneId", id);
			Like like = new Like();
			like.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			like.setUserId(Long.valueOf(params.get("userId").toString()));
			like.setSceneType(1);
			like.setSceneId(Long.valueOf(id));
			wrDao.addLike(like);
			Long returnId = like.getId();
			resultMap.put("likeId", returnId);
		}else{
			likeParams.put("likeId", likeId);
			wrDao.deleteLike(likeParams);
			resultMap.put("likeId", 0);
		}
		return resultMap;
	}
	
	/**
	 * 新增草稿---先判断是否存在草稿
	 * @param params: 类型workLogType(1:日报2:周报3:月报),--必填
	 * 					content日报内容,
	 * 					发送人sendToPerson,
	 * 					发送部门sendToDept(逗号隔开)
	 */
	@Override
	public Map<String,Object> addDraft(Map<String, Object> params){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String workLogType = params.get("workLogType") == null ? null : params.get("workLogType").toString();
		if(StringUtils.isEmpty(workLogType)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Map<String,Object> draftParams = new HashMap<String,Object>();
		draftParams.put("companyId", params.get("companyId"));
		draftParams.put("userId", params.get("userId"));
		draftParams.put("sceneType", 1);
		draftParams.put("subType", workLogType);
		Draft draft = draftDao.getDraft(draftParams);
		if(draft != null){
			Map<String,Object> fileParams = new HashMap<String,Object>();
			fileParams.put("fId", draft.getId());
			fileParams.put("businessId", 8);
			String fileUrl=PropertiesUtil.getPropertyByKey("file_url");
			List<FileInfo> fileInfoList = cpDao.getFileInfoList(fileParams);
			if(fileInfoList!=null&&!fileInfoList.isEmpty()){
				for(FileInfo fi:fileInfoList){
					 String urls[]=fi.getUrl().split("/");
					fi.setUrl(fileUrl+urls[urls.length-1]);
				}
			}
			if(draft.getContent() != null || fileInfoList.size() > 0){
				resultMap.put("isDraft", 1);
			}else{
				resultMap.put("isDraft", 0);
			}
			if(draft.getContent() != null){
				draft.setContent(JSON.parse(draft.getContent().toString()));
			}else{
				draft.setContent(JSON.parse(""));
			}
			resultMap.put("draft", draft);
			resultMap.put("fileInfoList", fileInfoList);
		}else{
			Draft dr = new Draft();
			dr.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			dr.setUserId(Long.valueOf(params.get("userId").toString()));
			dr.setSceneType(1);
			dr.setSubType(Integer.valueOf(workLogType));
//			dr.setContent("{}");
			draftDao.addDraft(dr);
			resultMap.put("isDraft", 0);
			resultMap.put("draft", dr);
		}
		return resultMap;
	}
	
	/**
	 * 更新草稿
	 * @param params: draftId草稿ID,--必填
	 * 					content日报内容,
	 * 					发送人sendToPerson,
	 * 					发送部门sendToDept(逗号隔开)
	 */
	@Override
	public Map<String,Object> updateDraft(Map<String, Object> params){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String draftId = params.get("draftId") == null ? null : params.get("draftId").toString();
		if(StringUtils.isEmpty(draftId)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Map<String,Object> draftParams = new HashMap<String,Object>();
		draftParams.put("content", params.get("content").toString());
		draftParams.put("sendToPerson", params.get("sendToPerson"));
		draftParams.put("sendToDept", params.get("sendToDept"));
		draftParams.put("id", draftId);
		draftDao.updateDraft(draftParams);
		return resultMap;
	}
	

	/**
	 * 获取树列表
	 * @param params: from(PC,APP)； type(1:最近联系人，2:同事，3:部门)； sceneType(1:工作汇报，2:任务，3:审批)
	 */
	@Override
	public Map<String, Object> getTreeList(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String from = params.get("from") == null ? null : params.get("from").toString();
		String type = params.get("type") == null ? null : params.get("type").toString();
		String sceneType = params.get("sceneType") == null ? null : params.get("sceneType").toString();
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(from) || StringUtils.isEmpty(sceneType)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		if("1".equals(type)){//最近联系人
			Map<String,Object> memoryParams = new HashMap<String,Object>();
			memoryParams.put("companyId", params.get("companyId"));
			memoryParams.put("userId", params.get("userId"));
			memoryParams.put("sceneType", sceneType);
			List<Memory> memoryList = memoryDao.getMemoryInfoList(memoryParams);
			resultMap.put("result", memoryList); 
		}else if("2".equals(type)){//同事
			Map<String,Object> userParams = new HashMap<String,Object>();
			userParams.put("companyId", params.get("companyId"));
			List<User> userList = userDao.selectByExample(userParams);
			if("PC".equals(from)){//PC端
				/*Map<String,Object> userMap = new HashMap<String,Object>();
				for(int i=0;i<26;i++){//按字母A~Z分类
					char y=(char)(65+i);
					List<User> pyList = new ArrayList<User>();
					for(User u : userList){
						PYUtil util = new PYUtil();
						try {
							String pyName = util.toPinYin(u.getName(),"",Type.UPPERCASE);
							u.setPyName(pyName);
							u.setFirstPYName(pyName.substring(0,1));
							String lastLetter = util.toPinYin(u.getName().substring(u.getName().length()-1, u.getName().length()),"",Type.UPPERCASE);
							u.setLastLetter(lastLetter.substring(0,1));
							if(u.getFirstPYName().equals(y+"")){
								pyList.add(u);
							}
						} catch (BadHanyuPinyinOutputFormatCombination e) {
							e.printStackTrace();
							throw new BizException(BizExceptionMessage.PROP_ERROR, "拼音转换异常");
						}
					}
					userMap.put(y+"", pyList);
				}*/
				resultMap.put("result", userList);
			}else{//APP端
				for(User u : userList){
					PYUtil util = new PYUtil();
					try {
						String pyName = util.toPinYin(u.getName(),"",Type.UPPERCASE);
						u.setPyName(pyName);
						u.setFirstPYName(pyName.substring(0,1));
						String lastLetter = util.toPinYin(u.getName().substring(u.getName().length()-1, u.getName().length()),"",Type.UPPERCASE);
						u.setLastLetter(lastLetter.substring(0,1));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
						throw new BizException(BizExceptionMessage.PROP_ERROR, "拼音转换异常");
					}
				}
				resultMap.put("result", userList);
			}
		}else{//部门
			Map<String,Object> deptParams = new HashMap<String,Object>();
			deptParams.put("companyId", params.get("companyId"));
			List<Department> deptList = departmentDao.getDepartmentList(deptParams);
			for(Department dept : deptList){
				dept.setPid(dept.getParentDeptId());
			}
			resultMap.put("result", deptList);
		}
		return resultMap;
	}

	/**
	 * 撤回工作汇报
	 */
	@Override
	public Map<String, Object> withdrawReport(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String id = params.get("id") == null ? null : params.get("id").toString();
		if(StringUtils.isEmpty(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		Map<String,Object> withParam = new HashMap<String,Object>();
		withParam.put("id", id);
		withParam.put("isWithdraw", 1);
		wrDao.updateReport(withParam);
		return resultMap;
	}
	
	/**
	 * 修改MSG状态--单纯针对业务的已读
	 */
	@Override
	public Map<String, Object> updateReadStatus(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 批量更新状态
    		String id = params.get("id") == null ? null : params.get("id").toString();
		if(StringUtils.isEmpty(id)){
			throw new BizException(BizExceptionMessage.EMPTY_ERROR);
		}
		try {
			HistoryLog historyLog = new HistoryLog();
			historyLog.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			historyLog.setIsRead(1);
			historyLog.setRefId(Long.valueOf(id));
			historyLog.setReceiveUid(Long.valueOf(params.get("userId").toString()));
			historyDao.updateHistoryLog(historyLog);
		} catch (Exception e){
			throw new BizException(BizExceptionMessage.DB_ERROR, "更新状态失败");
		}
		return resultMap;
	}
	
	

	
	
	/**
	 * 批量操作日报为已读
	 */
	@Override
	public Map<String, Object> batchUpdateWorkReport(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sceneType =params.get("sceneType")==null?null:params.get("sceneType").toString();
		if(StringUtils.isEmpty(sceneType)){
			throw new BizException("300", "请传递场景类型 sceneType");
		}
		try {
			historyDao.batchUpdateHistoryLog(params);
		} catch (Exception e){
			throw new BizException("300", "操作失败");
		}
		return resultMap;
	}
	
	public WorkReport getReportDetail(Map<String, Object> params) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		WorkReport workReportDetail= null;
		//工作汇报的主键id
		String id = params.get("id") == null ? null : params.get("id").toString();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		//场景类型(1表示工作汇报)
		String sceneType = params.get("sceneType") == null ? null : params.get("sceneType").toString();
		//查询回复时的页码,如果为-1表示查询所有回复(页大小默认10条)
		String pageNum = params.get("pageNum") == null ? null : params.get("pageNum").toString();
		if(!"-1".equals(pageNum)&&!StringUtils.isEmpty(pageNum)){
			
			Integer pageSize = 10;
			params.put("pageSize",(Integer.valueOf(pageNum))*pageSize );
			params.put("startRow",(Integer.valueOf(pageNum)-1)*pageSize );
		}
		if(StringUtils.isEmpty(id)){
			throw new BizException("1", "工作汇报id为空");
		}else if(StringUtils.isEmpty(companyId)){
			throw new BizException("1", "公司id为空");
		}else if(StringUtils.isEmpty(userId)){
			throw new BizException("1", "用户id为空");
		}
		
		//准备查询数据
		try {
			workReportDetail = wrDao.selectReportDetail(params);
			
			if(workReportDetail!=null){
				workReportDetail.setContent(JSON.parse(workReportDetail.getContent().toString()));
				workReportDetail.setLikeNum(workReportDetail.getLikePersons().size());
				workReportDetail.setReplyNum(workReportDetail.getReplys().size());
				Map<String,Object> fileParams = new HashMap<String,Object>();
				
				
				
				//工作汇报图片(app 只需要图片，后缀为jpg、png)
				Long fId = workReportDetail.getId();
				fileParams.put("fId", fId);
				fileParams.put("businessId", 6);
				String [] types = {"jpg","png"};
				fileParams.put("types", types);
//				String fileUrl=PropertiesUtil.getPropertyByKey("file_url");
				List<FileInfo> fiList = cpDao.getFileInfoList(fileParams);
				if(fiList!=null&&!fiList.isEmpty()){
					for(FileInfo fi:fiList){
						 int index=fi.getUrl().lastIndexOf("/");
						 fi.setThumbUrl(fi.getUrl().substring(0,index+1)+"Thumb_"+fi.getUrl().substring(index+1));
					}
				}
				workReportDetail.setFileInfos(fiList);
				for(Reply reply:workReportDetail.getReplys()){
					reply.setCreateTime(DateUtil.relativeDateFormat(reply.getCreateTime(),2));
				}
			}
			
		} catch (Exception e) {
			ExceptionUtil.throwExceptionUtil(e);
		}	
		return workReportDetail;
	}
	
	
		
}
