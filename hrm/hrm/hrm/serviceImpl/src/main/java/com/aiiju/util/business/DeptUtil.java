package com.aiiju.util.business;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.dept.DepartmentTreePojo;

/**
 * 
 * @ClassName: DeptUtil
 * @Description: 组织公用接口
 * @author 琪琪
 * @date 2016年11月22日 上午11:02:23 
 *
 */
public class DeptUtil {
	/**
	 * 根据组织list返回组织tree（当CommonList不为空的时候获取组织下的人员，职位树）
	 * @param list
	 * @return
	 */
	public static List<DepartmentTreePojo> getChildDepartments(List<DepartmentTreePojo> list,List<DepartmentTreePojo> commonList){
		List<DepartmentTreePojo> returnList=new ArrayList<DepartmentTreePojo>();
		if(list != null&&!list.isEmpty()) {
			
			for(DepartmentTreePojo department:list){
				department.setIsDept(true);
				if (null!=department.getParentDeptId() && department.getParentDeptId()==0 ) {
	                recursionFn(list,commonList, department);
	                returnList.add(department);
	            }
			}	
		}
		
        return  returnList;
	}
	/**
	 * 递归获取节点下子部门
	 * @param list
	 * @param department
	 */
	
	private static void recursionFn(List<DepartmentTreePojo> list,List<DepartmentTreePojo> commonList, DepartmentTreePojo department){
		Map<String,Object> relationMap = new HashMap<String,Object>();
		//获取子部门
		List<DepartmentTreePojo> childList=getChildList(list,department);
		List<DepartmentTreePojo> allList = new ArrayList<DepartmentTreePojo>();
		allList.addAll(childList);
	    //如果 列表不为空 获取组织下的相应信息  ---目前支持员工，职位
	    if(commonList!=null&&!commonList.isEmpty()){
	    	List<DepartmentTreePojo> deptCommonList=getInfoList(commonList,department);
	    	allList.addAll(deptCommonList);
//	    	department.setCommonList(deptCommonList);
	    }
	    department.setChildren(allList);
	    relationMap.put("children_num", childList.size());
	    relationMap.put("parent_num", 1);
	    department.setRelationship(relationMap);
	    if(childList!=null&&!childList.isEmpty()){
	    	for(DepartmentTreePojo d:childList){
	    		recursionFn(list,commonList, d);
	    	}
	    }
	}
	/**
	 * 根据上级节点和部门列表获取该节点的子部门
	 * @param list
	 * @param department
	 * @return
	 */
    private static List<DepartmentTreePojo> getChildList(List<DepartmentTreePojo> list, DepartmentTreePojo department) {
        List<DepartmentTreePojo> departmentList = new ArrayList<DepartmentTreePojo>();
        if(list!=null&&!list.isEmpty()){
        	for(DepartmentTreePojo d:list){
        		 Integer parentId=d.getParentDeptId();
        		 if (parentId!=null&&parentId.equals(department.getId())) {
                     departmentList.add(d);
                 }
        	}
        }
        return departmentList;
    }
    /**
     * 根据部门和所有信息（目前支持-员工，职位），组装该部门下的相应信息，用于树状图
     * @param commonList
     * @param department
     * @return
     */
    private static List<DepartmentTreePojo> getInfoList(List<DepartmentTreePojo> commonList,DepartmentTreePojo department){
    	List<DepartmentTreePojo> subList = new ArrayList<DepartmentTreePojo>();
        if(commonList!=null&&!commonList.isEmpty()){
        	for(DepartmentTreePojo d:commonList){
        		 Integer parentId=d.getParentDeptId();
        		 if (parentId!=null&&parentId.equals(department.getId())) {
        			 subList.add(d);
                 }
        	}
        }
        return subList;
//    	List<Map<String,Object>> subList=new ArrayList<Map<String,Object>>();
//    	if(commonList!=null&&!commonList.isEmpty()){
//    		for(Map<String,Object> info:commonList){
//    			if(info.get("deptId").toString().equals(department.getId().toString())){
//    				Map<String,Object> m = new HashMap<String,Object>();
//    				m.put("id", info.get("id"));
//    				m.put("name", info.get("name"));
//    				subList.add(m);
//    			}
//    		}
//    	}
//    	return subList;
    };
    
    /**
	 * 根据传入部门，返回该部门及其所有子部门--子方法
	 * @param queryDepts  传入部门
	 * @param allDepts   部门列表
	 * @param showDepts  返回显示的所有部门
	 * @return:
	 * @throws:
	 */
	public static List<Department> getAllDepts(List<Department> queryDepts,List<Department> allDepts,List<Department> showDepts){
		List<Department> subDepts = getSubDepts(queryDepts, allDepts);
		showDepts.addAll(queryDepts);
		if(subDepts!=null&&!subDepts.isEmpty()){
			getAllDepts(subDepts, allDepts, showDepts);
		}
		return showDepts;
	}
	/**
	 * 根据传入部门，获取该部门的子部门
	 * @param dept 传入部门
	 * @param allDepts 部门列表
	 * @return
	 */
	public static List<Department> getSubDepts(List<Department> depts,List<Department> allDepts){
		List<Department> subDepts = new ArrayList<Department>();
		for(Department dept : depts){
			for(Department allDept : allDepts){
				Integer parentId=allDept.getParentDeptId();
				if(parentId!=null&&parentId.equals(dept.getId())){
					subDepts.add(allDept);
				}
			}
		}
		return subDepts;
	}
    
}
