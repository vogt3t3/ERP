package com.aiiju.bean.oa.workStage;
import java.io.Serializable;
import java.util.List;
/**
 * 工作汇报-实体类---APP列表查询使用
 * @author BZ
 *
 */
public class WorkReportList implements Serializable{
	
	private static final long serialVersionUID = -1494146036297986211L;
	
	private String date;
	private String day;
	private List<WorkReport> list;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<WorkReport> getList() {
		return list;
	}
	public void setList(List<WorkReport> list) {
		this.list = list;
	}
	
}
