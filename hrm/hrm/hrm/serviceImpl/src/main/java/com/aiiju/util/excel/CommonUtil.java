package com.aiiju.util.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: CommonUtil 
 * @Description: 通用工具类
 * @author 哪吒 
 * @date 2016年11月10日 上午9:54:32 
 *
 */
public class CommonUtil {

	/**
	 * 比较两个list是否完全相同（此方法是为判断导入Excel的表头而写）
	 * @param originList
	 * @param targetList
	 * @return
	 */
	public static boolean compareList(List<String> originList, ArrayList<Object> targetList) {
		if (null != originList && null != targetList) {
			if (originList.size() == targetList.size()) { // 大小保持一致
				for (int i = 0; i < originList.size(); i++) {
					if (!originList.get(i).equals(targetList.get(i))) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 比较两个list是否完全相同
	 * @param originList
	 * @param targetList
	 * @return
	 */
	public static boolean compareList(List<String> originList, List<Long> targetList) {
		if (null != originList && null != targetList) {
			if (originList.size() == targetList.size()) { // 大小保持一致
				for (int i = 0; i < originList.size(); i++) {
					if (!originList.get(i).equals(String.valueOf(targetList.get(i)))) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

}
