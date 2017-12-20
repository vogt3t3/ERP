package com.ajy.web.vo;

public class RequestParam {

	/**
	 * （原始数据）商品id
	 */
	private Long num_id;
	/**
	 * 商户id（公司）
	 */
	private Long visit_id;

	/**
	 * 平台ID
	 */
	private Integer plat_from;

	/**
	 * 店铺类型
	 */
	private Long special_id;

	/**
	 * 库存id
	 */
	private Long sku_id;

	private int current_page;

	private int page_size;

	public Long getVisit_id() {
		return visit_id;
	}

	public void setVisit_id(Long visit_id) {
		this.visit_id = visit_id;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public Long getNum_id() {
		return num_id;
	}

	public void setNum_id(Long num_id) {
		this.num_id = num_id;
	}

	public Integer getPlat_from() {
		return plat_from;
	}

	public void setPlat_from(Integer plat_from) {
		this.plat_from = plat_from;
	}

	public Long getSpecial_id() {
		return special_id;
	}

	public void setSpecial_id(Long special_id) {
		this.special_id = special_id;
	}

	public Long getSku_id() {
		return sku_id;
	}

	public void setSku_id(Long sku_id) {
		this.sku_id = sku_id;
	}

}
