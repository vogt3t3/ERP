package com.aiiju.util.common;

import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiiju.bean.oa.staff.User;
import com.aiiju.util.business.PYUtil;
import com.aiiju.util.business.PYUtil.Type;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @author BZ
 * @Description 排序工具 2016年6月20日
 */
@SuppressWarnings("rawtypes")
public class SortUtil implements Comparator {
	
	private static Logger log = LoggerFactory.getLogger(SortUtil.class);
	
	public int compare(Object value1, Object value2) {
		String s1 = String.valueOf(this.getFieldValueByName("pyName", value1));
		String s2 = String.valueOf(this.getFieldValueByName("pyName", value2));
		return Collator.getInstance(Locale.ENGLISH).compare(s1, s2);
	}

	@SuppressWarnings("unchecked")
	public static List sort(List strList) {
		SortUtil comp = new SortUtil();
		Collections.sort(strList, comp);
		return strList; 
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
	
	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		User user1 = new User();
		user1.setAge(1);
		user1.setName("兵长");
		User user2 = new User();
		user2.setAge(2);
		user2.setName("奇牙");
		User user3 = new User();
		user3.setAge(3);
		user3.setName("伊泽");
		User user4 = new User();
		user4.setAge(4);
		user4.setName("哪吒");
		User user5 = new User();
		user5.setAge(5);
		user5.setName("二郎");
		User user6 = new User();
		user6.setAge(6);
		user6.setName("神笔");
		User user7 = new User();
		user7.setAge(7);
		user7.setName("闪电");
		User user8 = new User();
		user8.setAge(8);
		user8.setName("太一");
		User user9 = new User();
		user9.setAge(9);
		user9.setName("你大爷");
		User user10 = new User();
		user10.setAge(10);
		user10.setName("琪琪");
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);
		userList.add(user5);
		userList.add(user6);
		userList.add(user7);
		userList.add(user8);
		userList.add(user9);
		userList.add(user10);
		for(User u : userList){
			PYUtil util = new PYUtil();
			String pyName = util.toPinYin(u.getName(),"",Type.UPPERCASE);
			u.setPyName(pyName);
			u.setFirstPYName(pyName.substring(0,1));
		}
		SortUtil.sort(userList);
		for(User u : userList){
			System.out.println(u.getFirstPYName() + u.getName() + u.getPyName());
		}
	}
}
