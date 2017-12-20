package com.crm.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Bas_dict_t;
import com.crm.model.Bas_product_t;
import com.crm.service.DictService;


public class DictAction implements RequestAware, SessionAware {


	@Resource(name="DictService")
	private DictService dictService;
	
	private Map request;
	private	Map session;
	//数据字典
	private Bas_dict_t bas_dict_t;
	//产品
	private Bas_product_t bas_product_t;

	private int id;
	/**
	 * 用于查询字典
	 */
	private String type;
	private String name;
	private String value;
	/**
	 * 用于查询产品
	 */
	private String pname;
	private String ptype;
	private String prank;
	/**
	 * 用于库存查询
	 */
	private String pro;
	private String depot;
	

	/**
	 * 添加保存数据字典信息
	 * @return
	 */
	public String addDict(){
		if(bas_dict_t.getDict_type()==null)
		bas_dict_t.setDict_type("否");
		dictService.saveDict(bas_dict_t);
		
		request.put("bas_dict_t", bas_dict_t);
		
		return "dictCreate";
	}
	/**
	 * 添加保存产品信息
	 * @return
	 */
	public String addPro(){
		
		dictService.saveProduct(bas_product_t);
		
		
		openProduct();
		return "dictProduct";
	}
	
	/**
	 * 添加保存库存
	 * @return
	 */
	public String addSto(){
		dictService.saveProduct(bas_product_t);
		
		openStock();
		
		return "dictStock";
	}
	
	/**
	 * 修改保存库存
	 * @return
	 */
	public String modSto(){
		Bas_product_t b =dictService.find(bas_product_t.getPro_id(), Bas_product_t.class);
		b.setPro_goods_unit(bas_product_t.getPro_goods_unit());
		b.setPro_no(bas_product_t.getPro_no());
		b.setPro_depot(bas_product_t.getPro_depot());
		b.setPro_remark(bas_product_t.getPro_remark());
		dictService.saveProduct(b);
		
		openStock();
		
		return "dictStock";
	}
	
	/**
	 * 打开新建数据字典页面
	 * @return
	 */
	public String openInfoDict(){	
		openCreateDict();
		return "dictCreate";
	}
	
	/**
	 * 打开产品信息页面
	 * @return
	 */
	public String openProduct(){	
		List<Bas_product_t> list=dictService.showProduct(null, null, null);
		request.put("proList", list);
		return "dictProduct";
	}
	/**
	 * 打开新建产品信息页面
	 * @return
	 */
	public String openInfoPro(){	
		
		return "proCreate";
	}
	/**
	 * 打开新建库存信息页面
	 * @return
	 */
	public String openInfoSto(){	
		openStock();
		return "stoCreate";
	}
	/**
	 * 打开库存信息页面
	 * @return
	 */
	public String openStock(){	
		List<Bas_product_t> list=dictService.showStock(null, null);
		request.put("stoList", list);
		return "dictStock";
	}
	/**
	 * 打开字典编辑页面
	 * @return
	 */
	public String openAlter(){
		Bas_dict_t b=dictService.find(id, Bas_dict_t.class);
		request.put("dict", b);
		openCreateDict();
		return "dictAlter";
	}
	/**
	 * 打开产品编辑页面
	 * @return
	 */
	public String proAlter(){
		Bas_product_t b=dictService.find(id, Bas_product_t.class);
		request.put("pro", b);
		openProduct();
		return "proAlter";
	}
	/**
	 * 打开库存编辑页面
	 * @return
	 */
	public String stoAlter(){
		Bas_product_t b=dictService.find(id, Bas_product_t.class);
		request.put("sto", b);
		openStock();
		return "stoAlter";
	}
	/**
	 * 打开数据字典基础页面
	 * @return
	 */
	public String searchDict(){
		List<Bas_dict_t> list=dictService.showDict(type, name, value);
		request.put("dictList", list);
		return "dictOpen";
	}
	/**
	 * 产品查询
	 * @return
	 */
	public String searchPro(){
		List<Bas_product_t> list=dictService.showProduct(pname, ptype, prank);
		request.put("proList", list);
		return "dictProduct";
	}
	/**
	 * 库存查询
	 */
	public String searchStock(){
		List<Bas_product_t> list=dictService.showStock(pro,depot);
		request.put("stoList", list);
		return "dictStock";
	}
	/**
	 * 具体数据查询---可以省略
	 * @return	
	 * public String searchDictData(){
		List<Bas_dict_t> list=dictService.showDict(id);
		request.put("dictDataList", list);
		//System.out.println(id);
		return "dictAlter";
	}
	 */

	public DictService getDictService() {
		return dictService;
	}
	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	public Bas_dict_t getBas_dict_t() {
		return bas_dict_t;
	}
	public void setBas_dict_t(Bas_dict_t bas_dict_t) {
		this.bas_dict_t = bas_dict_t;
	}
	/*public String listDict(){
		List<Bas_dict_t> list=dictService.showDict();
		request.put("dicttList", list);
		return "dictList";
	}*/
	/**
	 * 打开创建基础数据界面
	 */
	public String openCreateDict(){
		List<Bas_dict_t> list=dictService.showDict(null, null, null);
		request.put("dictList", list);
		return "dictOpen";
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getPrank() {
		return prank;
	}
	public void setPrank(String prank) {
		this.prank = prank;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bas_product_t getBas_product_t() {
		return bas_product_t;
	}
	public void setBas_product_t(Bas_product_t bas_product_t) {
		this.bas_product_t = bas_product_t;
	}
	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getPro() {
		return pro;
	}
}
