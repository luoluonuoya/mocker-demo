package demo.facade.model.req.jobtitle;

import demo.facade.model.req.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("TitleJobDetailReq")
public class DetailReq extends BaseReq {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

}
