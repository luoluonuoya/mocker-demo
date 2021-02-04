package demo.facade.model.req.jobtitle;

import demo.facade.model.req.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("TitleJobPageReq")
public class PageReq extends BasePageReq {

    @ApiModelProperty("值")
    private String name;

    @ApiModelProperty("是否启用：0启用；1停用")
    private Byte isEnabled;

}
