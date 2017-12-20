package com.ajy.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.bean.oa.staff.User;
import com.aiiju.dao.oa.staff.IUserDao;
import com.aiiju.service.IFileUploadService;
import com.aiiju.service.staff.IUserService;
import com.aiiju.util.Image.ImageUtil;
import com.aiiju.util.common.ParameterUtil;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.enums.Response;
import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.DAOException;
import com.aiiju.util.http.StringUtils;
import com.ajy.web.vo.Result;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @ClassName: CommonController
 * @Description: 公共Controller
 * @author 小飞
 * @date 2016年6月13日 下午4:18:30
 *
 */

@Controller
public class CommonController { 

	private static Logger logger = Logger.getLogger(CommonController.class);
	
	@RequestMapping("/loginHrm")
	@SuppressWarnings("unchecked")
	public String loginHrm(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("---------HRM自己的登录----login.do---START------method:" + method + "-----param:" + param + "--------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		Object  redirectObject = params.get("redirect");
		//flag为1为登出
		Object flagObject=params.get("flag");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
		response.setContentType("text/html;charset=UTF-8");
		
		if(flagObject!=null&&flagObject.toString().equals("1")){
			logger.info("----------------------------登出清除session中的user----------------------------------");
			request.getSession().setAttribute("user",null);
			Result result = new Result();
			result.setCode(Response.LOGINOUT_SUCESS.getErrorCode());
			result.setMessage(Response.LOGINOUT_SUCESS.getMsg());
			String reslutJSON = JSONObject.toJSONString(result);
			try {
				response.getWriter().write(reslutJSON);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}else{
			logger.info("----------------------------登录校验手机和密码----------------------------------");
			String[] methods = method.split("/");
			String serviceName = methods[0] + "Service";
			String methodName = methods[1];
			// 获取service对象
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			Object service = webApplicationContext.getBean(serviceName);

			Result result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params, null, null);
			
			User user = null;
			try {

				if (result != null && result.getData() != null) {
					user = (User)((Map<String, Object>) result.getData()).get("user");
				}
				// 存储登录的员工信息
				request.getSession().setAttribute("user", user);
			   System.out.println("---------hrm登录id"+ "---------"+request.getSession().getId()+"-------------------");
				
				result.setCode(Response.LOGIN_SUCESS.getErrorCode());
				result.setMessage(Response.LOGIN_SUCESS.getMsg());
				
				//redirectFlag=1 大系统登录
				
				logger.info("-------------------------------redirectObject:"+redirectObject+"-------------------------------");
				String reslutJSON = JSONObject.toJSONString(result);
				response.getWriter().write(reslutJSON);
				//大系统内嵌hrm时候session验证。是否跳转
				if(redirectObject!=null&&redirectObject.toString().equals("1")){
					logger.info("-----------------------大系统内嵌hrm时候session验证START--------redirectFlag:"+redirectObject.toString()+"-------------------------------");
					return "redirect:https://hr.ecbao.cn/work";
				}
				

			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("----------------------------login.do---END----------------------------------------");
		return null;
	}
	@RequestMapping("/register")
	@SuppressWarnings("unchecked")
	public void register(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("-------------HRM自己的注册----regist.do---START------method:" + method + "-----param:" + param + "--------------");
		logger.info("----------------------------method:" + method + "----"+request.getSession().getAttribute("user")+"------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		// 获取员工信息
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		Result result=new Result();
		try {
				String[] methods = method.split("/");
				String serviceName = methods[0] + "Service";
				String methodName = methods[1];
				// 获取service对象
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				Object service = webApplicationContext.getBean(serviceName);
				 result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params, null,null);
				 //当扫码登录输出跳转url条用flushSession时，赋予user session
				 if((methodName.equals("flushSession")||methodName.equals("register"))&&result.getData()!=null){
					Map<String, Object> session=(Map<String, Object>)result.getData();
					Object o=session.get("user");
					if(o!=null){
						// 存储登录的员工信息
						User user=null;
						if(methodName.equals("flushSession")){
							user=JSONObject.parseObject(o.toString(),User.class);
						}else{
							user=(User)o;
						}
						
						request.getSession().setAttribute("user", user);
					}
				 }
				
				String reslutJSON = JSONObject.toJSONString(result);
				response.getWriter().write(reslutJSON);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------regist.do---END----------------------------------------");
	}
	
	@RequestMapping("/login")
	@SuppressWarnings("unchecked")
	public String login(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("----------------------------login.do---START----------------------------------------");
		logger.info("----------------------------method:" + method + "----------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		Object flagObject=params.get("flag");
		String redirectFlag = String.valueOf(params.get("redirect"));
		String redirectUrl = String.valueOf(params.get("redirectUrl"));

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
		response.setContentType("text/html;charset=UTF-8");
		if(flagObject!=null&&flagObject.toString().equals("1")){//1表示登出操作
			request.getSession().setAttribute("user",null);
			Result result = new Result();
			result.setCode(Response.LOGINOUT_SUCESS.getErrorCode());
			result.setMessage(Response.LOGINOUT_SUCESS.getMsg());
			String reslutJSON = JSONObject.toJSONString(result);
			try {
				response.getWriter().write(reslutJSON);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}else{
			String[] methods = method.split("/");
			String serviceName = methods[0] + "Service"; 
			String methodName = methods[1];

			// 获取service对象
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			Object service = webApplicationContext.getBean(serviceName);

			Result result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params, null, null);

			User user = null;
			try {

				if (result != null && result.getData() != null) {
					user = (User)result.getData();
				}
				// 存储登录的员工信息
				request.getSession().setAttribute("user", user);
				if (user != null) {
					result.setCode(Response.LOGIN_SUCESS.getErrorCode());
					result.setMessage(Response.LOGIN_SUCESS.getMsg());
				} else {
					result.setCode(Response.LOGIN_FAILURE.getErrorCode());
					result.setMessage(Response.LOGIN_FAILURE.getMsg());
				}
				logger.info("-------------------------------redirectFlag:"+redirectFlag+"-------------------------------");
				//大系统内嵌hrm时候session验证。是否跳转
				if("1".equals(redirectFlag)){
					request.getSession().setAttribute("user", user);
					logger.info("-----------------------大系统内嵌hrm时候session验证START--------redirectFlag:"+redirectFlag+"-------------------------------");
					return "redirect:"+redirectUrl;
					//return "redirect:https://hr.ecbao.cn/work";
					//https://hr.ecbao.cn/hrm/login.do?method=user%2FgetUser&param=%7B%22companyId%22%3A159929%2C%22loginUserId%22%3A%2284783%22%2C%22redirect%22%3A1%7D&sign=518edff39fd2e8318e1bcf159fcc01ed&redirect=1
				}
				String reslutJSON = JSONObject.toJSONString(result);
				response.getWriter().write(reslutJSON);

			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("----------------------------login.do---END----------------------------------------");
		return null;
	} 
	@RequestMapping("/api")
	@SuppressWarnings("unchecked")
	public void find(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("----------------------------api.do---START----------------------------------------");
		logger.info("----------------------------method:" + method + "----"+request.getSession().getAttribute("user")+"------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		try {
			if (sessionJudge(request, response, params)) {
				String[] methods = method.split("/");
				String serviceName = methods[0] + "Service";
				String methodName = methods[1];
				// 获取service对象
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				Object service = webApplicationContext.getBean(serviceName);
				Result result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params, null,
						null);
				if (result.getData() instanceof HSSFWorkbook) {
					HSSFWorkbook excel = (HSSFWorkbook) result.getData();
					response.setHeader("content-disposition", "attachment;filename="
							+ new String((excel.getSheetName(0)).getBytes("gbk"), "iso8859-1") + ".xls");
					OutputStream ouputStream;
					ouputStream = response.getOutputStream();
					excel.write(ouputStream);
					ouputStream.flush();
					ouputStream.close();
				} else {
					String reslutJSON = JSONObject.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
					response.getWriter().write(reslutJSON);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------api.do---END----------------------------------------");
	}
	
	@RequestMapping("/regist")
	@SuppressWarnings("unchecked")
	public void regist(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("----------------------------regist.do---START----------------------------------------");
		logger.info("----------------------------method:" + method + "----------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		// 获取员工信息
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		try {
				String[] methods = method.split("/");
				String serviceName = methods[0] + "Service";
				String methodName = methods[1];
				// 获取service对象
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				Object service = webApplicationContext.getBean(serviceName);
				Result result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params, null,
						null);
				if (result.getData() instanceof HSSFWorkbook) {
					HSSFWorkbook excel = (HSSFWorkbook) result.getData();
					response.setHeader("content-disposition", "attachment;filename="
							+ new String((excel.getSheetName(0)).getBytes("gbk"), "iso8859-1") + ".xls");
					OutputStream ouputStream;
					ouputStream = response.getOutputStream();
					excel.write(ouputStream);
					ouputStream.flush();
					ouputStream.close();
				} else {
					String reslutJSON = JSONObject.toJSONString(result);
					response.getWriter().write(reslutJSON);
				}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------regist.do---END----------------------------------------");
	}

	/**
	 * 文件上传统一入口--目前只针对两个入口开发-》附件上传（insertFileRecord），excel导入（excelImport）
	 * 
	 * @param request
	 * @param method
	 * @param param
	 * @param response
	 */
	@RequestMapping("/upload")
	@SuppressWarnings("unchecked")
	public void upload(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("----------------------------upload.do---START----------------------------------------");
		logger.info("----------------------------method:" + method + "----------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		try {
			if (sessionJudge(request, response, params)) {
				String[] methods = method.split("/");
				String serviceName = methods[0] + "Service";
				String methodName = methods[1];

				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
						request.getSession().getServletContext());
				// 过滤掉的文件类型
				String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".php", ".jsp" };
				List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
				List<InputStream> streamList = new ArrayList<InputStream>();
				if (multipartResolver.isMultipart(request)) {
					// 转换成多部分request
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
					// 取得request中的所有文件名
					Iterator<String> iter = multiRequest.getFileNames();
					while (iter.hasNext()) {
						FileInfo fi = new FileInfo();
						// 记录上传过程起始时的时间，用来计算上传时间
						int pre = (int) System.currentTimeMillis();
						// 取得上传文件
						MultipartFile file = multiRequest.getFile(iter.next());
						if ("insertFileRecord".equals(methodName)) {
							if (file != null) {
								// 取得当前上传文件的文件名称
								String originalFilename = file.getOriginalFilename();
								String fileType = originalFilename.split("\\.", 2)[1];
								// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
								if (originalFilename.trim() != "") {
									for (int temp = 0; temp < errorType.length; temp++) {
										// 文件名的第一个.后面的为文件后缀
										if (fileType.endsWith(errorType[temp])) {
											throw new IOException(originalFilename + ": Wrong File Type");
										}
									}
									String dirPath = PropertiesUtil.getPropertyByKey("file_location");
									String myFileName = "Upload" + System.currentTimeMillis() + "." + fileType;
									String path = dirPath + myFileName;
									File dir = new File(dirPath);
									if (!dir.exists() && !dir.isDirectory()) {
										dir.mkdir();
									}
									File localFile = new File(path);
									file.transferTo(localFile);
									// 设置file对象作后续的存入附件表操作
									fi.setFileName(originalFilename);
									fi.setType(fileType);
									//拼接成全路径(在数据库保存全路径) file_url=https://hr.ecbao.cn/upload/
									String fullUrl = PropertiesUtil.getPropertyByKey("file_url") + myFileName;
									fi.setUrl(fullUrl);
									fi.setFileSize(file.getSize());
								}
							}
						} else {
							streamList.add(file.getInputStream());
							fi.setFileName(file.getOriginalFilename());
						}
						// 记录上传该文件后的时间
						int finaltime = (int) System.currentTimeMillis();
						logger.info("---------------------" + "上传文件耗时：" + (finaltime - pre) + "-------------------");
						fileInfoList.add(fi);
					}
				}
				// 获取service对象
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				Object service = webApplicationContext.getBean(serviceName);

				Result result = this.getResult(service.getClass().getInterfaces()[0], service, methodName, params,
						fileInfoList, streamList);
				response.setHeader("Access-Control-Allow-Origin", "*");
				// response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=UTF-8");
				String reslutJSON = JSONObject.toJSONString(result);
				response.getWriter().write(reslutJSON);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------upload.do---END----------------------------------------");
	}

	/**
	 * 文件下载统一入口
	 * 
	 * @param param---path:文件路径
	 *            fileUpload/getFileInfoList
	 * @param response
	 */
	@RequestMapping("/download")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void download(String param, String method, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----------------------------download.do---START----------------------------------------");
		logger.info("----------------------------method:" + method + "----------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		String[] methods = method.split("/");
		String serviceName = methods[0] + "Service";
		String methodName = methods[1];
		// 获取service对象
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		Object service = webApplicationContext.getBean(serviceName);
		Class clazz = service.getClass().getInterfaces()[0];
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		// 设置输出的格式
		response.reset();
		response.setContentType("text/html;charset=UTF-8");
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len;
		try {
			if (sessionJudge(request, response, params)) {
				Method serviceMethod = clazz.getMethod(methodName, Map.class);
				Object object = serviceMethod.invoke(service, params);
				Map<String, Object> resultMap = (Map<String, Object>) object;
				List<FileInfo> fileInfoList = (List<FileInfo>) resultMap.get("fileInfoList");
				String fileName = fileInfoList.get(0).getFileName();
				String url = fileInfoList.get(0).getUrl();
				response.setHeader("content-disposition",
						"attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1"));
				// 读到流中
				InputStream inStream = new FileInputStream(url);
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------download.do---END----------------------------------------");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Result getResult(Class clazz, Object obj, String methodName, Map<String, Object> params,
			List<FileInfo> fileInfoList, List<InputStream> streamList) {
		Result result = new Result();
		try {
			// 执行方法，返回结果
			Method serviceMethod = clazz.getMethod(methodName, Map.class);
			if (fileInfoList != null && fileInfoList.size() > 0) {
				params.put("fileInfoList", fileInfoList);
			}
			if (streamList != null && streamList.size() > 0) {
				params.put("streamList", streamList);
			}
			Object object = serviceMethod.invoke(obj, params);
			result.setData(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			Throwable cause = e.getCause();
			if (cause instanceof DAOException) {
				result.setCode(((DAOException) cause).getErrorCode());
				result.setMessage(cause.getMessage());
			} else if (cause instanceof BizException) {
				result.setCode(((BizException) cause).getErrorCode());
				result.setMessage(cause.getMessage());
			} else {
				result.setCode("1");
				result.setMessage("执行失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setCode("1");
			result.setMessage("执行失败!");
		}
		return result;
	}
	@Autowired
	private IUserDao dao;
	
	/**
	 * session校验,提取出来,本地测试时注释部分代码
	 * @param request
	 * @param params
	 * @return
	 */
	private boolean sessionJudge(HttpServletRequest request, HttpServletResponse response, Map<String, Object> params){
		User currUser = null;
		logger.info("===================================SESSION验证START===========================================");
		
		//解决app调用接口登录失败问题
		String method = request.getParameter("method");
		String companyId = params.get("companyId")==null? null:params.get("companyId").toString();
		String userId = params.get("userId")==null? null:params.get("userId").toString();
		String from = params.get("from")==null? null:params.get("from").toString();
		String token = params.get("token")==null? null:params.get("token").toString();
		if(StringUtils.isNotBlank(from)&& from.equals("APP")){
			//APP 当有参数时(loginUserId), 需要验证TOKEN
			if(StringUtils.isNotBlank(userId)){
				
				//app端调用接口则从数据库查询(大系统和HR 用户ID同意,但是APP穿的user)
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId", companyId);
				map.put("id", userId);
				User userDB = dao.seleteById(map);
				if(userDB!=null){
					currUser = userDB;
				}
				//当APP 没有参数(loginUserId)，接口跳过session判断，不需要保存user信息到params
			}else{
				String [] methods = {"register/sendCheckCode",
						"workReport/changeLike","register/validateCheckCode","user/getUsers","user/getNewUsers",
						"department/getDepartmentList","flowInfo/getFlowInfoEnums","attendanceRule/getCurrentServerTime"
				};
				for (String string : methods) {
					if(string.equalsIgnoreCase(method)){
						return true;
					}
				}
			}
			
		}else{
			// PC端从SESSION中获取用户信息
			Object o = request.getSession().getAttribute("user");
			if(o!=null){
				currUser=(User)o;
			}
			if(currUser ==null){
				currUser = new User();
				/*currUser.setId(new Long(94463));
				currUser.setPhone("14033338888");
				currUser.setCompanyId(new Long(169359));
				currUser.setLoginUserId(new Long(94463));*/
				
				/*currUser.setId(new Long(41469));//idaxiong  管理员
				currUser.setPhone("13094753850");
				currUser.setCompanyId(new Long(34530));
				currUser.setLoginUserId(new Long(39354));
				
				currUser.setId(new Long(94344));//13025027641
				currUser.setPhone("13025027641");
				currUser.setCompanyId(new Long(169262));
				currUser.setLoginUserId(new Long(94344));*/
				
				currUser.setId(new Long(86658));//佩奇
				currUser.setPhone("18768180165");
				currUser.setCompanyId(new Long(61));
				currUser.setLoginUserId(new Long(87177));
				
				/*currUser.setId(new Long(93202));//路飞
				currUser.setPhone("18814808042");
				currUser.setCompanyId(new Long(61));
				currUser.setLoginUserId(new Long(93202));*/
				
				/*currUser.setId(new Long(88352));//小辉
				currUser.setPhone("18530998725"); 
				currUser.setCompanyId(new Long(61));
				currUser.setLoginUserId(new Long(89596));
				
				currUser.setId(new Long(84837));//测试
				currUser.setPhone("14061234567");
				currUser.setCompanyId(new Long(34530));
				currUser.setLoginUserId(new Long(92287));
				
				currUser.setId(new Long(64660));//陈卓一 管理员
				currUser.setPhone("18668189987");
				currUser.setCompanyId(new Long(139353));
				currUser.setLoginUserId(new Long(62607));
				
				currUser.setId(new Long(74679));//带权限的普通用户14081234567
				currUser.setPhone("14081234567");
				currUser.setCompanyId(new Long(34530));
				currUser.setLoginUserId(new Long(92389));
				
				currUser.setId(new Long(84850));//带权限的普通用户14061234568
				currUser.setPhone("14061234568");
				currUser.setCompanyId(new Long(34530));
				currUser.setLoginUserId(new Long(92288));*/
			}
			
		}
		try {
			if(currUser != null){
				params.put("userId", currUser.getId());
				params.put("empNo", currUser.getUserNo());
				params.put("phone", currUser.getPhone()); 
				params.put("companyId", currUser.getCompanyId()); 
				params.put("loginUserId", currUser.getLoginUserId());
				params.put("userName", currUser.getName());
			}else{
		 		logger.info("===================================SESSION验证失败===========================================");
				Result result = new Result();
				result.setCode(Response.LOGIN_FAILURE.getErrorCode());
				result.setMessage(Response.LOGIN_FAILURE.getMsg());
				String reslutJSON = JSONObject.toJSONString(result);
				response.getWriter().write(reslutJSON);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * PC端个人中心页面---上传头像
	 */
	@Autowired
	private IFileUploadService fileUplodService;
    @RequestMapping(value = "/uploadUserHeadImg")
    public void uploadImg(@RequestParam(value="imgStr", required = true) String imgStr,String companyId,String userId,String oper,String fileId,HttpServletRequest request,HttpServletResponse response){
    	JSONObject resultJson = new JSONObject();
    	OutputStream out = null;
    	if(StringUtils.isBlank(companyId,userId,oper)){
    		resultJson.put("code", 400);
    		resultJson.put("message", "请求参数为空!");
        }else if(StringUtils.isBlank(imgStr)){
    		resultJson.put("code", 404);
    		resultJson.put("message", "后台未接收到您上传的文件,请重新尝试");
    	}else if(StringUtils.isNotBlank(imgStr)&&StringUtils.isNotBlank(companyId,userId,oper)){
    		Base64 decoder = new Base64();
            byte[] imgBytes = null;
            try {
            	//1.获取上传头像的文件类型
            	String fileType = "png";//(由于经过前端cropbox插件的处理后的图片均为png格式,此处写成固定值)
            	//2.对文件流进行Base64解码得到文件的字节流数组
            	imgBytes = decoder.decode(imgStr.split(",")[1]);
                for (int i = 0; i < imgBytes.length; ++i) {
                    if (imgBytes[i] < 0) {//调整异常数据
                    	imgBytes[i] += 256;
                    }
                }
                //头像在服务器上的目录路径
    			String dirPath = PropertiesUtil.getPropertyByKey("file_location");
    			//头像在数据库中保存相对路径(此处仅保存文件名)
    			String fileName = "userHeadImg" + userId+System.currentTimeMillis() + "." + fileType;
    			//文件上传绝对路径(包含文件名)
    			String path = dirPath + fileName;
    			File dir = new File(dirPath);
    			if (!dir.exists() && !dir.isDirectory()) {
    				dir.mkdir();
    			}
    			out = new FileOutputStream(path);
    			//将文件写入服务器的硬盘上
                out.write(imgBytes);
                out.flush();
                //文件上传成功之后将文件信息保存到数据库中
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFid(new Long(userId));
                fileInfo.setBusinessId(5);//表示头像
                fileInfo.setCompanyId(new Long(companyId));
                //由于前端的特殊上传插件此处无法获取到上传文件的原名称因此存储处理后的新文件名
                fileInfo.setFileName(fileName);
                fileInfo.setFileSize(new Long(imgBytes.length));
                fileInfo.setType(fileType);
                //保存全路径
                fileInfo.setUrl(PropertiesUtil.getPropertyByKey("file_url")+fileName);
                
                if("add".equals(oper)){
                	//首次上传
                	fileInfo.setCreateDate(new Date());
                    fileInfo.setUpdateDate(new Date());
                	fileUplodService.saveFileInfo(fileInfo);
                	resultJson.put("code", 0);
                    resultJson.put("message", "头像上传成功！");
                    resultJson.put("fileInfo", fileInfo);
                }else if("update".equals(oper)&&StringUtils.isNotBlank(fileId)){
                	//修改操作
                	fileInfo.setId(new Long(fileId));
                	fileInfo.setUpdateDate(new Date());
                	fileUplodService.updateFileInfo(fileInfo);
                	resultJson.put("code", 0);
                    resultJson.put("message", "头像修改成功！");
                    resultJson.put("fileInfo", fileInfo);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                resultJson.put("code", 500);
                resultJson.put("message", "服务器发生异常,请稍候重试");
            }finally{
            	if (out != null) {  
                    try {  
                    	out.close();  
                    } catch (IOException e1) {  
                        e1.printStackTrace();  
                    }  
                }  
            }
            try {
            	response.setContentType("text/json;charset=utf-8");
    			response.getWriter().print(resultJson.toString());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
	 * app端上传附件通用接口 
	 * 由于app端要对上传的图片进行缩略图的处理,所以新开个接口供app上传图片用
	 * @param request
	 * @param method
	 * @param param
	 * @param response
	 */
	@RequestMapping("/uploadFileForApp")
	@SuppressWarnings("unchecked")
	public void uploadFileForApp(HttpServletRequest request, String method, String param, HttpServletResponse response) {
		logger.info("----------------------------app调用上传接口uploadFileForApp:START----------------------------------------");
		logger.info("----------------------------method:" + method + "----------------------------------------");
		logger.info("----------------------------param:" + param + "----------------------------------------");
		// 参数转成Map
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(param, Map.class);
		try {
			JSONObject resultJson = new JSONObject();
			List<FileInfo> returnFiles = new ArrayList<FileInfo>();
			if (sessionJudge(request, response, params)) {
				String[] methods = method.split("/");
				String methodName = methods[1];
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				// 过滤掉不允许的文件类型
				String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".php", ".jsp" };
				if (multipartResolver.isMultipart(request)) {
					// 转换成多部分request
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
					// 取得request中的所有文件名
					Iterator<String> iter = multiRequest.getFileNames();
					
					while (iter.hasNext()) {
						FileInfo fileInfo = new FileInfo();
						// 取得上传文件
						MultipartFile multipartFile = multiRequest.getFile(iter.next());
						if (multipartFile != null) {
							// 取得当前上传文件的文件名称
							String originalFilename = multipartFile.getOriginalFilename();
							//String fileType = originalFilename.split("\\.", 2)[1];
							// 获取图片扩展名
			       	    	String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")+1,originalFilename.length());
							if (originalFilename.trim() != "") {
								for (int temp = 0; temp < errorType.length; temp++) {
									// 文件名的第一个.后面的为文件后缀
									if (fileType.endsWith(errorType[temp])) {
										throw new IOException(originalFilename + ": Wrong File Type");
									}
								}
								String file_location_app = PropertiesUtil.getPropertyByKey("file_location_app");
								File dir = new File(file_location_app);
								if (!dir.exists() && !dir.isDirectory()) {
									//目录不存在则创建一个
									dir.mkdir();
								}
								
								//保证得到一个唯一的文件名,如userFileFromApp286ea1693ed8471ebeabd9f2a1c47f0f.jpg
								String uuid = UUID.randomUUID().toString().replaceAll("-", "");
								String fileName = "userFileFromApp" + uuid +"."+ fileType;
								
								File localFile = new File(file_location_app+fileName);
								//1.先将原文件上传至服务器(目前文件与项目在同一台服务器上)
								multipartFile.transferTo(localFile);
								
								BufferedImage bufImage = ImageIO.read(new FileInputStream(localFile));
								String thumbName = "";
								if(bufImage!=null){
									//2.如果上传文件是图片则将原图进行缩略图处理
									ImageUtil.thumbnailImage(localFile, bufImage.getWidth(), bufImage.getHeight(), "Thumb_", false);
									//缩略图文件名,如 ：Thumb_userFileFromApp20174261493201685573.jpg
									thumbName = "Thumb_"+fileName;
								}
								
								//3.准备将文件信息保存到数据库表中
								fileInfo.setFileName(originalFilename);
								fileInfo.setType(fileType);
								String httpFilePath = PropertiesUtil.getPropertyByKey("file_url_app");
								//4.拼接成全路径
								String userFileUrl = httpFilePath+fileName;
								fileInfo.setUrl(userFileUrl);
								fileInfo.setFileSize(multipartFile.getSize());
				                fileInfo.setCompanyId(new Long(params.get("companyId").toString()));
				                
								if("saveFileInfo".equals(methodName)){
				                	//文件信息保存到库中
									fileInfo.setCreateDate(new Date());
						            fileInfo.setUpdateDate(new Date());
				                	fileUplodService.saveFileInfo(fileInfo);
				                	//缩略图访问路径
				                	fileInfo.setThumbUrl(httpFilePath+thumbName);
				                	resultJson.put("code", 0);
				                    resultJson.put("message", "上传成功！");
				                }else if("uploadHeadImg".equals(methodName)){
				                	//此接口用于app端上传或修改上传头像
				                	String userId = params.get("userId")==null?null:params.get("userId").toString();
				                	//查询当前登录用户的老头像
				                	Map<String,Object> map = new HashMap<String,Object>();
				        			map.put("fId", userId);
				        			map.put("businessId", 5);//5表示头像
				        			map.put("isTemplate", 0);
				        			Map<String, Object> fileMap = fileUplodService.getFileInfoList(map);
				        			List<FileInfo> fileInfoList = (List<FileInfo>) fileMap.get("fileInfoList");
				        			FileInfo oldFile = null;
				        			if(fileInfoList!=null&&fileInfoList.size()>0){
				        				oldFile = fileInfoList.get(0);
				        			}
				        			fileInfo.setFid(new Long(userId));
				        			fileInfo.setBusinessId(5);
				        			if(oldFile!=null){
				        				//修改文件信息
					                	fileInfo.setId(oldFile.getId());
					                	fileInfo.setUpdateDate(new Date());
					                	fileUplodService.updateFileInfo(fileInfo);
					                	resultJson.put("code", 0);
					                    resultJson.put("message", "修改头像成功！");
				        			}else{
				        				//保存文件信息
										fileInfo.setCreateDate(new Date());
							            fileInfo.setUpdateDate(new Date());
					                	fileUplodService.saveFileInfo(fileInfo);
					                	resultJson.put("code", 0);
					                    resultJson.put("message", "上传头像成功！");
				        			}
				                	fileInfo.setUrl(userFileUrl);
				                }
								returnFiles.add(fileInfo);
							}
						}
					}
					try {
		            	response.setContentType("text/json;charset=utf-8");
		            	resultJson.put("fileInfo", returnFiles);
		    			response.getWriter().print(resultJson.toString());
		    		} catch (IOException e) {
		    			resultJson.put("code", 500);
		    			resultJson.put("message", "抱歉,服务器发生异常！");
		    			response.getWriter().write(resultJson.toString());
		    			e.printStackTrace();
		    		}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("----------------------------app端调用上传接口uploadFileForApp:END----------------------------------------");
	}
}
