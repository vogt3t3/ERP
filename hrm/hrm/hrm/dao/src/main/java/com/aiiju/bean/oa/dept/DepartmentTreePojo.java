package com.aiiju.bean.oa.dept;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 
 * 返回给前端树状结构类
@Author  太一 
@Date 创建时间：2016年11月10日 上午9:22:38 
*/
public class DepartmentTreePojo implements Serializable{
	private static final long serialVersionUID = 7913560884123556220L;
	//id
	private Integer id;
	//部门名称
	private String name;
	//上级部门id
	private Integer parentDeptId;
	//下级部门list
	private List<DepartmentTreePojo> children;
//	//组织下的职位列表或者是人员列表，统一用这个list来存储
//	private List<Map<String,Object>> commonList;
	//是否部门
	private boolean isDept;
	
	//部门架构树--前段组件使用，内部有两个(children_num，parent_num)属性
	private Map<String,Object> relationship;
	//当前在编人数
	private Integer curPersonNum;
	//缺编人数
	private Integer lackPersonNum;
	//当前编制人数
	private Integer personNum;
	//用户ID
	private Integer userId;
	//用户名称
	private String userName;
	/**
     * 组织层次
     */
	
	private Byte level;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(Integer parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
	public List<DepartmentTreePojo> getChildren() {
		return children;
	}
	public void setChildren(List<DepartmentTreePojo> children) {
		this.children = children;
	}
	public boolean getIsDept() {
		return isDept;
	}
	public void setIsDept(boolean isDept) {
		this.isDept = isDept;
	}
	public Map<String, Object> getRelationship() {
		return relationship;
	}
	public void setRelationship(Map<String, Object> relationship) {
		this.relationship = relationship;
	}
	public Integer getCurPersonNum() {
		return curPersonNum;
	}
	public void setCurPersonNum(Integer curPersonNum) {
		this.curPersonNum = curPersonNum;
	}
	public Integer getLackPersonNum() {
		return lackPersonNum;
	}
	public void setLackPersonNum(Integer lackPersonNum) {
		this.lackPersonNum = lackPersonNum;
	}
	public Integer getPersonNum() {
		return personNum;
	}
	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Byte getLevel() {
		return level;
	}
	public void setLevel(Byte level) {
		this.level = level;
	}
	
	
}
