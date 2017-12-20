package com.crm.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.JFreeChart;

import com.crm.model.Cst_lst_t;
import com.crm.service.StatService;

public class StatAction implements RequestAware,SessionAware{
	
	@Resource(name="StatService")
	private StatService statService;
	
	private JFreeChart chart;
	
	private String year;
	
	private Map request;
	private	Map session;
	/**
	 * 客户贡献度分析
	 * 根据客户订单总金额进行统计
	 */
	public String offerAnalysis(){
		chart=statService.offerAnalysis(year);
		return "contributionAnalysis";
	}
	
	public String offerAnalysisList(){
		return "contributionAnalysisList";
	}
	
	
	
	/**
	 * 客户服务分析
	 * 根据服务类型对服务进行统计
	 */
	public String serviceAnalysis(){
		
		chart=statService.serviceAnalysis(year);
		return "serviceAnalysis";
	}
	
	public String serviceAnalysisList(){
		return "serviceAnalysisList";
	}
	
	
	
	/**
	 * 客户构成分析
	 * 了解各种类型客户所占比例
	 */
	public String formAnalysis(){
		chart=statService.formAnalysis();
		return "formAnalysis";
	}
	public String formAnalysisList(){
		return "formAnalysisList";
	}
	
	
	
	
	
	/**
	 * 客户流失分析
	 * 统计流失客户的流失记录
	 */
	public String lstAnalysis(){
		List<Cst_lst_t> list=statService.lstAnalysis();
		request.put("lstList", list);
		return "lstAnalysis";
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		request=arg0;
	}

	

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	
	
}
