package com.aiiju.dao.oa.common;

import java.util.List;
import java.util.Map;
import com.aiiju.bean.oa.config.FileInfo;

public interface ICommonPageDao {
	
	/**
	 * 附件上传，插入附件表记录
	 * @param params
	 * @return
	 */
	public Long insertFileRecord(FileInfo fileInfo);

	/**
	 * 查询附件信息列表
	 * @param params
	 * @return
	 */
	public List<FileInfo> getFileInfoList(Map<String, Object> params);
	
	/**
	 * 查询附件列表记录数
	 * @param params
	 * @return
	 */
	public int getFileInfoListCount(Map<String, Object> params);
	
	/**
	 * 删除附件记录
	 * @param params
	 */
	public void delFileRecord(Map<String,Object> params);
	
	/**
	 * 根据ID批量修改文件信息
	 * @param params
	 * @return
	 */
	public int batchUpdateFileInfo(Map<String,Object> params);
	
	/**
	 * 修改上传文件
	 */
	public int updateFileInfo(FileInfo fileInfo);

	public int insertFileInfoSelective(FileInfo fileInfo);
	/**
	 * 通过主键id查询单个文件记录
	 * 2017.04.28  10:00
	 * @param id
	 * @return
	 */
	public FileInfo selectById(Long id);
}