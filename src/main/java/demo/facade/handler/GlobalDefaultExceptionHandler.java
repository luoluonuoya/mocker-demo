package demo.facade.handler;

import demo.common.exception.BusinessException;
import demo.common.model.ResultData;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData exceptionHandler(Exception ex, HttpServletResponse response) throws Exception {
        ResultData rs = ResultData.getFailResult();
        rs.setErrmsg("系统繁忙，请稍后再试！");
        if (ex instanceof BusinessException) {
            BusinessException be = (BusinessException) ex;
            rs.setErr(be.getCode());
            rs.setErrmsg(be.getMessage());
        } else if (ex instanceof ServletException) {
            log.error(ExceptionUtils.getFullStackTrace(ex));
            throw ex;
        } else {
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }
        return rs;
    }
}
