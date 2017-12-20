package com.crm.aop;

/**
 * 
 类名称：soreUrl 类描述： 对拦截的实体性判断其url 创建人：胡杰雄 创建时间：2014-9-12 下午9:43:38
 */
public class sortUrl {

	public static String getUrl(String target,String action){
		String result="";
		if("com.crm.action.ClientAction".equals(target))
			result+="client/";
		else if("com.crm.action.DictAction".equals(target))
			result+="dict/";
		else if("com.crm.action.IndentAction".equals(target))
			result+="indent/";
		else if("com.crm.action.RightAction".equals(target))
			result+="right/";
		else if("com.crm.action.SaleAction".equals(target))
			result+="chance/";
		else if("com.crm.action.ServiceAction".equals(target))
			result+="service/";
		else if("com.crm.action.StatAction".equals(target))
			result+="stat/";
		int i=0;
		for (;i < action.length();i++)
		{
			if(action.charAt(i)>'A'&&action.charAt(i)<'Z')
					break;
		}
		System.out.println(result);
		result+=action.substring(0, i)+"_"+action.substring(i, action.length());	
		return result;
	}
}
