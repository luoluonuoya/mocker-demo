package demo.persistence.auto.mapper;

import demo.common.dao.BaseDaoMybatis;
import demo.persistence.auto.model.JobTitle;
import demo.persistence.auto.model.JobTitleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobTitleMapper extends BaseDaoMybatis {
    long countByExample(JobTitleExample example);

    int deleteByExample(JobTitleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JobTitle record);

    int insertSelective(JobTitle record);

    List<JobTitle> selectByExample(JobTitleExample example);

    JobTitle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JobTitle record, @Param("example") JobTitleExample example);

    int updateByExample(@Param("record") JobTitle record, @Param("example") JobTitleExample example);

    int updateByPrimaryKeySelective(JobTitle record);

    int updateByPrimaryKey(JobTitle record);
}