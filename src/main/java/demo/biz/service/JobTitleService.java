package demo.biz.service;

import demo.biz.enums.BizErrorCode;
import demo.biz.enums.DeleteEnums;
import com.github.smallcham.plugin.page.support.Page;
import demo.biz.domain.repository.JobTitleRepository;
import demo.common.dao.BaseRepository;
import demo.common.exception.BusinessException;
import demo.common.service.AbsBaseService;
import demo.facade.model.req.jobtitle.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import demo.persistence.auto.model.JobTitle;
import demo.persistence.auto.model.JobTitleExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobTitleService extends AbsBaseService<JobTitleExample, JobTitle> {

    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public BaseRepository<JobTitleExample, JobTitle> getBaseRepository() {
        return jobTitleRepository;
    }

    public JobTitle queryByCode(Long accessId, String code) {
        return jobTitleRepository.queryByCode(accessId, code);
    }

    public void add(AddReq req) {
        JobTitle model = queryByCode(req.getAccessId(), req.getCode());
        if (null == model) {
            model = new JobTitle();
            BeanUtils.copyProperties(req, model);
            model.setIsDelete(DeleteEnums.NODELETE.getKey());
            insert(model);
        } else {
            throw new BusinessException(BizErrorCode.CODEEXISTED);
        }
    }

    public void update(UpdateReq req) {
        JobTitle model = selectByPrimaryKey(req.getId());
        if (null != model) {
            if (!req.getCode().equals(model.getCode())) {
                JobTitle tmp = queryByCode(model.getAccessId(), req.getCode());
                if (null != tmp) {
                    throw new BusinessException(BizErrorCode.CODEEXISTED);
                }
            }
            BeanUtils.copyProperties(req, model);
            updateByPrimaryKey(model);
        }
    }

    public void del(DelReq req) {
        JobTitle model = selectByPrimaryKey(req.getId());
        if (null != model && DeleteEnums.NODELETE.getKey().equals(model.getIsDelete())) {
            model.setIsDelete(DeleteEnums.DELETE.getKey());
            updateByPrimaryKey(model);
        }
    }

    public Page<JobTitle> page(PageReq req) {
        JobTitle model = new JobTitle();
        BeanUtils.copyProperties(req, model);
        model.setIsDelete(DeleteEnums.NODELETE.getKey());
        return jobTitleRepository.selectPageByNum(model, req.getPageNum(), req.getPageSize());
    }

    public List<JobTitle> selection(SelectionReq req) {
        return jobTitleRepository.queryNoDel(req.getAccessId());
    }

    public JobTitle detail(DetailReq req) {
        return selectByPrimaryKey(req.getId());
    }

    public String redis() {
        stringRedisTemplate.opsForValue().set("testKey", "testValue");
        String value = stringRedisTemplate.opsForValue().get("testKey");
        return value;
    }

}
