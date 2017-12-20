package com.aiiju.bean.oa.attendance.bo;

/**
 * APP端获取当前用户当天考勤规则返回实体对象
 * @author 小辉
 *
 */
public class SignRuleForAppBo {
    private Integer id;
    private String longitude;
	private String latitude;
	private int offset;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
