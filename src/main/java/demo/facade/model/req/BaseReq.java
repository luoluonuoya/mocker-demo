
package demo.facade.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: BaseReq
 * @author zhangjian
 */
@Data
public class BaseReq implements Serializable {
    @ApiModelProperty(value = "所属接入商ID",hidden = true)
    private Long accessId;
    @ApiModelProperty(value = "请求用户主键id",hidden = true)
    private Long operatePrimaryId;
    @ApiModelProperty(value = "请求用户guid",hidden = true)
    private String operateId;
    @ApiModelProperty(value = "请求用户姓名",hidden = true)
    private String operateName;
    @ApiModelProperty(value = "请求用户电话",hidden = true)
    private String operateMobile;

}
