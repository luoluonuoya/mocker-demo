/**
 * Project Name:gp-local;<br/>
 * File Name:JsapiTicketSignDTO;<br/>
 * Package Name:com.sq580.facade.model.req.wx;<br/>
 * Date: 2019-12-19 17:12;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.infrastructure.wechat;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * ClassName: JsapiTicketSignDTO<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-12-19 17:12<br/>
 *
 * @author zhangfs
 */
@Data
public class CreateQrcodeDTO {

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     */
    private String expire_seconds;
    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    private String action_name;
    /**
     * 二维码详细信息
     */
    private Map<String, ActionInfo> action_info = Maps.newHashMap();

    @Data
    @AllArgsConstructor
    public static class ActionInfo {
        /**
         * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
         */
        private String scene_str;
        /**
         * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
         * 数字局限性太大，这里只用字符串形
         */
//        private int scene_id;
    }

}