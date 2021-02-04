/**
 * Project Name:gp-local;<br/>
 * File Name:JsapiTicketSignDTO;<br/>
 * Package Name:com.sq580.facade.model.req.wx;<br/>
 * Date: 2019-12-19 17:12;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.infrastructure.wechat;

import lombok.Data;

/**
 * ClassName: JsapiTicketSignDTO<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-12-19 17:12<br/>
 *
 * @author zhangfs
 */
@Data
public class CreateQrcodeVO {
    private Integer errcode;
    private String errmsg;
    /**
     * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    private String ticket;
    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
     */
    private String expire_seconds;
    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

}