/**
 * Project Name:medical-instrument-market;<br/>
 * File Name:ParamValidateAspect;<br/>
 * Package Name:facade.handler;<br/>
 * Date: 2019-10-24 14:10;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.facade.handler;

import demo.biz.enums.BizErrorCode;
import demo.biz.service.TokenService;
import com.alibaba.fastjson.JSON;
import demo.common.exception.BusinessException;
import demo.facade.model.req.BaseReq;
import demo.facade.model.rsp.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

/**
 * ClassName: ParamValidateAspect<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-10-24 14:10<br/>
 *
 * @author zhangfs
 */
@Slf4j
@Aspect
@Component
public class ParamValidateAspect {

    @Autowired
    private TokenService tokenService;

    @Pointcut("(execution(* demo.facade.api..*(@org.springframework.web.bind.annotation.RequestBody (*), ..)))")
    public void aspect() {}

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        Object firstParam = params != null && params.length > 0 ? params[0] : null;
        String requestId = UUID.randomUUID().toString();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String ip = request.getRemoteAddr();
        // 打印获取请求
        String url = request.getRequestURL().toString();
        log.info("请求流水:{},ip:{},url:{},入参:{}", requestId, ip, url, JSON.toJSONString(firstParam));
        validate(firstParam);
        validToken(firstParam, request.getHeader("token"));

        // 打印响应
        Object resultData = joinPoint.proceed();
        //日志长度优化
        String jsonResultData = JSON.toJSONString(resultData);
        if (jsonResultData.length() > 1000) {
            jsonResultData = jsonResultData.substring(0, 1000);
        }
        log.info("请求流水：{}，出参：{}", requestId, jsonResultData);
        return resultData;
    }

    /**
     * 检查token
     *
     * @param param
     * @param token
     */
    private void validToken(Object param, String token) {
        BaseUser baseUser = tokenService.validToken(token);
        if (param instanceof BaseReq && null != param) {
            getUserInfo((BaseReq) param, baseUser);
        }
    }

    /**
     * 透传userInfo
     *
     * @param param
     * @throws Throwable
     */
    private void getUserInfo(BaseReq param, BaseUser baseUser) {
        param.setAccessId(baseUser.getAccessId());
        param.setOperatePrimaryId(baseUser.getId());
        param.setOperateId(baseUser.getGuid());
        param.setOperateName(baseUser.getName());
        param.setOperateMobile(baseUser.getMobile());
    }

    public static <T> void validate(T t) {
        if (t == null) {
            return;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            int i = 0;
            int size = constraintViolations.size();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                errors.append(constraintViolation.getMessage() + (++i == size ? "" : "&"));
            }
            log.warn("==========参数验证出错！==========={}", errors);
            throw new BusinessException(BizErrorCode.PARAM_VALID.getCode(), errors.toString());
        }
    }

}