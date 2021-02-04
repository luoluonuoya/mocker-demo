package demo.common.service;

import demo.common.dao.BaseRepository;

/**
 * Created by limiao on 2017/9/22.
 */
public interface BaseService<Example,Entity> {

    BaseRepository<Example,Entity> getBaseRepository();

    int deleteByPrimaryKey(Long id);

    int insert(Entity record);

    int insertSelective(Entity record);

    Entity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);
}
