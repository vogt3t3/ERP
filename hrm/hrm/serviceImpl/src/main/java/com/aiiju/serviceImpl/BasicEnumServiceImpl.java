package com.aiiju.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.aiiju.bean.common.BasicEnum;
import com.aiiju.bean.common.BasicEnumTree;
import com.aiiju.bean.common.City;
import com.aiiju.dao.oa.common.IBasicEnumDao;
import com.aiiju.service.IBasicEnumService;
import com.aiiju.util.common.Constant;
import com.alibaba.fastjson.JSONObject;

/**
 * 基础枚举表实现类
 * @author qiqi
 * @date 2016-10-26 11:11:11
 */
@Service("basicEnumService")
public class BasicEnumServiceImpl implements IBasicEnumService{
	
	
	@Autowired
	private IBasicEnumDao basicEnumDao;
	@Autowired
    private JedisPool jedisPool;
	@SuppressWarnings("rawtypes")
	@Override
	public List<BasicEnum> getBasicEnumListByParams(Map<String, Object> params) {
		
		//解析成列表
		List<BasicEnum> list=null;
		//获取参数
		String type=params.get("type")==null?null:params.get("type").toString();
		//获取redis对象
		Jedis redis=jedisPool.getResource();
		//获取keyName
		String keyName=getBasicType(type);
		//获取对应keyName的value
		Map<String, String> result=redis.hgetAll(keyName);
		
		//假如 result为空的话 从数据库取出，并且放到redis
		if(result!=null&&!result.isEmpty()){
			//从redis取出list
			List<BasicEnum> redisList=new ArrayList<BasicEnum>();
	        for(Map.Entry entry: result.entrySet()) { 
	        	BasicEnum be= JSONObject.parseObject(entry.getValue().toString(),BasicEnum.class); 
	        	redisList.add(be);
	        } 
	        redis.close();
	        return redisList;
		}else{
			list=basicEnumDao.getBasicEnumListByParams(params);
			if(list!=null&&!list.isEmpty()){
				for(BasicEnum be:list){
					String id=be.getId().toString();
					//存储列表
					redis.hset(keyName,id,JSONObject.toJSONString(be));
				}
			}
			redis.close();
			return list;
		}
	}

	
	public String getBasicType(String type){
		String result=Constant.EMPTY_STR;
		switch (type) {
		case "1"://专业资格
			result="professionalList";
			break;
		case "2"://职业资格
			result="qualificationList";
			break;
		case "3":
			result="abilityList";
			break;
		case "4":
			result="nationList";
			break;
		case "5":
			result="cityList";
			break;
		default://type=1
			result="professionalList";
			break;
		}
		return result;
	}


	@Override
	public void storeBasicEnums(Map<String, Object> params) {
		List<BasicEnum> list=null;
		Jedis redis=jedisPool.getResource();
		
		list=basicEnumDao.getBasicEnumListByParams(params);
    	
    	if(list!=null&&!list.isEmpty()){
    		for(BasicEnum be:list){
    			String key=be.getType()+"_"+be.getName();
    			Boolean flag=redis.exists(key);
    			if(!flag){
    				redis.set(key, be.getId().toString());
    			}
    			
    		}
    	}
    	redis.close();
	}


	@Override
	public List<City> getCitys(Map<String, Object> params) {
		//获取参数
		String type=params.get("type")==null?null:params.get("type").toString();
		//获取keyName
		String keyName=getBasicType(type);
		
		List<City> citys=getCity(keyName);
		
		if(params.get("parentId")!=null){
			Integer parentId=Integer.parseInt(params.get("parentId").toString());
			List<City> list = new ArrayList<City>();
			for (City city : citys) {
				if(parentId==city.getParentId()){
					list.add(city);
				}
			}
			return list;			
		}
		return citys;			
	}
	
	
	@SuppressWarnings("rawtypes")
	private List<City> getCity(String keyName){
		Jedis redis=jedisPool.getResource();
		List<City> list=null;
		//获取对应keyName的value
		Map<String, String> result=redis.hgetAll(keyName);
		
		if(result!=null&&!result.isEmpty()){
			//从redis取出list
			List<City> redisList=new ArrayList<City>();
	        for(Map.Entry entry: result.entrySet()) { 
	        	City be= JSONObject.parseObject(entry.getValue().toString(),City.class); 
	        	redisList.add(be);
	        } 
	        redis.close();
	        return redisList;
		}else{
			list=basicEnumDao.getCitys(null);
			if(list!=null&&!list.isEmpty()){
				for(City be:list){
					String id=be.getId().toString();
					//存储列表
					redis.hset(keyName,id,JSONObject.toJSONString(be));
				}
			}
			redis.close();
			return list;			
		}
	}


	@Override
	public List<BasicEnumTree> getBasicEnumListTreeByPrams(
			Map<String, Object> params) {
		List<BasicEnum> list=getBasicEnumListByParams(params);
		List<BasicEnumTree> treeList=new ArrayList<BasicEnumTree>();
		if(list!=null&&!list.isEmpty()){
			for(BasicEnum be:list){
				if(be.getParentId()!=null&&be.getParentId().intValue()==0){
					BasicEnumTree parentTree=new BasicEnumTree(be.getId(), be.getType(),be.getName(),be.getParentId(),false);
					List<BasicEnumTree> childList=new ArrayList<BasicEnumTree>();
					for(BasicEnum b:list){
						if(b.getParentId().intValue()==be.getId().intValue()){
							BasicEnumTree childTree=new BasicEnumTree(b.getId(), b.getType(),b.getName(),b.getParentId(),true);
							childList.add(childTree);
						}
					}
					parentTree.setChildren(childList);
					treeList.add(parentTree);
				}
			}
		}
		return treeList;
	}


}
