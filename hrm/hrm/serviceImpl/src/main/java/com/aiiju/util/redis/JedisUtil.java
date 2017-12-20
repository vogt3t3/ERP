package com.aiiju.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @ClassName: JedisUtil
 * @Description: redis工具类(非集群)
 * @author 哪吒
 * @date 2016年11月24日 下午12:01:48
 *
 */

public class JedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);
	
	private static JedisPool jedisPool = null;
	
	private static Integer EXPIRE = 60000;
	
	/**
	 * 构造方法
	 */
	public JedisUtil() {
		
	}
	
	/**
	 * 从jedis连接池中获取jedis对象
	 * @return
	 */
	private Jedis getJedis() {
        if (jedisPool == null) {
            synchronized (EXPIRE) {
                if (jedisPool == null) {
                    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                    jedisPool = wac.getBean(JedisPool.class);
                }
            }
        }
        return jedisPool.getResource();
    }
	
	/**
	 * 懒汉单例模式，双重检查锁定，线程安全
	 */
	private static JedisUtil jedisUtil = null;
	public static JedisUtil getInstance() {
        if (null == jedisUtil) {
            synchronized (JedisUtil.class) {
               if (null == jedisUtil) {
                   jedisUtil = new JedisUtil();
               }
            }
        }
        return jedisUtil;
    }
	
	/**
	 * 设置单个值
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		String result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 根据key获取值
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 设置单个值，并设置过期时间
	 * @param key
	 * @param value
	 * @param expire 以秒为单位
	 * @return
	 */
	public String set(String key, String value, int expire) {
		String result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.setex(key, expire, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 设置单个值
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(byte[] key, byte[] value) {
		String result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 设置单个值，并设置过期时间
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String set(byte[] key, byte[] value, int expire) {
		String result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.setex(key, expire, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 根据key查询是否存在
	 * @param key
	 * @return
	 */
	public Boolean exists(String key) {
		Boolean result = false;
		Jedis jedis = getJedis();
		try {
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 在某个时间点失效
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Long expireAt(String key, long unixTime) {
		Long result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.expireAt(key, unixTime);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 查询key的过期时间
	 * @param key
	 * @return
	 */
	public Long ttl(String key) {
		Long result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.ttl(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 删除key对应的记录，只能是单个key
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		Long result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 删除key对应的记录，可以是多个key
	 * @param keys
	 * @return
	 */
	public Long del(String... keys) {
		Long result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.del(keys);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}

	/**
	 * 删除keys对应的记录,可以是多个key
	 * @param keys
	 * @return
	 */
	public long del(byte[]... keys) {
		Long result = null;
		Jedis jedis = getJedis();
		try {
			result = jedis.del(keys);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return result;
	}
}
