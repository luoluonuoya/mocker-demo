package module;

import com.alibaba.fastjson.JSON;
import demo.Bootstrap;
import demo.common.model.ResultData;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import redis.embedded.RedisServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 有需要用到内嵌redis的可以在类里引入这些方法
 */
@SpringBootTest(classes = Bootstrap.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisMockerTest {

    @Autowired
    private MockMvc mvc;
    private static RedisServer redisServer;
    private static final String TOKEN = "{\"accessId\": 0}";

    @BeforeAll
    public static void beforeAll() {
        redisServer = RedisServer.builder().port(6379).setting("maxmemory 64M").build();
        redisServer.start();
    }

    @AfterAll
    public static void afterAll() {
        redisServer.stop();
        redisServer = null;
    }

    @Test
    void case1_redis() throws Exception {
        MvcResult result = mvc.perform(post("/jobTitle/test/redis")
                .contentType(APPLICATION_JSON)
                .header("token", TOKEN)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ResultData resultData = JSON.parseObject(result.getResponse().getContentAsString(), ResultData.class);
        assertEquals(resultData.getData().toString(), "testValue");
    }

}
