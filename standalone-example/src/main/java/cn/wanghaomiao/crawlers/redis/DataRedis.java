package cn.wanghaomiao.crawlers.redis;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;

@Service
@Scope("prototype")
public class DataRedis extends AbstractRedis {

	private static JedisPool DATA_CENTER_JEDIS_POOL = null ;
	
	@Override
	public JedisPool getJedisPool() {
		if (DATA_CENTER_JEDIS_POOL == null) {
            synchronized (DataRedis.class) {
                if (DATA_CENTER_JEDIS_POOL == null) {
                    	DATA_CENTER_JEDIS_POOL = JedisPoolBuilder.getJedisPool(
                    			100,
                    			100, 
                    			100, 
                    			true, true, "127.0.0.1" , 6379, 3000) ;
                }
            }
        }
        return DATA_CENTER_JEDIS_POOL;
	}

	@Override
	public void destroyJedisPool() {
		if (DATA_CENTER_JEDIS_POOL != null) {
            DATA_CENTER_JEDIS_POOL.destroy();
            DATA_CENTER_JEDIS_POOL = null;
        }
	}
}
