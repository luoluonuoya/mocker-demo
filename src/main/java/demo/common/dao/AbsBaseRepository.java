package demo.common.dao;

import com.github.smallcham.plugin.page.support.Page;
import com.github.smallcham.plugin.page.support.RowBounds;
import demo.common.util.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.function.Function;

/**
 * Created by limiao on 2017/9/22.
 */
public abstract class AbsBaseRepository<Example,Entity> implements BaseRepository<Example,Entity> {

    @Override
    public long countByExample(Example example) {
        return this.getBaseDaoMybatis().countByExample(example);
    }

    @Override
    public int deleteByExample(Example example) {
        return this.getBaseDaoMybatis().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return this.getBaseDaoMybatis().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Entity record) {
        return this.getBaseDaoMybatis().insert(record);
    }

    @Override
    public int insertSelective(Entity record) {
        return this.getBaseDaoMybatis().insertSelective(record);
    }

    @Override
    public List<Entity> selectByExample(Example example) {
        return this.getBaseDaoMybatis().selectByExample(example);
    }

    @Override
    public Entity selectByPrimaryKey(Long id) {
        return this.getBaseDaoMybatis().selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(@Param("record") Entity record, @Param("example") Example example) {
        return this.getBaseDaoMybatis().updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(@Param("record") Entity record, @Param("example") Example example) {
        return this.getBaseDaoMybatis().updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Entity record) {
        return this.getBaseDaoMybatis().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Entity record) {
        return this.getBaseDaoMybatis().updateByPrimaryKey(record);
    }

    protected Entity getListFirst(List<Entity> list){
        if(list==null||list.size()==0){
            return null;
        }
        return list.get(0);
    }

    protected abstract List<Entity> selectPage(RowBounds rowBounds);

    private Page<Entity> page(RowBounds rowBounds){
        List<Entity> list = selectPage(rowBounds);
        return PageUtil.<Entity>getInstance().myAsPage(rowBounds, list);
    }


    public Page<Entity> selectPageByNum(Entity record,int nowPage){
        RowBounds rowBounds = Page.rowBounds(nowPage,record);
        return this.page(rowBounds);
    }

    public Page<Entity> selectPageByNum(Entity record,int nowPage,int pageSize){
        RowBounds rowBounds = Page.rowBounds(nowPage,pageSize,record);
        return this.page(rowBounds);
    }

    protected Page<Entity> selectPageByNum(Entity record, long nowPage, long pageSize, Function<RowBounds, List> function) {
        RowBounds rowBounds = Page.rowBounds(nowPage, pageSize, record);
        List result = function.apply(rowBounds);
        return PageUtil.getInstance().myAsPage(rowBounds, result);
    }

}
