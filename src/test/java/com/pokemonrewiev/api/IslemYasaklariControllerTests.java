/*package com.pokemonrewiev.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.service.IslemYasaklariService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = IslemYasaklari.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class IslemYasaklariControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IslemYasaklariService islemYasaklariService;

    @Autowired
    private ObjectMapper objectMapper;

    private IslemYasaklari islemYasaklari;
    private IslemYasaklariDto islemYasaklariDto;

    @BeforeEach
    public void init() {
        islemYasaklari = IslemYasaklari.builder().unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();
        islemYasaklariDto = IslemYasaklariDto.builder().unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();


    }

    @Test
    public void IslemYasaklarControllerCreateDto() throws Exception{
        given(islemYasaklariService.createDto(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(islemYasaklariDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }


}
*/