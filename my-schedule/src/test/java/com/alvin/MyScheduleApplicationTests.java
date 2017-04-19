package com.alvin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @SpringApplicationConfiguration,
 * @ContextConfiguration with the SpringApplicationContextLoader,
 * @IntegrationTest or @WebIntegrationTest
 *
 * 从1.4开始@SpringBootTest替代了以上所有
 *
 * 可以参考
 * https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.4-Release-Notes
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyScheduleApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void contextLoads() throws Exception {
        this.mvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome"));
    }

}
