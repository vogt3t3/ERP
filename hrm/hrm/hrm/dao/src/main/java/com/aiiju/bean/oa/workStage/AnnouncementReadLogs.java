package com.aiiju.bean.oa.workStage;

import java.io.Serializable;
import java.util.Map;

/**
 * 公告记录
 * @author 维斯
 *
 * 2016年12月28日   上午10:59:13
 */
public class AnnouncementReadLogs implements Serializable {

    private static final long serialVersionUID = -2193368239103348032L;
    /**
     *  流水号
     */
    private Long id;
    /**
     *  是否已读 0-否 1-是
     */
    private Integer isRead;

    private Integer nums;
    private String isReads;
    /** 
     * 公告接收人，即用户ID 多个逗号分隔
     */
    private Long recipient;
    /** 
     * 公告ID
     */
    private Long announcementId;
    /**
     * 创建时间
     */
    private String createTime;
    private Integer isTop;
    
    
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public String getIsReads() {
		return isReads;
	}
	public void setIsReads(String isReads) {
		this.isReads = isReads;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIsRead() {
        return isRead;
    }
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
    public Long getRecipient() {
        return recipient;
    }
    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }
    public Long getAnnouncementId() {
        return announcementId;
    }
    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }
    @Override
    public String toString() {
        return "AnnouncementReadLogsPojo [id=" + id + ", isRead=" + isRead + ", recipient=" + recipient
                + ", announcementId=" + announcementId + "]";
    }
    
}
