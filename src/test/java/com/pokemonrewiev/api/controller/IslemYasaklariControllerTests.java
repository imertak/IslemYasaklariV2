package com.pokemonrewiev.api.controller;

import com.pokemonrewiev.api.service.IslemYasaklariService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(IslemYasaklariController.class)
public class IslemYasaklariControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        String s = "MKK İŞLEM YASAKLARI...";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(s,result.getResponse().getContentAsString());
    }

}
