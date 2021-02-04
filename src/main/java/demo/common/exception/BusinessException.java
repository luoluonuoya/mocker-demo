package demo.common.exception;

import demo.biz.enums.BizErrorCode;
import demo.common.model.ResultData;
import lombok.Data;

/**
 * 业务异常
 *
 * @author limiao
 */
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    int code = ResultData.ERR;


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(BizErrorCode bizErrorCode) {
        super(bizErrorCode.getDesc());
        this.code = bizErrorCode.getCode();
    }

    public BusinessException(int code, String message, Throwable t) {
        super(message,t);
        this.code = code;
    }

}
