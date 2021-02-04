package demo.persistence.ext.mapper;

import com.github.smallcham.plugin.page.support.RowBounds;
import demo.persistence.auto.model.JobTitle;

import java.util.List;

public interface JobTitleExtMapper {

    List<JobTitle> page(RowBounds rowBounds);

}
