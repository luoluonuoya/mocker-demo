package demo.facade.api;

import com.alibaba.fastjson.JSON;
import demo.biz.domain.repository.JobTitleRepository;
import demo.biz.enums.BizErrorCode;
import demo.common.model.ResultData;
import demo.persistence.auto.model.JobTitle;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Project Name:medical-market;<br/>
 * File Name:JobTitleControllerTest;<br/>
 * Package Name:facade.api;<br/>
 * Date: 2021-01-27 17:12;<br/>
 * Copyright (c) 2021, www.sq580.com All Rights Reserved.;<br/>
 */
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobTitleControllerTest {

    @Autowired
    private MockMvc mvc;
    private static final String TOKEN = "{\"accessId\": 0}";
    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Test
    @Sql("/data/JobTitleControllerTest/case1_add_exist.sql")
    void case1_add_exist() throws Exception {
        MvcResult result = mvc.perform(post("/jobTitle/add")
                .content("{\"code\": \"01\", \"name\": \"全科医师\", \"remark\": \"remark\", \"isEnabled\": 0, \"sort\": 1}")
                .contentType(APPLICATION_JSON)
                .header("token", TOKEN)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ResultData resultData = JSON.parseObject(result.getResponse().getContentAsString(), ResultData.class);
        assertEquals(resultData.getErr(), BizErrorCode.CODEEXISTED.getCode());
        assertEquals(resultData.getErrmsg(), BizErrorCode.CODEEXISTED.getDesc());
    }

    @Test
    void case2_add_success() throws Exception {
        MvcResult result = mvc.perform(post("/jobTitle/add")
                .content("{\"code\": \"02\", \"name\": \"主任医师\", \"remark\": \"remark\", \"isEnabled\": 0, \"sort\": 0}")
                .contentType(APPLICATION_JSON)
                .header("token", TOKEN)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ResultData resultData = JSON.parseObject(result.getResponse().getContentAsString(), ResultData.class);
        assertEquals(resultData, ResultData.SUCCESS);

        JobTitle jobTitle = jobTitleRepository.queryByCode(0L, "02");
        assertNotNull(jobTitle);
    }

}