package com.aiiju.dao.oa.workStage;

import java.util.Map;

import com.aiiju.bean.oa.workStage.Draft;

/**
 * 
 * @ClassName: IDraftDao 
 * @Description: 草稿DAO
 * @author BZ
 * @date 2017年2月16日 下午5:34:56 
 *
 */

public interface IDraftDao {

	/**
	 * 添加草稿
	 * @param params
	 */
	public void addDraft(Draft dr);
	
	/**
	 * 查询草稿
	 * @param params
	 */
	public Draft getDraft(Map<String, Object> params);
	
	/**
	 * 更新草稿
	 * @param params
	 */
	public void updateDraft(Map<String, Object> params);
	
	/**
	 * 删除草稿
	 * @param params
	 */
	public void deleteDraft(Map<String, Object> params);
}
