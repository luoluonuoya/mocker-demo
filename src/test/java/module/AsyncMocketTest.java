package module;

import demo.Bootstrap;
import demo.biz.domain.repository.JobTitleRepository;
import demo.persistence.auto.model.JobTitle;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Project Name:mocker-demo;<br/>
 * File Name:AsyncMocketTest;<br/>
 * Package Name:module;<br/>
 * Date: 2021-03-17 10:59;<br/>
 * Copyright (c) 2021, www.sq580.com All Rights Reserved.;<br/>
 */
@SpringBootTest(classes = Bootstrap.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsyncMocketTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Test
    public void callable() throws Exception {
        MvcResult result = mvc.perform(
                post("/jobTitle/test/callable")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        JobTitle model = jobTitleRepository.queryByCode(0L, "10000");
        assertNull(model);

        mvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());

        model = jobTitleRepository.queryByCode(0L, "10000");
        assertEquals(model.getName(), "异步");
    }

}
