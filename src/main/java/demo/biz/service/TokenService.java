package demo.biz.service;

import com.alibaba.fastjson.JSON;
import demo.biz.enums.BizErrorCode;
import demo.common.exception.BusinessException;
import demo.facade.model.rsp.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "mocker-demo:user:token:";
    @Value("${mockswitch:false}")
    private boolean MOCKSWITCH;
    @Value("${token.timeout:3600}")
    private int EXPIRE;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            log.error("生成Token失败", e);
            throw new BusinessException(BizErrorCode.TOKENFAILURE);
        }
    }

    private String genTokenKey(String token){
        return TOKEN_PREFIX + token;
    }

    /**
     * 以key-value形式存储了token：{userinfo}
     * @param baseUser
     * @return
     */
    public String createToken(BaseUser baseUser) {
        // 生成一个token
        String token = generateValue();
        String json = JSON.toJSONString(baseUser);
        stringRedisTemplate.opsForValue().set(genTokenKey(token), json, EXPIRE, TimeUnit.SECONDS);
        baseUser.setToken(token);
        return token;
    }

    public BaseUser validToken(String token) {
        if (MOCKSWITCH) {
            return JSON.parseObject(token, BaseUser.class);
        }

        String object = stringRedisTemplate.opsForValue().get(genTokenKey(token));
        if (StringUtils.isNotBlank(object)) {
            // 续期
            stringRedisTemplate.opsForValue().set(genTokenKey(token), object, EXPIRE, TimeUnit.SECONDS);
            return JSON.parseObject(object, BaseUser.class);
        }
        throw new BusinessException(BizErrorCode.TOKENFAILURE);
    }

}
