package demo.common.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by limiao on 2017/9/22.
 */
public abstract class AbsBaseService<Example,Entity> implements BaseService<Example,Entity> {

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int deleteByPrimaryKey(Long id) {
        return this.getBaseRepository().deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int insert(Entity record) {
        return this.getBaseRepository().insert(record);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int insertSelective(Entity record) {
        return this.getBaseRepository().insertSelective(record);
    }

    @Override
    public Entity selectByPrimaryKey(Long id) {
        Entity entity = this.getBaseRepository().selectByPrimaryKey(id);
        if (entity==null){
            return null;
        }
        return entity;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int updateByPrimaryKeySelective(Entity record) {
        return this.getBaseRepository().updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int updateByPrimaryKey(Entity record) {
        return this.getBaseRepository().updateByPrimaryKey(record);
    }

}
