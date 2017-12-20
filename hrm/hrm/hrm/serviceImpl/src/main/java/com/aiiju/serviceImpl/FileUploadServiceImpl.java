package com.aiiju.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiiju.bean.oa.config.FileInfo;
import com.aiiju.dao.oa.common.ICommonPageDao;
import com.aiiju.service.IFileUploadService;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.enums.ExcelTemplateEnum;
import com.aiiju.util.page.Pagination;
@Service("fileUploadService")
public class FileUploadServiceImpl extends CommonPageService implements IFileUploadService{

	@Autowired
	private ICommonPageDao commonPageDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertFileRecord(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<FileInfo> fileInfoList = (List<FileInfo>) params.get("fileInfoList");
		String ids = "";
		for(FileInfo fi : fileInfoList){
			fi.setBusinessId(Integer.valueOf(params.get("businessId").toString()));
			fi.setFid(Long.valueOf(params.get("fId").toString()));
			fi.setCompanyId(Long.valueOf(params.get("companyId").toString()));
			commonPageDao.insertFileRecord(fi);
			Long returnId = fi.getId();
			ids = ids + String.valueOf(returnId) + ",";
		}
		resultMap.put("ids", ids.substring(0, ids.length()-1));
		resultMap.put("fileInfoList", fileInfoList);
		return resultMap;
	}

	/**
	 *  获取文件信息   isTemplate 0--非模板下载，1--模板下载,2--文件查询
	 */
	@Override
	public Map<String,Object> getFileInfoList(Map<String, Object> params) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		String isTemplate = params.get("isTemplate") == null ? null : params.get("isTemplate").toString();
		if("1".equals(isTemplate)){
			FileInfo fi = new FileInfo();
			String tempName = params.get("tempName") == null ? null : params.get("tempName").toString();
			String fileName = ExcelTemplateEnum.getName(tempName);
			//存放到服务器上的ExcelTemplate路径下面
			String dirPath = this.getClass().getResource("/").getPath() + "ExcelTemplate/";
			fi.setUrl(dirPath + tempName+".xls");
			fi.setFileName(fileName+".xls");
			fileInfoList.add(fi);
		}else if("0".equals(isTemplate)){
			fileInfoList = commonPageDao.getFileInfoList(params);
		}else{
			int num=commonPageDao.getFileInfoListCount(params);
			int pageNum=Integer.parseInt(params.get("pageNum").toString());
			int pageSize=Integer.parseInt(params.get("pageSize").toString());
			Pagination pt=new Pagination(num,pageNum,pageSize);
			params.put("pageSize", pageSize);
			params.put("startRow", pt.getStartPos());
			fileInfoList = commonPageDao.getFileInfoList(params);
			resultMap.put("page", pt);
		}
		resultMap.put("fileInfoList", fileInfoList);
		return resultMap;
	}

	@Override
	public int updateFileInfo(FileInfo fileInfo) {
		if(fileInfo!=null){
			return commonPageDao.updateFileInfo(fileInfo);
		}
		return 0;
	}

	@Override
	public int saveFileInfo(FileInfo fileInfo) {
		if(fileInfo!=null){
			return commonPageDao.insertFileInfoSelective(fileInfo);
		}
		return 0;
	}


}
