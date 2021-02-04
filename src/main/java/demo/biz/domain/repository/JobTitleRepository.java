package demo.biz.domain.repository;

import com.github.smallcham.plugin.page.support.RowBounds;
import demo.biz.enums.DeleteEnums;
import demo.biz.enums.EnabledEnums;
import demo.common.dao.AbsBaseRepository;
import demo.common.dao.BaseDaoMybatis;
import demo.persistence.auto.mapper.JobTitleMapper;
import demo.persistence.auto.model.JobTitle;
import demo.persistence.auto.model.JobTitleExample;
import demo.persistence.ext.mapper.JobTitleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobTitleRepository extends AbsBaseRepository<JobTitleExample, JobTitle> {

    @Autowired
    private JobTitleMapper titleJobMapper;
    @Autowired
    private JobTitleExtMapper titleJobExtMapper;

    @Override
    protected List<JobTitle> selectPage(RowBounds rowBounds) {
        return titleJobExtMapper.page(rowBounds);
    }

    @Override
    public BaseDaoMybatis<JobTitleExample, JobTitle> getBaseDaoMybatis() {
        return titleJobMapper;
    }

    public JobTitle queryByCode(Long accessId, String code) {
        JobTitleExample example = new JobTitleExample();
        JobTitleExample.Criteria criteria = example.createCriteria();
        criteria.andAccessIdEqualTo(accessId);
        criteria.andCodeEqualTo(code);
        criteria.andIsEnabledEqualTo(EnabledEnums.ENABLE.getKey());
        return getListFirst(selectByExample(example));
    }

    public List<JobTitle> queryNoDel(Long accessId) {
        JobTitleExample example = new JobTitleExample();
        JobTitleExample.Criteria criteria = example.createCriteria();
        criteria.andAccessIdEqualTo(accessId);
        criteria.andIsEnabledEqualTo(EnabledEnums.ENABLE.getKey());
        criteria.andIsDeleteEqualTo(DeleteEnums.NODELETE.getKey());
        example.setOrderByClause("sort asc");
        return selectByExample(example);
    }
}
