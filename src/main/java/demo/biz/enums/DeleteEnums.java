/**
 * Project Name:cf-pack-center;<br/>
 * File Name:DeleteEnums;<br/>
 * Package Name:biz.enums;<br/>
 * Date: 2018-11-02 11:22;<br/>
 * Copyright (c) 2018, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ClassName: DeleteEnums<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2018-11-02 11:22<br/>
 *
 * @author zhangfs
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DeleteEnums {

    NODELETE((byte) 0, "未删除"),
    DELETE((byte) 1, "删除"),
    ;

    private Byte key;
    private String value;

}