package com.pokemonrewiev.api.service;

import com.pokemonrewiev.api.client.IslemYasaklariClient;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.PayEntity;
import com.pokemonrewiev.api.exceptions.IslemYasaklariNotFoundException;
import com.pokemonrewiev.api.mapper.IslemYasaklariMapper;
import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.repository.PayRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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
    @Mock
    private PayRepository payRepository;

    @Test
    public void should_ReturnIslemYasaklariDto_when_CreateIslemYasaklariDto(){
        PayEntity payEntity = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .mkkSicilNo("test")
                .kurulKararNo("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .payEntity(payEntity)
                .build();

        IslemYasaklariDto islemYasaklariDto = IslemYasaklariDto.builder()
                .unvan("test")
                .mkkSicilNo("test")
                .kurulKararNo("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .pay("test")
                .build();

        List<PayEntity> payEntityList = new ArrayList<>();
        payEntityList.add(payEntity);

        when(payRepository.findAll()).thenReturn(payEntityList);
        when(payRepository.findByPayKodu(islemYasaklari.getPayKodu())).thenReturn(payEntity);

        IslemYasaklariDto savedIslemYasaklariDto = islemYasaklariService.createDto(islemYasaklariDto);

        Mockito.verify(payRepository, times(1)).findAll();
        Mockito.verify(payRepository,times(1)).findByPayKodu(islemYasaklari.getPayKodu() );
        Mockito.verify(islemYasaklariRepository,times(1)).save(any(IslemYasaklari.class));

        Assertions.assertNotNull(savedIslemYasaklariDto);
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


        IslemYasaklariDto islemYasaklariDtoSaved = islemYasaklariService.getDetail(id);

        Assertions.assertNotNull(islemYasaklariDtoSaved);
        Assertions.assertEquals(islemYasaklariDtoSaved.getUnvan(),islemYasaklari.getUnvan());
    }

    @Test
    public void shouldUpdateIslemYasaklariwhenGiveNewUnvan(){
        String unvan ="yeni unvan";
        PayEntity payEntity = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .mkkSicilNo("test")
                .kurulKararNo("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .payEntity(payEntity)
                .build();
        IslemYasaklariDto islemYasaklariDto = IslemYasaklariDto.builder()
                .unvan("yeni unvan")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();


        when(islemYasaklariRepository.findByUnvan(any())).thenReturn(islemYasaklari);

        islemYasaklariService.updateIslemYasaklari(islemYasaklariDto,unvan);

        Assertions.assertEquals(islemYasaklari.getUnvan(),unvan);

    }


}
