/**
 * Project Name:gp-local;<br/>
 * File Name:JsApiSignatureReq;<br/>
 * Package Name:com.sq580.facade.model.req.wx;<br/>
 * Date: 2019-12-19 16:13;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.facade.model.rsp.jobtitle;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName: JsApiSignatureReq<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-12-19 16:13<br/>
 *
 * @author zhangfs
 */
@Data
@ApiModel("WechatInvitationUrlRsp")
@AllArgsConstructor
public class InvitationUrlRsp {
    private String url;
}