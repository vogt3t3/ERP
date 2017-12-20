package com.aiiju.serviceImpl.workStage;

import java.util.ArrayList;
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
import com.aiiju.bean.oa.workStage.Announcement;
import com.aiiju.bean.oa.workStage.AnnouncementReadLogs;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.dao.oa.dept.IDepartmentDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.dao.oa.workStage.IAnnouncementDao;
import com.aiiju.dao.oa.workStage.IAnnouncementReadLogsDao;
import com.aiiju.dao.oa.workStage.IMessagePushLogsDao;
import com.aiiju.service.workStage.IAnnouncementService;
import com.aiiju.serviceImpl.CommonPageService;
import com.aiiju.util.business.DeptUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.pushMessage.PushUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Service("announcementService")
@Transactional
public class AnnouncementServiceImpl extends CommonPageService implements IAnnouncementService {

	/**
	 * 日志log打印
	 */
	private static Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);
	@Autowired
	private IAnnouncementDao dao;
	@Autowired
	private IAnnouncementReadLogsDao logsDao;
	@Autowired
	private IMessagePushLogsDao messagePushLogsDao;
	@Override
	public Map<String, Object> getAnnouncements(Map<String, Object> map) {
		logger.info("=============获取公告列表start===============");
		String recipient=map.get("userId").toString();
		String companyId=map.get("companyId").toString();
		Map<String, Object> parms= new HashMap<String, Object>();
		/*//判断查询本人
		if(map.get("emp")!=null){
			parms.put("empId", recipient);
		}*/
		parms.put("recipient", recipient);
		parms.put("companyId", companyId);
		int muns=logsDao.countAnnouncementReadLogs(Long.parseLong(recipient));
		//统计总数
		int pageNum = Integer.parseInt(map.get("pageNum").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		Pagination pt = new Pagination(muns, pageNum, pageSize);
		// 添加分页条件
		parms.put("pageSize", pageSize);
		parms.put("startRow", pt.getStartPos());
		List<Announcement> announcements=dao.getAnnouncements(parms);
		Map<String, Object> announcementMap= new HashMap<String, Object>();
		announcementMap.put("page", pt);
		
		//获取统计读/未读的总计
		Map<String, Object> isRead = new HashMap<String, Object>();
		for (Announcement announcement : announcements) {
			//1.查询发布人的头像
			Map<String, Object> userHead = new HashMap<String, Object>();
			userHead.put("fId", announcement.getUserId());
			userHead.put("businessId", 5);
			List<FileInfo> list1 = fileDao.getFileInfoList(userHead);
			if(list1!=null&&list1.size()>0){
				announcement.setSenderImg(list1.get(0).getUrl());
			}
			List<AnnouncementReadLogs> list =logsDao.countIsRead(announcement.getId());
			isRead.put(announcement.getId().toString(), list);
			
			//2.将接收部门的数据进行处理：仅返回逗号隔开的部门名称,如：产品部,研发部,测试部
			String recipientDept = announcement.getRecipientDept();
			if(StringUtils.isNotBlank(recipientDept)){
				JSONArray array = JSONArray.parseArray(recipientDept);
				int i = 1;
				StringBuilder recDep = new StringBuilder();
				StringBuilder recDepIds = new StringBuilder();
				for (Object object : array) {
					JSONObject json = JSONObject.parseObject(object.toString());
					String decpName = json.getString("name");
					String decpId = json.getString("id");
					if(i==1||i==array.size()){
						recDep.append(decpName);
						recDep.append(decpId);
					}else{
						recDep.append(","+decpName);
						recDep.append(","+decpId);
					}
					i++;
				}
				announcement.setRecipientDept(recDep.toString());
				announcement.setRecipientDeptIds(recDepIds.toString());
			}
			
			//3.根据前端需求将公告附件与公告列表一起返回
			String[] fileIds=announcement.getFiles().split(",");
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();
			if(fileIds!=null&&fileIds.length>0){
				Map<String,Object> idMap = new HashMap<String, Object>();
				for (String id : fileIds) {
					if(StringUtils.isNotBlank(id)){
						idMap.put("id", id);
						List<FileInfo> fileInfoList=fileDao.getFileInfoList(idMap);
						fileInfos.add(fileInfoList.get(0));
						if(fileInfos!=null&&fileInfos.size()>0){
							announcement.setFileList(fileInfos);
						}
					}
				}
			}
		}
		
		announcementMap.put("announcements", announcements);
		announcementMap.put("isRead", isRead);
		logger.info("=============获取公告列表end===============");
		return announcementMap;
	}

	@Override
	public boolean updateAnnouncement(Map<String, Object> map) {
		String announ = map.get("announcement").toString();
		String fileIds = map.get("fileIds").toString();
		//当前登录用户
		String currentUserId = map.get("userId")==null?null:map.get("userId").toString();
		Announcement announcement = JSONObject.parseObject(announ, Announcement.class);
		if(!currentUserId.equals(announcement.getUserId().toString())){
			throw new BizException("402","对不起,您无权限进行此操作！");
		}
		announcement.setFiles(fileIds);
		
		//比较文件是否做了修改
		Announcement ment=dao.seleteAnnouncementId(announcement.getId());
		String[] fileOld=ment.getFiles().split(",");
		String[] fileNew=fileIds.split(",");
		List<String> ids = new ArrayList<String>();
		for (String neo : fileNew) {
			for (String old : fileOld) {
				if(old.equals(neo)){
					ids.add(old);
					break;
				}
			}
		}
		//删除服务器上文件
		for (String string : ids) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", string);
			this.delFileRecord(params);
		}
		
		dao.updateAnnouncement(announcement);
		Map<String, Object> isTop = new HashMap<String, Object>();
		isTop.put("announcementId", announcement.getId());
		isTop.put("isTop", 0);
		isTop.put("isRead", 0);
		//将这条公告下的 记录全部改为未读 未置顶
		logsDao.isTopAndRead(isTop);
		return true;
	}

	@Override
	public boolean isTopAnnouncement(Map<String, Object> map) {
		String recipient=map.get("userId")==null?null:map.get("userId").toString();
		String announcementId=map.get("announcementId")==null?null:map.get("announcementId").toString();
		String top=map.get("isTop")==null?null:map.get("isTop").toString();//是否置顶
		
		Map<String, Object> isTop = new HashMap<String, Object>();
		isTop.put("recipient", recipient);
		if(top.equals("1")){
			//先将已有的设为未置顶
			isTop.put("isTop", 0);
			logsDao.isTopAndRead(isTop);
			
		}
		
		isTop.put("announcementId", announcementId);
		isTop.put("isTop", top);
		isTop.put("isRead", 0);
		logsDao.isTopAndRead(isTop);
		return true;
	}
	
	@Autowired
	private IDepartmentDao departmentDao;
	@Autowired
	private IUserDao userDao;

	@Autowired
	private ICommonPageDao fileDao;
	@Override
	@Transactional
	public int addAnnouncement(Map<String, Object> map)  {
		String announ = map.get("announcement").toString();
		String fileIds = map.get("fileIds").toString();
		StringBuffer UserIds = new StringBuffer();
		
		
		Announcement announcement = JSONObject.parseObject(announ, Announcement.class);
		announcement.setFiles(fileIds);
		announcement.setUserId(Long.parseLong(map.get("userId").toString()));
		announcement.setCompanyId(Long.parseLong(map.get("companyId").toString()));
		dao.addAnnouncement(announcement);
		//获取当前查询的部门列表
		String[] dept=announcement.getRecipientDept().replace("[", "").replace("]", "").trim().split(",");
		
		List<Department> curDepts=new ArrayList<Department>();	
		int m=1;
		for (int i = 0; i < dept.length; i+=2) {
			String de=dept[i]+","+dept[m];
			m+=2;
			Department department=JSONObject.parseObject(de, Department.class);
			curDepts.add(department);
		}

		//该部门及其子部门
		List<Department> showDepts = new ArrayList<Department>();
		//获取所有部门
		List<Department> allDepts= departmentDao.getDepartmentList(map);
		List<Department> deptList=DeptUtil.getAllDepts(curDepts, allDepts, showDepts);
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("deptIds", deptList);
		userMap.put("companyId", map.get("companyId"));
		//获取发送部门下 （含下属部门）人员
		List<User> list = userDao.selectByExample(userMap);
		if(list ==null || list.size()==0){
			throw new BizException("300","所选部门员工人数为0");
		}
		List<AnnouncementReadLogs> announList = new ArrayList<AnnouncementReadLogs>();
		for (User user : list) {
			AnnouncementReadLogs annlog = new AnnouncementReadLogs();
			annlog.setAnnouncementId(announcement.getId());
			annlog.setRecipient(user.getId());
			announList.add(annlog);
			if(!user.getId().toString().equals(map.get("userId").toString())){
				UserIds.append(user.getId()+",");
			}
		}
		//批量插入记录表
		logsDao.insertAnnouncementReadLogs(announList);
		
		if(!StringUtils.isEmpty(UserIds.toString())) {
			String pushTitle =  map.get("userName").toString()+"发布了公告。";
			String pushUserIds =  UserIds.toString().substring(0,UserIds.length()-1);
			
			map.put("pushTitle",pushTitle);
			map.put("pushUserIds",pushUserIds);
			PushUtil.sendNoticesByPhp(map,4,7);
			//推送记录
			List<Map<String, Object>> addList=new ArrayList<Map<String, Object>>();
			String[] userIds=pushUserIds.split(",");
			for(String userId:userIds){
				Map<String, Object>  addMap=new HashMap<String, Object>();
				addMap.put("userId", userId);
				addMap.put("companyId",map.get("companyId"));
				addMap.put("relateId",announcement.getId());
				addMap.put("title",announcement.getTitle());
				addMap.put("type",1);//1是公告2是考勤
				addMap.put("content", announcement.getContent());
				addList.add(addMap);
			}
			try {
				messagePushLogsDao.insertMessagePushLogs(addList);	
			}catch (Exception e) {
				throw new BizException("1", "插入推送公告历史数据失败");
			}
			
		}
		return 1;
	}

	@Override
	public boolean deleteAnnouncement(Map<String, Object> map) {
		String id = map.get("id")==null?null:map.get("id").toString();
		//当前登录用户
		String currentUserId = map.get("userId")==null?null:map.get("userId").toString();
		
		Announcement announcement=dao.seleteAnnouncementId(Long.parseLong(id));
		if(!currentUserId.equals(announcement.getUserId().toString())){
			throw new BizException("402","对不起,您无权限进行此操作！");
		}
		if(announcement.getFiles()!=null){
			String[] files = announcement.getFiles().split(",");
			// 删除相关附件
			for (String string : files) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", string);
				this.delFileRecord(params);
			}
		}
		//删除公告
		dao.deleteAnnouncementId(Long.parseLong(id));
		//删除相关公告
		logsDao.deleteAnnouncementReadLogsId(Long.parseLong(id));
		return true;
	}
	
	@Override
	public Map<String,Object> getAnnouncementId(Map<String, Object> map) {
		
		String recipient=map.get("userId")==null?null:map.get("userId").toString();
		String announcementId=map.get("announcementId")==null?null:map.get("announcementId").toString();
		Map<String,Object> announcementMap = new HashMap<String, Object>();
		Announcement announcement=dao.seleteAnnouncementId(Long.parseLong(announcementId));
		//1.查询公告相关附件
		String[] fileIds=announcement.getFiles().split(",");
		List<FileInfo> list = new ArrayList<FileInfo>();
		if(fileIds!=null&&fileIds.length>0){
			
			for (String id : fileIds) {
				if(StringUtils.isNotBlank(id)){
					FileInfo file = fileDao.selectById(new Long(id));
					list.add(file);
				}
			}
		}
		announcementMap.put("file", list);
		//2.查询公告发布人的头像
		Map<String, Object> userHead = new HashMap<String, Object>();
		userHead.put("fId", announcement.getUserId());
		userHead.put("businessId", 5);
		List<FileInfo> list1 = fileDao.getFileInfoList(userHead);
		if(list1!=null&&list1.size()>0){
			announcement.setSenderImg(list1.get(0).getUrl());
		}
		//3.查看详情时修改记录表为已读
		Map<String, Object> isTop = new HashMap<String, Object>();
		isTop.put("recipient", recipient);
		isTop.put("announcementId", announcementId);
		isTop.put("isRead", 1);
		logsDao.isTopAndRead(isTop);
		announcementMap.put("announcement", announcement);
		
		return announcementMap;
	}

}
