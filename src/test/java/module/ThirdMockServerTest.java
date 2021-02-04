package module;

import com.alibaba.fastjson.JSON;
import demo.Bootstrap;
import demo.biz.enums.BizErrorCode;
import demo.common.model.ResultData;
import demo.facade.model.rsp.jobtitle.InvitationUrlRsp;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.stop.Stop.stopQuietly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 有需要用到第三方服务请求的可以参考该类
 * 内嵌开启一个本地mockServer，将mockServer的域名+端口在实际访问第三方服务的业务层提前注入，如下
 *
 * private static String url = "这里写实际的第三方请求，一般从配置读取";
 * @PostConstruct
 * private void initialise() {
 *      // 如果读到配置，就使用我们自己的内嵌服务器，这样就不会请求到真实的第三方去
 *     if (null != System.getProperty("mockServer")) {
 *         url = System.getProperty("mockServer");
 *     }
 * }
 *
 * springcloud之feign说明：
 * 参考文章：https://www.jianshu.com/p/baa1e97a5a99
 * 1. @FeignClient注解默认primary为ture，则现将外部接口的primary设置为false
 * 2. 给mock的实现类加上@Primary，可以同时在类上加上@ConditionalOnProperty，设置当读取到某个配置的时候优先加载该实现类，不过一般实现类写在test包下，无所谓
 */
@SpringBootTest(classes = Bootstrap.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ThirdMockServerTest {

    private static ClientAndServer mockServer;
    private static final String TOKEN = "{\"accessId\": 0}";
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void startProxy() {
        mockServer = startClientAndServer();
        System.setProperty("mockServer", "http://localhost:" + mockServer.getPort());
    }

    @AfterAll
    public static void stopProxy() {
        stopQuietly(mockServer);
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
    }

    @Test
    void test() throws Exception {
        // mock请求
        mockServer
                .when(
                        request().withPath("/cgi-bin/qrcode/create")
                                .withQueryStringParameter("access_token", "accessToken")
                )
                .respond(
                        response()
                                .withHeaders(
                                        new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody("{\"ticket\":\"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm\n" +
                                        "3sUw==\",\"expire_seconds\":60,\"url\":\"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI\"}")
                );

        // 调用controller接口，接口链路的业务层会调用到上面的mock接口，因为我们在业务层把目标请求给改了
        MvcResult result = mvc.perform(
                post("/jobTitle/test/wechat")
                        .content("{\"guid\": \"xxx\"}")
                        .header("token", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        // 验证请求
        ResultData resultData = JSON.parseObject(result.getResponse().getContentAsString(), ResultData.class);
        assertEquals(resultData.getErr(), BizErrorCode.SUCCESS.getCode());
        assertNotNull(resultData.getData());

        InvitationUrlRsp rsp = JSON.parseObject(JSON.toJSONString(resultData.getData()), InvitationUrlRsp.class);
        assertEquals(rsp.getUrl(), "http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI");
    }

}
