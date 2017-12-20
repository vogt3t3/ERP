package com.aiiju.bean.oa.workStage;

import java.io.Serializable;
import java.util.List;

import com.aiiju.bean.oa.config.FileInfo;

/**
 * 公告
 * @author 维斯
 *
 * 2016年12月28日   上午11:00:59
 */
public class Announcement implements Serializable{
	
	private static final long serialVersionUID = -2122573962897379767L;
	/**
	 * 公告的id
	 */
	private Long id;
	/**
	 * 公司ID
	 */
	private Long companyId;
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 公告标题
	 */
	private String title;
	/**
	 * 公告内容
	 */
	private String content;
	/**
	 * 发布人/部门
	 */
	private String signature;
	/**
	 * 接收部门
	 */
	private String recipientDept;
	/**
	 * 接收部门ID ,隔开
	 */
	
	private String recipientDeptIds;
	/**
	 * 公告创建时间
	 */
	private String createTime;
	/**
	 * 公告修改时间
	 */
	private String updateTime;
	/**
	 * 文件
	 */
	private String files;
	private List<FileInfo> fileList;
	
	/**
	 * 发布人头像
	 */
	private String senderImg;
	/**
	 * 是否置顶 默认0未置顶  1置顶
	 */
	private Integer isTop;
	/**
	 * 已读/未读
	 */
	private Integer isRead;
	
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public Integer getIsTop() {
        return isTop;
    }
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
    public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getContent() {
		return content;
	}
	public String getRecipientDept() {
        return recipientDept;
    }
    public void setRecipientDept(String recipientDept) {
        this.recipientDept = recipientDept;
    }
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<FileInfo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileInfo> fileList) {
		this.fileList = fileList;
	}
	public String getSenderImg() {
		return senderImg;
	}
	public void setSenderImg(String senderImg) {
		this.senderImg = senderImg;
	}
	public String getRecipientDeptIds() {
		return recipientDeptIds;
	}
	public void setRecipientDeptIds(String recipientDeptIds) {
		this.recipientDeptIds = recipientDeptIds;
	}
	
		
}
