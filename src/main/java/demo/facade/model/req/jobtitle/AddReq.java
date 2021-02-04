package demo.facade.model.req.jobtitle;

import demo.facade.model.req.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("TitleJobAddReq")
public class AddReq extends BaseReq {

    @ApiModelProperty("值")
    @NotBlank(message = "值不能为空")
    private String code;

    @ApiModelProperty("值描述")
    @NotBlank(message = "值描述不能为空")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态：0有效；1失效")
    @NotNull(message = "状态不能为空")
    private Byte isEnabled;

    @ApiModelProperty("显示次序")
    private Integer sort;

}
