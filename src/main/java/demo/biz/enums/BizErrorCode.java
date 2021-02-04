package demo.biz.enums;

import demo.common.model.ResultData;
import lombok.Getter;

@Getter
public enum BizErrorCode {

    SUCCESS(ResultData.OK, ResultData.OK_MSG),
    PARAM_VALID(ResultData.PARAM_VALID,ResultData.PARAM_VALID_MSG),
    NOTFOUND(ResultData.NOTFOUND,ResultData.NOTFOUND_MSG),
    FAIL(ResultData.FAIL,ResultData.FAIL_MSG),
    TIME_OUT(ResultData.TIME_OUT,ResultData.TIME_OUT_MSG),
    ERR(ResultData.ERR,ResultData.ERR_MSG),
    TOKENFAILURE(ResultData.TOKEN,ResultData.TOKEN_MSG),
    PAGESUCCESS(0,ResultData.OK_MSG),
    CODEEXISTED(1000, "值已经存在"),
    ;

    /** 错误码 */
    private final int code;
    /** 描述 */
    private final String desc;

    private BizErrorCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
