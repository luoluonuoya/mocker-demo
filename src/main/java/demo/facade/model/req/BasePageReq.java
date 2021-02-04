
package demo.facade.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author zhangjian
 * 分页对象
 */
@Data
@ApiModel(value = "自定义分页参数")
public class BasePageReq extends BaseReq implements Serializable {

    @NotNull(message = "当前页码不能为空")
    @Min(value = 1, message = "当前页号必须大于等于1")
    @ApiModelProperty(value = "当前页号")
    private Integer pageNum;

    @NotNull(message = "当前页显示行数不能为空")
    @Min(value = 1, message = "当前页长必须大于等于1")
    @ApiModelProperty(value = "页长")
    private Integer pageSize;

}
