package com.aiiju.util.business;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: TreeUtil
 * @Description: 树结构公共方法
 * @author BZ
 * @date 2016年12月9日 上午11:02:23 
 *
 */
public class TreeUtil {
	
	/**
	 * 获取树结构列表
	 * @param allList
	 * @return
	 */
	public static List<?> getTree(List<?> allList) {
		List<Object> treeList = new ArrayList<Object>();
		if (allList != null && !allList.isEmpty()) {
			for (Object pt : allList) {
				String parentId = "";
				try {
					parentId = pt.getClass().getDeclaredField("parentId").get(pt).toString();
					if ("0".equals(parentId)) {
						recursionTree(pt, allList);
						treeList.add(pt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return treeList;
	}
	/**
	 * 递归查询树
	 * @param pt
	 * @param allList
	 * @throws Exception
	 */
	private static void recursionTree(Object pt, List<?> allList) throws Exception {
		List<Object> childList = getChildList(pt, allList);
		Field f = pt.getClass().getDeclaredField("children");
		f.setAccessible(true);
		f.set(pt, childList);
		if (childList != null && !childList.isEmpty()) {
			for (Object p : childList) {
				recursionTree(p, allList);
			}
		}
	}
	/**
	 * 获取子节点列表
	 * @param pt
	 * @param allList
	 * @return
	 */
	private static List<Object> getChildList(Object pt, List<?> allList) {
		List<Object> ptList = new ArrayList<Object>();
		if (allList != null && !allList.isEmpty()) {
			for (Object p : allList) {
				try {
					Field pf = p.getClass().getDeclaredField("parentId");
					pf.setAccessible(true);
					String parentId = pf.get(p).toString();
					Field ptf = pt.getClass().getDeclaredField("id");
					ptf.setAccessible(true);
					String id = ptf.get(pt).toString();
					if (id.equals(parentId)) {
						ptList.add(p);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ptList;
	}
	
}
