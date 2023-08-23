package com.pokemonrewiev.api.service;

import com.pokemonrewiev.api.client.IslemYasaklariClient;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.mapper.IslemYasaklariMapper;
import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.service.impl.IslemYasaklariServiceImpl;
import jakarta.inject.Inject;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IslemYasaklariServiceTests {

    @Mock
    private IslemYasaklariRepository islemYasaklariRepository;

    @InjectMocks
    private IslemYasaklariServiceImpl islemYasaklariService;
    @Mock
    private IslemYasaklariMapper islemYasaklariMapper;
    @Mock
    private IslemYasaklariClient islemYasaklariClient;

    @Test
    public void should_ReturnIslemYAsaklariDto_when_CreateIslemYasaklariDto(){
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        IslemYasaklariDto islemYasaklariDto = IslemYasaklariDto.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        when(islemYasaklariMapper.INSTANCE.mapToEntity(Mockito.any(IslemYasaklariDto.class))).thenReturn(islemYasaklari);
        when(islemYasaklariRepository.save(Mockito.any(IslemYasaklari.class))).thenReturn(islemYasaklari);


        IslemYasaklariDto islemYasaklariDtoSaved = islemYasaklariService.createDto(islemYasaklariDto);

        Assertions.assertNotNull(islemYasaklariDtoSaved);

    }

    @Test
    public void shouldReturnDetailIslemYasaklariDtowhenGiveIslemYasaklariId(){
        int id = 1;
        IslemYasaklariDto islemYasaklariDto = IslemYasaklariDto.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();
        islemYasaklari.setId(id);



        when(islemYasaklariRepository.findById(id)).thenReturn(Optional.ofNullable(islemYasaklari));
        when(islemYasaklariMapper.INSTANCE.mapToDto(Mockito.any(IslemYasaklari.class))).thenReturn(islemYasaklariDto);

        IslemYasaklariDto islemYasaklariDtoSaved = islemYasaklariService.getDetail(id);

        Assertions.assertNotNull(islemYasaklariDtoSaved);
        Assertions.assertEquals(islemYasaklariDtoSaved.getUnvan(),islemYasaklari.getUnvan());
    }



}
