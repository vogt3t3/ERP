package com.aiiju.service;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aiiju.bean.oa.config.FileInfo;

/**
 * 
 * @ClassName: IFileUploadService
 * @Description: 文件上传 接口
 * @author BZ
 * @date 2016年10月28日 上午11:02:23 
 *
 */
@Repository
public interface IFileUploadService {
	
	/**
	 * 附件上传之后，插入附件表记录
	 * @param params
	 * @return
	 */
	public Map<String, Object> insertFileRecord(Map<String,Object> params);
	
	/**
	 * 查询附件信息列表
	 * @param params
	 * @return
	 */
	public Map<String,Object> getFileInfoList(Map<String,Object> params);
	
	/**
	 * 删除附件记录
	 * @param params : id-附件ID
	 * @return
	 */
	public String delFileRecord(Map<String,Object> params);
	
	/**
	 * 修改上传文件
	 * @param params
	 * @return
	 */
	public int updateFileInfo(FileInfo fileInfo);
	
	/**
	 * 保存单个文件对象
	 * @param fileInfo
	 * @return
	 */
	public int saveFileInfo(FileInfo fileInfo);
}
