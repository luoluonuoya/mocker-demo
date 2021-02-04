/**
 * Project Name:medical-instrument-market;<br/>
 * File Name:InvitationDoctorReq;<br/>
 * Package Name:com.sq580.facade.model.req.wechat;<br/>
 * Date: 2020-03-31 15:58;<br/>
 * Copyright (c) 2020, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.facade.model.req.jobtitle;

import demo.facade.model.req.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: InvitationDoctorReq<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2020-03-31 15:58<br/>
 *
 * @author zhangfs
 */
@Data
@ApiModel("WeChatInvitationDoctorReq")
public class InvitationDoctorReq extends BaseReq {

    @ApiModelProperty("guid")
    @NotBlank(message = "guid不能为空")
    private String guid;

}