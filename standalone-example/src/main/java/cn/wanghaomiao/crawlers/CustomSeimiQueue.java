package cn.wanghaomiao.crawlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alibaba.fastjson.JSON;

import cn.wanghaomiao.crawlers.redis.DataRedis;
import cn.wanghaomiao.seimi.annotation.Queue;
import cn.wanghaomiao.seimi.core.SeimiQueue;
import cn.wanghaomiao.seimi.struct.Request;

@Queue
public class CustomSeimiQueue implements SeimiQueue {

	DataRedis dataRedis = new DataRedis() ;
	
	@Override
	public Request bPop(String crawlerName) {
		try {
			String val = dataRedis.lpop("CustomSeimiQueue_" + crawlerName) ;
			if(val == null || val.equals("")){
				return null ;
			}
			Request request = (Request) IOStream.getInstance().readSerializableObject(Base64.decode(val)) ;
			return request ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	@Override
	public boolean push(Request req) {
		byte[] bytes = IOStream.getInstance().readSerializableObject(req) ;
		String val = Base64.encode(bytes) ;
		dataRedis.lpush("CustomSeimiQueue_" + req.getCrawlerName(), val) ;
		return true ;
	}

	@Override
	public long len(String crawlerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isProcessed(Request req) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addProcessed(Request req) {
		// TODO Auto-generated method stub

	}

	@Override
	public long totalCrawled(String crawlerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearRecord(String crawlerName) {
		// TODO Auto-generated method stub

	}

}
