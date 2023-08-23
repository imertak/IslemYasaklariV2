package com.pokemonrewiev.api.repository;

import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.PayEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class IslemYasaklariRepositoryTestswithMockito {

    @Mock
    private IslemYasaklariRepository mockIslemYasaklariRepository;
    @Mock
    private PayRepository mockPayRepository;

    @Test
    public void shouldSaveIslemYasaklariObjectandReturnNotNullIslemYasaklari(){
        MockitoAnnotations.openMocks(this);

        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        when(mockIslemYasaklariRepository.save(islemYasaklari)).thenReturn(islemYasaklari);

        mockIslemYasaklariRepository.save(islemYasaklari);

        Optional<IslemYasaklari> islemYasaklariSaved = mockIslemYasaklariRepository.findById(islemYasaklari.getId());
        Assertions.assertNotNull(islemYasaklariSaved);
    }

    @Test
    public void shouldSavePayEntityObjectandReturnNotNullPayEntity(){
        PayEntity pay = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();

        when(mockPayRepository.save(Mockito.any(PayEntity.class))).thenReturn(pay);

        mockPayRepository.save(pay);
        Optional<PayEntity> payEntitySaved = mockPayRepository.findById(pay.getId());

        Assertions.assertNotNull(payEntitySaved);

    }

}
