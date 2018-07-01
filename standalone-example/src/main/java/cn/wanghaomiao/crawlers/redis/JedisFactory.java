package cn.wanghaomiao.crawlers.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public interface JedisFactory {
	
	/**
	 * 获取JedisPool连接池
	 * @return
	 */
	JedisPool getJedisPool() ;
	
	/**
	 * 销毁JedisPool连接池
	 */
	void destroyJedisPool() ;
	
	/**
	 * 从JedisPool连接池中获取Jedis实例
	 * @return
	 */
	Jedis getJedis() ;
	
	/**
	 * 关闭Jedis实例
	 * @return
	 */
	void closeJedis(Jedis jedis) ;
}
