package cn.wanghaomiao.dao.mybatis;

import java.util.Map;

import cn.wanghaomiao.model.BlogContent;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author 汪浩淼 et.tw@163.com
 * @since 2016/7/27.
 */
public interface MybatisStoreDAO {

    @Insert("insert into blog (title,content,update_time) values (#{blog.title},#{blog.content},now())")
    @Options(useGeneratedKeys = true, keyProperty = "blog.id")
    int save(@Param("blog") BlogContent blog);
    
    @Update("update brand_info set tmall_shop = #{url} where brand_id = #{brand_id}")
    void saveBrand(Map<String,Object> data) ;
    
    
}
