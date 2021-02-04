/**
 * Project Name:medical-instrument-market;<br/>
 * File Name:QrCodeSCANEnums;<br/>
 * Package Name:com.sq580.biz.domain.enums;<br/>
 * Date: 2020-04-21 13:59;<br/>
 * Copyright (c) 2020, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: QrCodeSCANEnums<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2020-04-21 13:59<br/>
 *
 * @author zhangfs
 */
@Getter
@AllArgsConstructor
public enum QrCodeSCANEnums {

    INVITATION_DOCTOR(1, "邀请医生注册"),
    ;

    private Integer key;
    private String value;

}