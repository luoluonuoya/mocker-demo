package demo.common.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by limiao on 2017/9/22.
 */
public interface BaseDaoMybatis<Example,Entity> {

    long countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Entity record);

    int insertSelective(Entity record);

    List<Entity> selectByExample(Example example);

    Entity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Entity record, @Param("example") Example example);

    int updateByExample(@Param("record") Entity record, @Param("example") Example example);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);
}
