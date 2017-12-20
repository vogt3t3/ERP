package com.aiiju.serviceImpl.salary;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.salary.SpwSalaryBatch;
import com.aiiju.bean.oa.salary.SpwSalaryPay;
import com.aiiju.bean.oa.salary.SpwSalaryTemp;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.salary.ISpwSalaryBatchDao;
import com.aiiju.dao.oa.salary.ISpwSalaryPayDao;
import com.aiiju.dao.oa.salary.ISpwSalaryTempDao;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.salary.ISpwSalaryPayService;
import com.aiiju.util.common.EncryptUtil;
import com.aiiju.util.common.MD5;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.RandomCodeUtil;
import com.aiiju.util.common.RandomCodeUtil.RandomCodeLevel;
import com.aiiju.util.common.Result;
import com.aiiju.util.common.Tools;
import com.aiiju.util.enums.SalaryPay;
import com.aiiju.util.excel.CommonUtil;
import com.aiiju.util.excel.ExcelUtil;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;
import com.aiiju.util.http.APIRequestUtil;
import com.aiiju.util.page.Pagination;
import com.aiiju.util.redis.JedisUtil;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.internal.LinkedTreeMap;

@Service("spwSalaryPayService")
public class SpwSalaryPayServiceImpl implements ISpwSalaryPayService {

	private static Logger log = LoggerFactory.getLogger(SpwSalaryPayServiceImpl.class);
	
	@Autowired
	private ISpwSalaryPayDao salaryPayDao;
	@Autowired
	private ISpwSalaryBatchDao batchDao;
	@Autowired
	private ISpwSalaryTempDao tempDao;
	@Autowired
	private IUserDao userDao;
	 
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> leadSpwSalaryPays(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SpwSalaryPay> list = new ArrayList<SpwSalaryPay>();
		String companyId = params.get("companyId").toString();
		String pid = params.get("pid").toString();
		if (pid != null && !"".equals(pid)) {
			salaryPayDao.deleteById(Long.parseLong(pid));
			batchDao.deleteById(Long.parseLong(pid));
		}
		List<FileInfo> fileInfoList = (List<FileInfo>) params.get("fileInfoList");
		// 获取流
		List<InputStream> streamList = (List<InputStream>) params.get("streamList");

		ArrayList<ArrayList<Object>> salaryPays = null;
		try {
			salaryPays = ExcelUtil.readRows(streamList.get(0));
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.READ_FILE_FAIL);
		}
		if (salaryPays == null || salaryPays.isEmpty()) {
			return null;
		}
		// 获取对应表字段
		String[] salaryPayB = SalaryPay.getAll().toArray(new String[SalaryPay.getAll().size()]);

		// 判断字段长度 字段是否一致
		if (!CommonUtil.compareList(SalaryPay.getAll(), salaryPays.get(1))) {
			throw new BizException(BizExceptionMessage.EXCEL_MODEL_ERROR);
		}
		salaryPays.remove(0);
		salaryPays.remove(0);
		SpwSalaryPay pay = null;
		for (ArrayList<Object> salaryPay : salaryPays) {
			// 创建对象接受数据
			pay = new SpwSalaryPay();
			Object[] salary = salaryPay.toArray(new Object[salaryPay.size()]);
			// 循环对应字段属性赋值
			for (int m = 0; m < salary.length; m++) {
				String val = salary[m].toString();
				// 判断字段一致
				if ("部门".equals(salaryPayB[m])) {
					pay.setDeptName(val);
					continue;
				}
				if ("手机".equals(salaryPayB[m])) {
					log.info("---------------------------手机:"+val+"----------------------------------------");
					boolean b = Tools.checkMobileNumber(val.trim());
					log.info("---------------------------手机SB:"+b+"----------------------------------------");
					if (b) {
						pay.setPhone(val.trim());
						continue;
					} else {
						throw new BizException(BizExceptionMessage.PHONE_IS_EXIST);
					}
				}
				if ("邮箱".equals(salaryPayB[m])) {
					log.info("---------------------------邮箱:"+val+"----------------------------------------");
					boolean b = Tools.checkEmail(val.trim());
					log.info("---------------------------邮箱SB:"+b+"----------------------------------------");
					if (b) {
						pay.setEmail(val.trim());
						continue;
					} else {
						throw new BizException(BizExceptionMessage.EMIAL_IS_EXIST);
					}
				}
				Field field;
				try {
					// 反射给对象属性赋值
					field = pay.getClass().getDeclaredField(SalaryPay.getIndex(salaryPayB[m]));
					field.setAccessible(true);
					String type = field.getType().toString();// 获取字段类型
					if (type.endsWith("String")) {
						field.set(pay, val); // 给属性设值
					} else if (type.endsWith("int") || type.endsWith("Integer")) {
						field.set(pay, Integer.valueOf(val));
					} else if (type.endsWith("long") || type.endsWith("Long")) {
						field.set(pay, Long.valueOf(val));
					} else if (type.endsWith("byte") || type.endsWith("Byte")) {
						field.set(pay, Byte.valueOf(val));
					} else if (type.endsWith("short") || type.endsWith("Short")) {
						field.set(pay, Short.valueOf(val));
					} else if (type.endsWith("double") || type.endsWith("Double")) {
						field.set(pay, Double.valueOf(val));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pay.setCompanyId(Long.parseLong(companyId));
			list.add(pay);
		}
		Set<String> set = new HashSet<String>();
		for (SpwSalaryPay ssp : list) {
			set.add(ssp.getPhone());
		}
		if (set.size() < list.size()) {
			map.put("error", "导入数据手机号码重复");
			throw new BizException(BizExceptionMessage.PROP_ERROR, map.get("error").toString());
		}

		// List<String>
		// for (int i = 0; i < list.size(); i++) {
		// String no=list.get(i).getPhone();
		// for (String string : phones) {
		// if(no.equals(string)){
		// map.put("error" + i, "第"+i+"错误:手机"+no+"已存在。");
		// }
		// }
		// }
		// if(StringUtils.isEmpty(map.get("error").toString())){
		// throw new BizException(BizExceptionMessage.PROP_ERROR,
		// map.get("error").toString());
		// }
		// 放入集合
		map.put("spwSalaryPays", list);
		map.put("fileName", fileInfoList.get(0).getFileName());
		map.put("topList", SalaryPay.getAllKV());
		return map;

	}

	/*@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> sendMessageOfSalary(Map<String, Object> params) { // 请求参数pid,companyId,薪资时间，查询列

		long totaltime = System.currentTimeMillis();
		Map<String, Object> result = new HashMap<String, Object>();
		// 根据条件（批次ID）获取需要发送的邮件ID
		List<SpwSalaryPay> salaryPays = null;
		try {
			log.debug("===========获取薪酬列表开始==========" + params.get("pid"));
			long searchtime = System.currentTimeMillis();
			salaryPays = salaryPayDao.selectSalaryInfoByPid(params);
			log.debug("===========获取薪酬列表耗时==========" + (System.currentTimeMillis() - searchtime));
			log.debug("===========获取薪酬列表结束==========" + salaryPays);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}

		// 记录发送短信成功的Id
		List<String> smsSuccIds = new ArrayList<String>();
		// 记录发送邮件成功的Id
		List<String> mailSuccIds = new ArrayList<String>();
		// 获取邮件主题
		String subject = (String) params.get("mailTitle");
		// 获取公司名称（请求PHP接口）
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("visit_id", params.get("companyId"));
		Result companyInfo = APIRequestUtil.getResultFromPhpAPI("php.company.url", "php.company.getById", requestParams,
				"2", "2");
		// 获取companyName
		String companyName = "";
		if (null != companyInfo && null != companyInfo.getData()) { // 返回有数据
			LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) companyInfo.getData();
			LinkedTreeMap<String, Object> resultMap = (LinkedTreeMap<String, Object>) dataMap.get("result");
			companyName = (String) resultMap.get("company_name");
		}
		// 发送邮件
		if (null != salaryPays && !salaryPays.isEmpty()) {
			for (SpwSalaryPay salaryPay : salaryPays) {
				if (StringUtils.isEmpty(salaryPay.getEmail()) && StringUtils.isEmpty(salaryPay.getPhone())) {

				} else {
					log.debug("===========循环发送工资条流程开始==========");
					long sendmailtime = System.currentTimeMillis();
					// 组装URL
					String url = MessageFormat.format(PropertiesUtil.getPropertyByKey("search_salary_url"), salaryPay.getId(), salaryPay.getCompanyId().toString());
					// 生成4位数字随机验证码
					String checkCode = RandomCodeUtil.getRandomCode(6, RandomCodeLevel.SIMPLE, false);
					// 组装邮件发送内容
					String mailContent = assembleContent((String) params.get("salaryTime"), checkCode, url, companyName,
							(String) params.get("userName"));
					// 发送邮件
					// 获取资源路径
					String path = SpwSalaryPayServiceImpl.class.getClassLoader().getResource("").getPath();
					// 此处有个坑。path以"/"开始的，本地测试时，读取不到"/全路径"，所以要截取；而在linux下，必须要以"/"开头
					
					 * if (path.startsWith("/")) { path = path.substring(1); }
					 
					boolean mailResult = MailUtil.syncSendWithImg(subject, mailContent, path + "img/bg.png",
							path + "img/title.png", salaryPay.getEmail());
					log.info("=============发送邮件耗时=============" + (System.currentTimeMillis() - sendmailtime));
					log.debug("=============发送邮件是否成功=============" + mailResult);
					// 长链接组装成短链接（请求PHP接口,测试环境注释掉）
					long sendsmstime = System.currentTimeMillis();
					String shortURL = "";
					Map<String, Object> shortURLParams = new HashMap<String, Object>();
					shortURLParams.put("url", url);
					Result shortURLResult = APIRequestUtil.getResultOfShortURL(shortURLParams, "2");
					if (null != shortURLResult && StringUtils.equals("0", shortURLResult.getCode())) { // shortURL
						LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) shortURLResult
								.getData();
						if (null != dataMap.get("url")) {
							shortURL = (String) dataMap.get("url");
						}
					}
					// 组装短信内容
					Result smsResult = null;
					if (StringUtils.isNotEmpty(shortURL)) {
						String smsContent = MessageFormat.format(PropertiesUtil.getPropertyByKey("sms.msg.salary.content"),
								params.get("salaryTime"), checkCode, shortURL, companyName);
						// 请求短信接口发送短信
						Map<String, Object> smsParams = new HashMap<String, Object>();
						smsParams.put("phone", salaryPay.getPhone());
						smsParams.put("message", smsContent);
						smsParams.put("type", "tz");
						smsResult = APIRequestUtil.getResultOfSMS(smsParams, "2");
					}
					log.info("=============发送工资条短信耗时=============" + (System.currentTimeMillis() - sendsmstime));
					log.debug("=============发送短信是否成功=============" + smsResult.getCode());
					if (mailResult) { // 邮件发送成功
						// 记录成功的Id
						log.debug("=========组装邮件发送成功的ID=============");
						mailSuccIds.add(salaryPay.getId().toString());
					}
					if (null != smsResult && StringUtils.equals("0", smsResult.getCode())) { // 短信发送成功
						log.debug("=========组装短信发送成功的ID=============");
						smsSuccIds.add(salaryPay.getId().toString());
					}
					if (mailResult || (null != smsResult && StringUtils.equals("0", smsResult.getCode()))) {
						// redis存放验证码和查询字段，命名规则：验证码 id_checkCode
						// id_showFields；过期时间：24h
						try {
							long redistime = System.currentTimeMillis();
							JedisUtil.getInstance().set(salaryPay.getId() + "_checkCode", checkCode, 24 * 60 * 60);
							log.info("=============redis存储耗时=============" + (System.currentTimeMillis() - redistime));
						} catch (Exception e) {
							throw new BizException(BizExceptionMessage.DB_ERROR, "redis缓存数据失败");
						}
					}
				}
			}
		}

		if ((null != mailSuccIds && !mailSuccIds.isEmpty()) || (null != mailSuccIds && !mailSuccIds.isEmpty())) {
			// 批量更新薪酬表中is_send字段和is_email_send字段
			try {
				if (null != mailSuccIds && !mailSuccIds.isEmpty()) {
					log.debug("=========邮件发送成功的ID=============");
					params.put("mailSuccIds", mailSuccIds);
				}
				if (null != smsSuccIds && !smsSuccIds.isEmpty()) {
					log.debug("=========短信发送成功的ID=============");
					params.put("smsSuccIds", smsSuccIds);
				}
				log.debug("=========更新薪酬发送状态开始=============");
				Long updatepaytime = System.currentTimeMillis();
				salaryPayDao.updateStatusByPid(params);
				log.info("=========更新薪酬发送状态耗时=============" + (System.currentTimeMillis() - updatepaytime));
				log.debug("=========更新薪酬发送状态成功=============");

			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "更新薪酬发送状态失败");
			}

			// 更新薪酬批次表中的is_send字段（参数：pid）
			try {
				params.put("isSend", 1);
				params.put("id", params.get("pid"));
				params.put("salaryName", params.get("salaryTime") + "工资单");
				log.debug("=========更新薪酬批次发送状态开始=============");
				Long updatetime = System.currentTimeMillis();
				batchDao.updateStatusById(params);
				log.info("=========更新薪酬批次发送状态耗时=============" + (System.currentTimeMillis() - updatetime));
				log.debug("=========更新薪酬批次发送状态成功=============");
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "更新薪酬批次发送状态失败");
			}
			
			// 更新薪酬批次表状态成功后，发送短信通知工资条发送者
			long salarysucctime = System.currentTimeMillis();
			Result smsResult = null;
			String smsSuccContent = PropertiesUtil.getPropertyByKey("sms.msg.salarysucc.content");
			// 请求短信接口发送短信
			Map<String, Object> smsParams = new HashMap<String, Object>();
			smsParams.put("phone", params.get("phone"));
			smsParams.put("message", smsSuccContent);
			smsParams.put("type", "tz");
			smsResult = APIRequestUtil.getResultOfSMS(smsParams, "2");
			log.info("=============发送短信耗时=============" + (System.currentTimeMillis() - salarysucctime));
			log.debug("=============发送短信是否成功=============" + smsResult.getCode());

			// 存储当前薪酬的查询字段
			long insertsalarytime = System.currentTimeMillis();
			String showFields = (String) params.get("showFields");
			String[] showFieldsArray = showFields.split(",");
			List<SpwSalaryTemp> list = new ArrayList<SpwSalaryTemp>();
			for (int i = 0; i < showFieldsArray.length; i++) {
				SpwSalaryTemp salaryTemp = new SpwSalaryTemp();
				salaryTemp.setOriginFeild(showFieldsArray[i]);
				salaryTemp.setFeildName(SalaryPay.getName(showFieldsArray[i]));
				salaryTemp.setFeildSort(i + 1);
				salaryTemp.setPid(Long.valueOf(params.get("pid").toString()));
				salaryTemp.setCompanyId(Long.valueOf(params.get("companyId").toString()));
				list.add(salaryTemp);
			}
			try {
				tempDao.insertTemp(list);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR, "批量增加薪酬的查询字段失败");
			}
			log.debug("=========存储当前薪酬的查询字段耗时=============" + (System.currentTimeMillis() - insertsalarytime));
		} else {
			throw new BizException(BizExceptionMessage.SYS_ERROR, "发送工资条失败");
		}

		log.info("=========发送工资条总耗时=============" + (System.currentTimeMillis() - totaltime));
		return result;
	}*/

	@Override
	@Transactional
	public long inserts(Map<String, Object> params) {
		String companyId = params.get("companyId").toString();
		String userId = params.get("userId").toString();
		String fileName = params.get("fileName").toString();
		String pswPath = PropertiesUtil.getPropertyByKey("passwordPath");
		String pswKey = PropertiesUtil.getProperty(pswPath, "passwordKey");
		String pid = params.get("pid").toString();
		if (pid != null && !"".equals(pid)) {
			return Long.parseLong(pid);
		}
		// 存入 薪资批次表
		SpwSalaryBatch batch = new SpwSalaryBatch();
		batch.setSalaryName(fileName);
		batch.setUserId(Long.parseLong(userId));
		batch.setCompanyId(Long.parseLong(companyId));
		batchDao.insert(batch);
		String spwSalaryPays = params.get("spwSalaryPays").toString();
		List<SpwSalaryPay> list = JSONArray.parseArray(spwSalaryPays, SpwSalaryPay.class);
//		Department dept = null;
		for (SpwSalaryPay ssp : list) {
//			dept = new Department();
//			dept.setName(ssp.getDeptName());
//			dept.setCompanyId(Long.parseLong(companyId));
//			Department deptId = salaryPayDao.selectDeptName(dept);
//			if (deptId == null) {
//				throw new BizException(BizExceptionMessage.DETY_NAME_EXIST);
//			} else {
//				ssp.setDeptId(Long.parseLong(deptId.getId().toString()));
//			}
			ssp.setPswKey(pswKey);
			ssp.setPid(batch.getId());
			ssp.setCompanyId(Long.parseLong(companyId));
			ssp.setIsDel(0);
			ssp.setIsSend(0);
			ssp.setIsWithdraw(0);
		}
		salaryPayDao.inserts(list);
		return batch.getId();
	}

	/**
	 * 组装邮件内容
	 * 
	 * @param salaryTime
	 *            工资条时间
	 * @param checkCode
	 *            校验码
	 * @param salaryPay
	 *            薪资信息
	 * @return
	 */
	/*private String assembleContent(String salaryTime, String checkCode, String url, String companyName,
			String userName) {
		StringBuilder content = new StringBuilder();
		content.append(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
				.append("<html>").append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
				.append("<title>爱聚HR工资条发放</title>").append("<style type=\"text/css\">")
				.append(".content{font-family:\"Microsoft Yahei\";font-size: 16px;width: 833px; height: 602px;margin: 0 auto;position: relative;}")
				.append(".bgimg{position:absolute;left: 0px;top: 10px;z-index: -1;width: 100%!important;height: 96%!important;width: expression(this.width > 150 ? \"150px\" : this.width)!important;}")
				.append(".title{width: 327px;height: 43px;padding-top: 150px; padding-left: 253px;}")
				.append(".p1{padding-top: 20px; padding-left: 90px;}")
				.append(".p2{text-indent: 2em;padding-top: 5px; padding-left: 90px;word-break:break-all; width:680px;}")
				.append(".bottom{padding-top: 60px;text-align: center; color: #ACACAC;}")
				.append(".p3{padding-top: 1px;font-size: 14px;font-weight: bold;}").append("</style>").append("</head>")
				.append("<body").append("<div class=\"content\">")
				.append("<img src=\"cid:bgimg\" class=\"bgimg\"/>")
				.append("<div class=\"title\"><img src=\"cid:titleimg\"></div>").append("<div><p class=\"p1\">您好：</p>")
				.append("<p class=\"p2\">您的").append(salaryTime).append("工资条已发放，随机验证码为").append(checkCode)
				.append("，点击下方链接输入验证码查看你的工资条（").append(companyName).append(")</p><p class=\"p2\"><a href=\"")
				.append(url).append("\">").append(url).append("</a></p></div>")
				.append("<div class=\"bottom\"><p class=\"p3\">").append(salaryTime).append("</p><p class=\"p3\">本工资条由")
				.append(companyName + "的管理员" + userName)
				.append("，通过爱聚HR发送，如有任何问题，请联系管理员</p><p class=\"p3\">Powered By 爱聚HR</p></div></div>").append("</body>")
				.append("</html>");

		return content.toString();
	}*/

	/**
	 * 根据用户名查询历史记录
	 */
	@Override
	public Map<String, Object> getSalaryBatchList(Map<String, Object> params) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<SpwSalaryBatch> list = new ArrayList<SpwSalaryBatch>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String userId = params.get("userId") == null ? null : params.get("userId").toString();
		int num = batchDao.getSalaryBatchCountByParams(params);
		int pageNum = Integer.parseInt(params.get("pageNum").toString());
		int pageSize = Integer.parseInt(params.get("pageSize").toString());
		Pagination pt = new Pagination(num, pageNum, pageSize);
		params.put("pageSize", pageSize);
		params.put("startRow", pt.getStartPos());
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(companyId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "用户ID或公司ID不能为空");
		} else {
			list = batchDao.queryHistorySalaryBatch(params);
		}
		m.put("result", list);
		m.put("page", pt);
		return m;
	}

	/**
	 * 工资单发送历史明细记录-----导入预览 通用
	 */
	@Override
	public Map<String, Object> getSalaryPayList(Map<String, Object> params) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<SpwSalaryPay> list = new ArrayList<SpwSalaryPay>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String pid = params.get("pid") == null ? null : params.get("pid").toString();
//		String pswKey = PropertiesUtil.getPropertyByKey("passwordKey");
		String pswPath = PropertiesUtil.getPropertyByKey("passwordPath");
		String pswKey = PropertiesUtil.getProperty(pswPath, "passwordKey");
		params.put("pswKey", pswKey);
		int num = salaryPayDao.getSalaryInfoCountByParams(params);
		int pageNum = Integer.parseInt(params.get("pageNum").toString());
		int pageSize = Integer.parseInt(params.get("pageSize").toString());
		Pagination pt = new Pagination(num, pageNum, pageSize);
		params.put("pageSize", pageSize);
		params.put("startRow", pt.getStartPos());
		if (StringUtils.isEmpty(pid) || StringUtils.isEmpty(companyId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "批次号或公司ID不能为空");
		} else {
			list = salaryPayDao.selectSalaryInfoByPid(params);
		}
		m.put("result", list);
		m.put("page", pt);
		m.put("topList", SalaryPay.getAllKV());
		return m;
	}

	@Override
	public Map<String, Object> validateCheckCodeById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取请求参数id（这是工资条ID）
		String from = params.get("from") == null ? null : params.get("from").toString();
		String salaryId = params.get("id") == null ? null : params.get("id").toString();
		if (null != from && StringUtils.equals("html", from)) {
			salaryId = EncryptUtil.decryptStr(salaryId);
		}
		
		// redis中取出验证码
		String checkCodeFromRedis = JedisUtil.getInstance().get(salaryId + "_checkCode");
		if (StringUtils.isNotEmpty(checkCodeFromRedis)) {
			if (!StringUtils.equals(checkCodeFromRedis, (String) params.get("checkCode"))) { // 验证码错误
				throw new BizException(BizExceptionMessage.CHECKCODE_ERROR);
			}
		} else {
			// 无效的checkCode
			throw new BizException(BizExceptionMessage.VCODE_IS_DISABLE);
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> sendSMSOfCheckCode(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 查询手机号是否是本人
		/*List<User> userList = null;
		try {
			userList = userDao.selectByExample(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		if (null != userList && !userList.isEmpty()) {
			sendSMS(params);
		} else {
			throw new BizException(BizExceptionMessage.PHONE_IS_NONE);
		}*/
		if (StringUtils.isNotEmpty(String.valueOf(params.get("setPhone")))) {
			if (StringUtils.equals(String.valueOf(params.get("phone")), String.valueOf(params.get("setPhone")))) {
				sendSMS(params);
			} else {
				throw new BizException(BizExceptionMessage.PHONE_IS_NONE, "手机号不是当前登录用户注册的手机号");
			}
		} else {
			throw new BizException(BizExceptionMessage.PARAM_ERROR);
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> validateCheckCode(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String phone = (String) params.get("phone");
		// 获取验证码
		JedisUtil jedisUtil = JedisUtil.getInstance();
		String checkCodeFromRedis = jedisUtil.get(phone);
		if (null != checkCodeFromRedis) {
			if (!StringUtils.equals(checkCodeFromRedis, (String) params.get("checkCode"))) { // 验证失败
				throw new BizException(BizExceptionMessage.CHECKCODE_ERROR);
			} else { // 验证码正确
				// 删除验证码信息
				jedisUtil.del(phone);
			}
		} else {
			throw new BizException(BizExceptionMessage.VCODE_IS_DISABLE);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateSecondaryPwd(Map<String, Object> params) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		String password = (String) params.get("secondaryPwd");
		if (StringUtils.isNotEmpty(password)) {
			// 密码加密
			String encPassword = MD5.md5(password);
			try {
				User user = new User();
				user.setLoginUserId(Long.valueOf(params.get("loginUserId").toString()));
				user.setTwoLevelPwd(encPassword);
				userDao.updateUserInfoByLoginUserId(user);
			} catch (Exception e) {
				throw new BizException(BizExceptionMessage.DB_ERROR);
			}
		}
		return requestMap;
	}

	/**
	 * 发送短信
	 * 
	 * @param params
	 */
	private void sendSMS(Map<String, Object> params) {
		String phone = (String) params.get("phone");
		// 生成4位数字组成的验证码
		String vcode = RandomCodeUtil.getValidationCode(4);
		String message = MessageFormat.format(PropertiesUtil.getPropertyByKey("sms.msg.secondpwd.update"), vcode);
		params.put("message", message);
		params.put("type", "tz");
		// // 第三方短信平台发送验证码短信，返回发送状态信息
		Result result = APIRequestUtil.getResultOfSMS(params, "2");

		if (StringUtils.equals("0", result.getCode())) { // 发送短信成功
			JedisUtil jedisUtil = JedisUtil.getInstance();
			jedisUtil.set(phone, vcode, 10 * 60);
		} else {
			throw new BizException(BizExceptionMessage.GET_VCODE_ERROR);
		}
	}

	/**
	 * 获取我的工资单历史记录
	 */
	@Override
	public Map<String, Object> getMyHistorySalaryPayList(Map<String, Object> params) {
		List<SpwSalaryBatch> list = new ArrayList<SpwSalaryBatch>();
		Map<String, Object> m = new HashMap<String, Object>();
		String phone = params.get("phone") == null ? null : params.get("phone").toString();
		int num = batchDao.getMyHistorySalaryPayListCount(params);
		int pageNum = Integer.parseInt(params.get("pageNum").toString());
		int pageSize = Integer.parseInt(params.get("pageSize").toString());
		Pagination pt = new Pagination(num, pageNum, pageSize);
		params.put("pageSize", pageSize);
		params.put("startRow", pt.getStartPos());
		if (StringUtils.isEmpty(phone)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "员工号码为空");
		} else {
			list = batchDao.getMyHistorySalaryPayList(params);
		}
		m.put("result", list);
		m.put("page", pt);
		return m;
	}

	/**
	 * 获取我的工资单详细
	 */
	@Override
	public Map<String, Object> getMySalaryPay(Map<String, Object> params) {
		Map<String, Object> m = new HashMap<String, Object>();
		SpwSalaryPay ssp = new SpwSalaryPay();
		Map<String, String> displayMap = new LinkedHashMap<String, String>();
		List<SpwSalaryTemp> sstList = new ArrayList<SpwSalaryTemp>();
		String from = params.get("from") == null ? null : params.get("from").toString();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String pid = params.get("id") == null ? null : params.get("id").toString();
		if (null != from && StringUtils.equals("html", from)) {
			companyId = EncryptUtil.decryptStr(companyId);
			pid = EncryptUtil.decryptStr(pid);
		}
//		String pswKey = PropertiesUtil.getPropertyByKey("passwordKey");
		String pswPath = PropertiesUtil.getPropertyByKey("passwordPath");
		String pswKey = PropertiesUtil.getProperty(pswPath, "passwordKey");
		params.put("pswKey", pswKey);
		if (StringUtils.isEmpty(pid) || StringUtils.isEmpty(companyId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "记录ID不能为空");
		} else {
			Map<String,Object> salaryParams = new HashMap<String,Object>();
			salaryParams.put("id", pid);
			salaryParams.put("pswKey", pswKey);
			ssp = salaryPayDao.getMySalaryPay(salaryParams);
			if(ssp != null){
				params.put("pid", ssp.getPid());
				sstList = tempDao.getSalaryTemp(params);
				// 数据转换传给前端展示用
				for (SpwSalaryTemp sst : sstList) {
					displayMap.put(sst.getOriginFeild(), sst.getFeildName());
				}
			}else{
				throw new BizException(BizExceptionMessage.PROP_ERROR, "该工资单已撤回!");
			}
		}
		
		String companyName = JedisUtil.getInstance().get(params.get("pid") + "_companyName");
		String userName = JedisUtil.getInstance().get(params.get("pid") + "_userName");
		m.put("result", ssp);
		m.put("companyName", companyName);
		m.put("userName", userName);
		m.put("topList", displayMap);
		return m;
	}
	
	/**
	 * 获取我的工资单详细--APP数据展示专用
	 */
	@Override
	public Map<String, Object> getMySalaryPayForApp(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SpwSalaryPay ssp = new SpwSalaryPay();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<SpwSalaryTemp> sstList = new ArrayList<SpwSalaryTemp>();
		String companyId = params.get("companyId") == null ? null : params.get("companyId").toString();
		String pid = params.get("id") == null ? null : params.get("id").toString();
//		String pswKey = PropertiesUtil.getPropertyByKey("passwordKey");
		String pswPath = PropertiesUtil.getPropertyByKey("passwordPath");
		String pswKey = PropertiesUtil.getProperty(pswPath, "passwordKey");
		params.put("pswKey", pswKey);
		if (StringUtils.isEmpty(pid) || StringUtils.isEmpty(companyId)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "记录ID不能为空");
		} else {
			ssp = salaryPayDao.getMySalaryPay(params);
			if(ssp != null){
				params.put("pid", ssp.getPid());
				sstList = tempDao.getSalaryTemp(params);
				// 数据转换传给前端展示用
				try {
					for (SpwSalaryTemp sst : sstList) {
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("name", sst.getFeildName());
							map.put("value", this.getFieldValueByName(sst.getOriginFeild(), ssp));
							resultList.add(map);
					}
				} catch (Exception e) {
					throw new BizException(BizExceptionMessage.LOGIC_ERROR, "反射排序异常");
				}
			}else{
				throw new BizException(BizExceptionMessage.PROP_ERROR, "该工资单已撤回!");
			}
		}
		resultMap.put("result", resultList);
		return resultMap;
	}

	/**
	 * 判断是否存在二级密码，从而选择跳转到哪个页面。 isHaveSecondPwd 0--不存在 1--存在
	 */
	@Override
	public Map<String, Object> toSetOrInputPwd(Map<String, Object> params) {
		log.info("--------------------------------判断是否存在二级密码，从而选择跳转到哪个页面START--------------------------------");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String spwd = salaryPayDao.getSecondPwd(params);
		if (StringUtils.isEmpty(spwd)) {
			resultMap.put("isHaveSecondPwd", 0);
		} else {
			resultMap.put("isHaveSecondPwd", 1);
		}
		log.info("--------------------------------判断是否存在二级密码，从而选择跳转到哪个页面END--------------------------------");
		return resultMap;
	}

	/**
	 * 二级密码校验 isRight 0--密码错误；1--密码正确
	 */
	@Override
	public Map<String, Object> checkSecondPwd(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String secondaryPwd = params.get("secondaryPwd") == null ? null : params.get("secondaryPwd").toString();
		if (StringUtils.isEmpty(secondaryPwd)) {
			throw new BizException(BizExceptionMessage.PROP_ERROR, "二级密码不能为空");
		} else {
			String spwd = salaryPayDao.getSecondPwd(params);
			if (MD5.md5(secondaryPwd).equals(spwd)) {
				resultMap.put("isRight", 1);
			} else {
				resultMap.put("isRight", 0);
			}
		}
		return resultMap;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> withdrawSalaryById(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			batchDao.updateStatusById(params);
			salaryPayDao.updatesalaryPayByPid(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> delSalaryInfo(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			salaryPayDao.updateStatusByPid(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}
		return resultMap;
	}
	
	/**
	 * 反射用工具方法
	 * @param fieldName
	 * @param o
	 * @return
	 */
	private Object getFieldValueByName(String fieldName, Object o) {  
		try {    
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
			String getter = "get" + firstLetter + fieldName.substring(1);    
			Method method = o.getClass().getMethod(getter, new Class[] {});    
			Object value = method.invoke(o, new Object[] {});    
			return value;    
		} catch (Exception e) {    
			log.error(e.getMessage(),e);    
			return null;    
		}    
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> sendMessageOfSalary(Map<String, Object> params) { // 请求参数pid,companyId,薪资时间，查询列
		
		// 建立线程池，ThreadPoolExecutor(核心线程数, 线程池维护线程的最大数量, 程池维护线程所允许的空闲时间, 线程池维护线程所允许的空闲时间的单位, 线程池所使用的缓冲队列)
//		ExecutorService exc = Executors.newCachedThreadPool();  暂时没有替换，推荐使用。
		ExecutorService exc = new ThreadPoolExecutor(2, 10, 120L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());

		long totaltime = System.currentTimeMillis();
		Map<String, Object> result = new HashMap<String, Object>();
		// 根据条件（批次ID）获取需要发送的邮件ID
		List<SpwSalaryPay> salaryPays = null;
		try {
			salaryPays = salaryPayDao.selectSalaryInfoByPid(params);
		} catch (Exception e) {
			throw new BizException(BizExceptionMessage.DB_ERROR);
		}

		// 记录发送短信成功的Id
		List<String> smsSuccIds = new ArrayList<String>();
		// 记录发送邮件成功的Id
		List<String> mailSuccIds = new ArrayList<String>();
		// 获取公司名称（请求PHP接口）
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("visit_id", params.get("companyId"));
		Result companyInfo = APIRequestUtil.getResultFromPhpAPI("php.company.url", "php.company.getById", requestParams,
				"2", "2");
		// 获取companyName
		String companyName = "";
		if (null != companyInfo && null != companyInfo.getData()) { // 返回有数据
			LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) companyInfo.getData();
			LinkedTreeMap<String, Object> resultMap = (LinkedTreeMap<String, Object>) dataMap.get("result");
			companyName = (String) resultMap.get("company_name");
		}
		params.put("companyName", companyName);
		params.put("subject", params.get("mailTitle"));
		
		// 多线程中需要加上final修饰符
		final List<SpwSalaryPay> finalSalaryPays = salaryPays;
		// 发送邮件（多线程）
		exc.submit(() -> {
			
			try {
				for (SpwSalaryPay salaryPay : finalSalaryPays) {
					// 组装URL TEST
					/*String url = "https://hr.ecbao.cn/work/checkYzm.html?id=" + salaryPay.getId() + "%26companyId="
							+ salaryPay.getCompanyId();*/
					// 组装URL					
					String url = MessageFormat.format(PropertiesUtil.getPropertyByKey("search_salary_url"),
							EncryptUtil.encryptToHexStr(String.valueOf(salaryPay.getId())),
							EncryptUtil.encryptToHexStr(String.valueOf(salaryPay.getCompanyId())));
					 
					// 生成6位数字随机验证码
					String checkCode = RandomCodeUtil.getRandomCode(6, RandomCodeLevel.SIMPLE, false);
					// 判断发送邮件是否开启
					boolean mailResult = false;
					if (StringUtils.equals("ON", PropertiesUtil.getPropertyByKey("send.email"))) {
						mailResult = sendMail(salaryPay, params, url, checkCode);
					}
					boolean smsResult = sendSMS(salaryPay, params, url, checkCode);
					if (mailResult) {
						mailSuccIds.add(salaryPay.getId().toString());
					}
					if (smsResult) {
						smsSuccIds.add(salaryPay.getId().toString());
					}
					if (mailResult || smsResult) {
						// redis存放验证码和查询字段，命名规则：验证码 id_checkCode
						// id_showFields；过期时间：24h
						try {
							JedisUtil.getInstance().set(salaryPay.getId() + "_checkCode", checkCode, 24 * 60 * 60);
						} catch (Exception e) {
							throw new BizException(BizExceptionMessage.DB_ERROR, "redis缓存数据失败");
						}
					}
				}
				
				if ((null != mailSuccIds && !mailSuccIds.isEmpty()) || (null != smsSuccIds && !smsSuccIds.isEmpty())) {
					// 批量更新薪酬表中is_send字段和is_email_send字段
					try {
						if (null != mailSuccIds && !mailSuccIds.isEmpty()) {
							log.debug("=========邮件发送成功的ID=============");
							params.put("mailSuccIds", mailSuccIds);
						}
						if (null != smsSuccIds && !smsSuccIds.isEmpty()) {
							log.debug("=========短信发送成功的ID=============");
							params.put("smsSuccIds", smsSuccIds);
						}
						log.debug("=========更新薪酬发送状态开始=============");
						Long updatepaytime = System.currentTimeMillis();
						salaryPayDao.updateStatusByPid(params);
						log.info("=========更新薪酬发送状态耗时=============" + (System.currentTimeMillis() - updatepaytime));
						log.debug("=========更新薪酬发送状态成功=============");

					} catch (Exception e) {
						throw new BizException(BizExceptionMessage.DB_ERROR, "更新薪酬发送状态失败");
					}

					// 更新薪酬批次表中的is_send字段（参数：pid）
					try {
						params.put("isSend", 1);
						params.put("id", params.get("pid"));
						params.put("salaryName", params.get("salaryTime") + "工资单");
						log.debug("=========更新薪酬批次发送状态开始=============");
						Long updatetime = System.currentTimeMillis();
						batchDao.updateStatusById(params);
						// redis存放companyName,userName
						JedisUtil.getInstance().set(params.get("pid") + "_companyName", String.valueOf(params.get("companyName")), 24 * 60 * 60);
						JedisUtil.getInstance().set(params.get("pid") + "_userName", String.valueOf(params.get("userName")), 24 * 60 * 60);
						log.info("=========更新薪酬批次发送状态耗时=============" + (System.currentTimeMillis() - updatetime));
						log.debug("=========更新薪酬批次发送状态成功=============");
					} catch (Exception e) {
						throw new BizException(BizExceptionMessage.DB_ERROR, "更新薪酬批次发送状态失败");
					}
					
					// 更新薪酬批次表状态成功后，发送短信通知工资条发送者
					long salarysucctime = System.currentTimeMillis();
					Result smsResult = null;
					String smsSuccContent = PropertiesUtil.getPropertyByKey("sms.msg.salarysucc.content");
					// 请求短信接口发送短信
					Map<String, Object> smsParams = new HashMap<String, Object>();
					smsParams.put("phone", params.get("phone"));
					smsParams.put("message", smsSuccContent);
					smsParams.put("type", "tz");
					smsResult = APIRequestUtil.getResultOfSMS(smsParams, "2");
					log.info("=============发送短信耗时=============" + (System.currentTimeMillis() - salarysucctime));
					log.debug("=============发送短信是否成功=============" + smsResult.getCode());

					// 存储当前薪酬的查询字段
					long insertsalarytime = System.currentTimeMillis();
					String showFields = (String) params.get("showFields");
					String[] showFieldsArray = showFields.split(",");
					List<SpwSalaryTemp> list = new ArrayList<SpwSalaryTemp>();
					for (int i = 0; i < showFieldsArray.length; i++) {
						SpwSalaryTemp salaryTemp = new SpwSalaryTemp();
						salaryTemp.setOriginFeild(showFieldsArray[i]);
						salaryTemp.setFeildName(SalaryPay.getName(showFieldsArray[i]));
						salaryTemp.setFeildSort(i + 1);
						salaryTemp.setPid(Long.valueOf(params.get("pid").toString()));
						salaryTemp.setCompanyId(Long.valueOf(params.get("companyId").toString()));
						list.add(salaryTemp);
					}
					try {
						tempDao.insertTemp(list);
					} catch (Exception e) {
						throw new BizException(BizExceptionMessage.DB_ERROR, "批量增加薪酬的查询字段失败");
					}
					log.debug("=========存储当前薪酬的查询字段耗时=============" + (System.currentTimeMillis() - insertsalarytime));
				} else {
					throw new BizException(BizExceptionMessage.SYS_ERROR, "发送工资条失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//关闭线程池
				exc.shutdown();
			}
		});
		
		log.info("=========发送工资条总耗时=============" + (System.currentTimeMillis() - totaltime));
		return result;
	}
	
	/**
	 * 发送邮件（工资条专用）
	 * @param salaryPay 工资详情
	 * @param emailVo 邮件实体类
	 * @return
	 */
	private boolean sendMail(SpwSalaryPay salaryPay, Map<String, Object> params, String url, String checkCode) {
		boolean result = false;
		try {
			HtmlEmail email = new HtmlEmail();
			// 基础配置
			email.setDebug(Boolean.valueOf(PropertiesUtil.getPropertyByKey("mail.stmp.debug")));
			email.setCharset("utf-8");
			email.setHostName(PropertiesUtil.getPropertyByKey("mail.smtp.host"));
			email.setSmtpPort(Integer.valueOf(PropertiesUtil.getPropertyByKey("mail.smtp.port")));
			email.setAuthentication(PropertiesUtil.getPropertyByKey("mail.stmp.user"), PropertiesUtil.getPropertyByKey("mail.stmp.pwd"));
			email.setFrom(PropertiesUtil.getPropertyByKey("mail.stmp.user"), PropertiesUtil.getPropertyByKey("mail.stmp.username"));
			// 防止死锁
			email.setSocketTimeout(60 * 1000);
			email.setSocketConnectionTimeout(60 * 1000);
			// 添加发送人
			email.addTo(salaryPay.getEmail());
			
			String path = SpwSalaryPayServiceImpl.class.getClassLoader().getResource("").getPath();
			// 此处有个坑。path以"/"开始的，本地测试时，读取不到"/全路径"，所以要截取；而在linux下，路径必须要以"/"开头
			/*if (path.startsWith("/")) {
				path = path.substring(1);
			}*/
			FileSystemResource bgfile = new FileSystemResource(path + "img/bg.png");
			FileSystemResource titlefile = new FileSystemResource(path + "img/title.png");
			String bgcid = email.embed(bgfile.getFile());
			String titlecid = email.embed(titlefile.getFile());
			String content = assembleContent(String.valueOf(params.get("salaryTime")), checkCode, url,
					String.valueOf(params.get("companyName")),
					String.valueOf(params.get("userName")), bgcid, titlecid);
			email.setHtmlMsg(content);
			email.setSubject(String.valueOf(params.get("subject")));
			email.send();
			result = true;
		} catch (Exception e) {
			result = false;
		}

		return result;
	}
	
	/**
	 * 发送短信（因为发送工资条邮件和短信发送完成后有存储验证码逻辑，所以两者不可分开）
	 * @param salaryPay
	 * @param emailVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean sendSMS(SpwSalaryPay salaryPay, Map<String, Object> params, String url, String checkCode) {
		boolean result = false;
		String shortURL = "";
		Map<String, Object> shortURLParams = new HashMap<String, Object>();
		shortURLParams.put("url", url);
		Result shortURLResult = APIRequestUtil.getResultOfShortURL(shortURLParams, "2");
		if (null != shortURLResult && StringUtils.equals("0", shortURLResult.getCode())) { // shortURL
			LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) shortURLResult
					.getData();
			if (null != dataMap.get("url")) {
				shortURL = (String) dataMap.get("url");
			}
		}
		// 组装短信内容
		Result smsResult = null;
		if (StringUtils.isNotEmpty(shortURL)) {
			String smsContent = MessageFormat.format(PropertiesUtil.getPropertyByKey("sms.msg.salary.content"),
					params.get("salaryTime"), checkCode, shortURL, params.get("companyName"));
			// 请求短信接口发送短信
			Map<String, Object> smsParams = new HashMap<String, Object>();
			smsParams.put("phone", salaryPay.getPhone());
			smsParams.put("message", smsContent);
			smsParams.put("type", "tz");
			smsResult = APIRequestUtil.getResultOfSMS(smsParams, "2");
		}
		if (null != smsResult && StringUtils.equals("0", smsResult.getCode())) { // 短信发送成功
			result = true;
		}
		return result;
	}
	
	/**
	 * 组装邮件内容
	 * 
	 * @param salaryTime
	 * @param checkCode
	 * @param url
	 * @param companyName
	 * @param userName
	 * @return
	 */
	private static String assembleContent(String salaryTime, String checkCode, String url, String companyName,
			String userName, String bg, String title) {
		StringBuilder content = new StringBuilder();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
				.append("<html>").append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
				.append("<title>爱聚HR工资条发放</title>").append("<style type=\"text/css\">")
				.append(".content{font-family:\"Microsoft Yahei\";font-size: 16px;width: 833px; height: 602px;margin: 0 auto;position: relative;}")
				.append(".bgimg{position:absolute;left: 0px;top: 10px;z-index: -1;width: 100%!important;height: 96%!important;width: expression(this.width > 150 ? \"150px\" : this.width)!important;}")
				.append(".title{width: 327px;height: 43px;padding-top: 150px; padding-left: 253px;}")
				.append(".p1{padding-top: 20px; padding-left: 90px;}")
				.append(".p2{text-indent: 2em;padding-top: 5px; padding-left: 90px;word-break:break-all; width:680px;}")
				.append(".bottom{padding-top: 75px;text-align: center; color: #ACACAC;}")
				.append(".p3{padding-top: 1px;font-size: 14px;font-weight: bold;}").append("</style>").append("</head>")
				.append("<body>").append("<div class=\"content\">")
				.append("<img src=\"cid:").append(bg).append("\" class=\"bgimg\"/>")
				.append("<div class=\"title\"><img src=\"cid:").append(title).append("\"></div>")
				.append("<div><p class=\"p1\">您好：</p>")
				.append("<p class=\"p2\">您的").append(salaryTime).append("工资条已发放，随机验证码为").append(checkCode)
				.append("，点击下方链接输入验证码查看你的工资条（").append(companyName).append(")</p><p class=\"p2\"><a href=\"")
				.append(url).append("\">").append(url).append("</a></p></div>")
				.append("<div class=\"bottom\"><p class=\"p3\">").append(salaryTime).append("</p><p class=\"p3\">本工资条由")
				.append(companyName + "的管理员" + userName)
				.append("，通过爱聚HR发送，如有任何问题，请联系管理员</p><p class=\"p3\">Powered By 爱聚HR</p></div></div>").append("</body>")
				.append("</html>");

		return content.toString();
	}
}
