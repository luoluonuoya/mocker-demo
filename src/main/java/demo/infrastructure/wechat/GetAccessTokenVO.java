/**
 * Project Name:cf-gpsms-biz;<br/>
 * File Name:GetAccessTokenVO;<br/>
 * Package Name:com.sq580.ms.gpsms.biz.service.model.rsp.wconfig;<br/>
 * Date: 2019-07-26 16:45;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.infrastructure.wechat;

import lombok.Data;

/**
 * ClassName: GetAccessTokenVO<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-07-26 16:45<br/>
 *
 * @author zhangfs
 */
@Data
public class GetAccessTokenVO {

    private Integer errcode;

    private String access_token;

    private Integer expires_in;

}