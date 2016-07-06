package com.lofitskyi.util;

import com.lofitskyi.config.RootConfig;
import com.lofitskyi.config.WebConfig;
import com.lofitskyi.repository.PersistentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class})
public class PersistentExceptionHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void handle() throws Exception {
        mockMvc.perform(get("/throw"))
                .andExpect(redirectedUrl("/error"));
    }

    @Controller
    @RequestMapping("/throw")
    class ArticleApiController {

        @RequestMapping(method = RequestMethod.GET)
        String throwPersistenceException() throws PersistentException {
            throw new PersistentException();
        }
    }

}