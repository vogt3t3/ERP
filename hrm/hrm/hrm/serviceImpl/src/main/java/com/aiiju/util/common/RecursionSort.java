package com.aiiju.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.aiiju.bean.oa.dept.Department;
import com.aiiju.bean.oa.staff.User;
/**
 * 递归排序
 * @author 琦玉
 *
 * 2016年12月15日   下午7:28:09
 */
public class RecursionSort {

	
	/**
	  * 同一个父部门的部门，放到一个value值
	  * @param  
	  * @return
	  */
	 public static List<Object> listSort(List<Department> departmentList,List<User> usersList){
		 
		 Map<Integer,List<Department>> m = new HashMap<Integer,List<Department>>();
		 for (Department department : departmentList) {
			if(m.get(department.getParentDeptId())==null){
				List<Department> l4= new ArrayList<Department>();
				l4.add(department);
				m.put(department.getParentDeptId(), l4);
			}else {
				m.get(department.getParentDeptId()).add(department);
			}
			
		}
		 List<Object> objectList =new ArrayList<>();
		 Recursion(m,0,usersList,objectList);
	  
	  return objectList;
	 }
	 /**
	  * 递归部门，每层都有子部门，并加入员工
	  * @param  
	  * @return
	  */
	 
	 public static List<Object> Recursion(Map<Integer,List<Department>> departmentMap,int i,List<User> usersList,List<Object> objectList){
		 List<Department> list = departmentMap.get(i);
		 for (Department department : list) {
			 objectList.add(department);
			 for (int j = 0; j < usersList.size(); j++) {
				if(usersList.get(j).getDeptId().equals(department.getId().longValue())){
					User remove = usersList.remove(j);
					remove.setLevel(department.getLevel().intValue()+1);
					remove.setParentDeptId(department.getId().longValue());
					objectList.add(remove);
					j--;
				}
			}
			 if(departmentMap.get(department.getId())!=null){
				 Recursion(departmentMap,department.getId(),usersList,objectList);
			 }
			 
		}
		 
		 
		return objectList;
		 
	 }
	 
	 /**
	  * 递归部门人数，父部门的人数包含其下子部门的人数加上挂在自身上的人数
	  * @param  deptList部门集合
	  * @param  level部门层级
	  * @param  dept 选择的部门
	  * @return 
	  */
	 
	 public static void  deptPersonNum(List<Department> deptList ,Byte level,Department dept ){
			 
			
			 if(level==null){
				 level = deptList.get(0).getLevel();
			 }
			 if(dept==null){
				 
				 for (Department department : deptList) {
					 if(department.getLevel()==level){
						 for(Department departmentSub : deptList){
							 if(department.getId().toString().equals(departmentSub.getParentDeptId().toString())){
									 deptPersonNum(deptList,departmentSub.getLevel(),departmentSub);
									 department.setCurPersonNum(department.getCurPersonNum()+departmentSub.getCurPersonNum());
							 }
						 }
					 }	 
				 }
		 	}else {
					for(Department departmentSub : deptList){
						 if(dept.getId().toString().equals(departmentSub.getParentDeptId().toString())){
								 deptPersonNum(deptList,departmentSub.getLevel(),departmentSub);
								 dept.setCurPersonNum(dept.getCurPersonNum()+departmentSub.getCurPersonNum());
						 	
						 }
				}
			 }	
		 
	}
		 
		 
		 
}
