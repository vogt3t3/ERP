package com.aiiju.util.pushMessage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import com.aiiju.util.enums.SceneTypeEnum;
import com.aiiju.util.http.APIRequestUtil;



/**
 * 
 * @ClassName: Pagination 
 * @Description: 推送信息工具类
 * @author 琦玉 
 * @date 2016年8月20日 上午10:46:47 
 *
 */
public class PushUtil implements Serializable {

	
	/**
	 * 通知消息
	 * @param params
	 * @param sceneTypeId 情景类型
	 * @param type 1、待处理工作  2、工作汇报回复 3、工作汇报 6、审批回复 7、公告
	 */
	public static void sendNoticesByPhp(Map<String,Object> params,int sceneTypeId,int type){
		
		String sceneStr = SceneTypeEnum.getValue(sceneTypeId); 
		
		String realUserIds=params.get("pushUserIds").toString();
		String notices=params.get("pushTitle").toString();
		//获取真正不重复的userIds 推送消息
		sendMessageByPhpAPI(params.get("companyId").toString(), notices,null,realUserIds,type,"php.api.send.message");
		//发送openfire的IM消息
		sendMessageByPhpAPI(params.get("companyId").toString(), "["+sceneStr+"]"+notices,Integer.parseInt(params.get("userId").toString()) ,realUserIds,type,"php.api.send.imMessage");
		
		}
	

	/**
	 * 通过php接口发送各类消息
	 * @param visitId 企业Id
	 * @param notices 提醒文本
	 * @param userIds 需要发送的userIds 逗号分隔
	 * @param type 7是通知消息(还未明确)
	 * @param methodKey 请求PHPAPI的方法名
	 */
	public static void sendMessageByPhpAPI(String companyId,String notices,Integer curUserId,String userIds,int type,String methodKey){
		Map<String,Object> sendArgs=new HashMap<String,Object>();
		sendArgs.put("visit_id",companyId);
		sendArgs.put("msg_type", type);
		//userIds="67991,62378,68272";
		
		if( curUserId!=null){
			sendArgs.put("from_im_no", curUserId);
		}
		sendArgs.put("im_no", userIds);
		sendArgs.put("message", notices);
		
		String urlName ="php.api.url";
		APIRequestUtil.getResultFromPhpAPI(urlName, methodKey, sendArgs,"2","2");
	}
		

	
		
}
