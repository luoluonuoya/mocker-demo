package demo.facade.api;

import demo.biz.enums.QrCodeSCANEnums;
import demo.biz.service.JobTitleService;
import com.github.smallcham.plugin.page.support.Page;
import demo.biz.service.WeChatService;
import demo.facade.model.req.jobtitle.*;
import demo.common.model.ResultData;
import demo.facade.model.rsp.jobtitle.InvitationUrlRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.persistence.auto.model.JobTitle;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/jobTitle")
@Api(value = "职称字典管理", tags = {"职称字典管理"})
public class JobTitleController {

    @Autowired
    private JobTitleService jobTitleService;
    @Autowired
    private WeChatService weChatService;

    @ApiOperation(value = "新增职称", notes = "新增职称")
    @PostMapping("/add")
    public ResultData add(@RequestBody AddReq req) {
        jobTitleService.add(req);
        return ResultData.SUCCESS;
    }

    @ApiOperation(value = "修改职称", notes = "修改职称")
    @PostMapping("/update")
    public ResultData update(@RequestBody UpdateReq req) {
        jobTitleService.update(req);
        return ResultData.SUCCESS;
    }

    @ApiOperation(value = "删除职称", notes = "删除职称")
    @PostMapping("/del")
    public ResultData del(@RequestBody DelReq req) {
        jobTitleService.del(req);
        return ResultData.SUCCESS;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page")
    public ResultData<Page<JobTitle>> page(@RequestBody PageReq req) {
        return ResultData.getSuccessData(jobTitleService.page(req));
    }

    @ApiOperation(value = "职称详情", notes = "职称详情")
    @PostMapping("/detail")
    public ResultData<JobTitle> detail(@RequestBody DetailReq req) {
        return ResultData.getSuccessData(jobTitleService.detail(req));
    }

    @ApiOperation(value = "下拉查询", notes = "下拉查询")
    @PostMapping("/selection")
    public ResultData<List<JobTitle>> selection(@RequestBody SelectionReq req) {
        return ResultData.getSuccessData(jobTitleService.selection(req));
    }

    @ApiOperation(value = "操作redis", notes = "操作redis")
    @PostMapping("/test/redis")
    public ResultData<String> redis() {
        return ResultData.getSuccessData(jobTitleService.redis());
    }

    @ApiOperation(value = "操作redis", notes = "操作redis")
    @PostMapping("/test/wechat")
    public ResultData<InvitationUrlRsp> wechat(@RequestBody InvitationDoctorReq req) {
        return ResultData.getSuccessData(weChatService.createQrcode(QrCodeSCANEnums.INVITATION_DOCTOR, req.getOperatePrimaryId()));
    }

}
