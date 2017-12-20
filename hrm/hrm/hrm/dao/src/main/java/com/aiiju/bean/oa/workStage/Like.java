package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 点赞-实体类---列表查询使用
 * @author BZ
 *
 */
public class Like implements Serializable{
	
	private static final long serialVersionUID = 813108285254713235L;
	//工作汇报ID
	private Long id;
	//公司ID
	private Long companyId;
	//发起人ID
	private Long userId;
	//场景类型
	private Integer sceneType;
	//场景对应ID
	private Long sceneId;
	
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
	public Integer getSceneType() {
		return sceneType;
	}
	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	
}


