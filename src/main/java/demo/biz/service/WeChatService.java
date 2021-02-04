package demo.biz.service;

import com.alibaba.fastjson.JSON;
import demo.biz.enums.QrCodeSCANEnums;
import demo.infrastructure.wechat.CreateQrcodeDTO;
import demo.infrastructure.wechat.CreateQrcodeVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Getter
@Slf4j
@Service
public class WeChatService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${wechat.auth.service}")
    private String WECHATAUTHSERVICE;
    @Value("${wechat.auth.product}")
    private String AUTH_PRODUCT;
    @Value("${wechat.auth.runType}")
    private String AUTH_RUNTYPE;
    @Value("${wechat.appid}")
    private String APPID;
    @Value("${wechat.appsecret}")
    private String APPSECRET;
    @Value("${wechat.templateId.sharenotice}")
    private String TEMPLATEID_SHARENOTICE;
    @Value("${wechat.templateId.meetingnotice}")
    private String TEMPLATEID_MEETINGNOTICE;
    private static final String TOKEN_PREFIX = "mockerDemo:wechat:token:";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static String weChatUrl = "https://api.weixin.qq.com";

    @PostConstruct
    private void init() {
        if (null != System.getProperty("mockServer")) {
            weChatUrl = System.getProperty("mockServer");
        }
    }

    private String genTokenKey(String token){
        return TOKEN_PREFIX + token;
    }

    private String hiddenSecret(String url) {
        return url.replace(APPID, APPID.substring(0, 3) + "****" + APPID.substring(APPID.length() - 3))
                .replace(APPSECRET, APPSECRET.substring(0, 3) + "****" + APPSECRET.substring(APPSECRET.length() - 3));
    }

    private <T> T post(String url, Object param, Class<T> clazz) {
        Long currTime = System.currentTimeMillis();
        log.info("请求微信，请求id:{}, url:{}, 入参:{}", currTime, hiddenSecret(url), JSON.toJSONString(param));
        T resultData = restTemplate.postForObject(url, param, clazz);
        log.info("请求微信，请求id:{}, url:{}, 出参:{}", currTime, hiddenSecret(url), JSON.toJSONString(resultData));
        return resultData;
    }

    private <T> T get(String url, MultiValueMap headers, Object param, Class<T> clazz) {
        Long currTime = System.currentTimeMillis();
        log.info("请求微信，请求id:{}, url:{}, 入参:{}", currTime, hiddenSecret(url), JSON.toJSONString(param));
        HttpEntity httpEntity = null;
        if (null != headers) {
            httpEntity = new HttpEntity(headers);
        }
        ResponseEntity<T> resultData = restTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz, param);
        log.info("请求微信，请求id:{}, url:{}, 出参:{}", currTime, hiddenSecret(url), JSON.toJSONString(resultData.getBody()));
        return resultData.getBody();
    }

    /**
     * 获取缓存中的accessToken，没有就重新获取，不主动刷新，相关业务时过期重启触发获取
     */
    public String getAccessToken(boolean expires) {
//        String accessToken = null;
//        if (!expires) {
//            accessToken = stringRedisTemplate.opsForValue().get(genTokenKey(APPID));
//        }
//        if (StringUtils.isBlank(accessToken)) {
//            GetAccessTokenVO accessTokenVO = null;
//            do {
//                // 若错误码为-1，则表示系统繁忙，此时请开发者稍候再试
//                if (accessTokenVO != null && accessTokenVO.getErrcode() == -1) {
//                    log.error("系统繁忙，暂停3秒后重新获取accessToken");
//                    try {
//                        TimeUnit.SECONDS.sleep(3);
//                    } catch (InterruptedException e) {
//                        log.error(e.getMessage(), e);
//                    }
//                }
//                accessTokenVO = get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
//                        + APPID + "&secret=" + APPSECRET, null, null, GetAccessTokenVO.class);
//            } while (null != accessTokenVO.getErrcode() && accessTokenVO.getErrcode() == -1);
//            accessToken = accessTokenVO.getAccess_token();
//        }
//        if (StringUtils.isNotBlank(accessToken)) {
//            stringRedisTemplate.opsForValue().set(genTokenKey(APPID), accessToken, 7200, TimeUnit.SECONDS);
//        }
//        return accessToken;
        return "accessToken";
    }

    /**
     * 生成带参数二维码
     * @param key
     * @return
     */
    public CreateQrcodeVO createQrcode(QrCodeSCANEnums enums, Long key) {
        String accessToken = getAccessToken(Boolean.FALSE);
        CreateQrcodeDTO dto = new CreateQrcodeDTO();
        dto.setExpire_seconds("3600");
        // 数字局限性太大，这里只用字符串形
        dto.setAction_name("QR_STR_SCENE");
        StringBuffer scene = new StringBuffer();
        scene.append(AUTH_PRODUCT);
        scene.append(AUTH_RUNTYPE);
        scene.append(enums.getKey());
        scene.append(key);
        dto.getAction_info().put("scene", new CreateQrcodeDTO.ActionInfo(scene.toString()));
        String json = post(weChatUrl + "/cgi-bin/qrcode/create?access_token=" + accessToken,
                dto, String.class);
        CreateQrcodeVO vo = JSON.parseObject(json, CreateQrcodeVO.class);
        return vo;
    }

}
