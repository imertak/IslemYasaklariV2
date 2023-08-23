package com.pokemonrewiev.api.repository;

import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.PayEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IslemYasaklariRepositoryTests {

    @Autowired
    private IslemYasaklariRepository islemYasaklariRepository;
    @Autowired
    private PayRepository payRepository;

    @Test
    public void shouldSaveIslemYasaklariObjectandReturnNotNull(){
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();


        islemYasaklariRepository.save(islemYasaklari);
        Optional<IslemYasaklari> islemYasaklariSaved = islemYasaklariRepository.findById(islemYasaklari.getId());

        Assertions.assertNotNull(islemYasaklariSaved);
    }

    @Test
    public void shouldSavePayEntityObjectandReturnNotNull(){
        PayEntity payEntity = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();

        payRepository.save(payEntity);

        PayEntity payEntitySaved = payRepository.findByPayKodu(payEntity.getPayKodu());
        Assertions.assertNotNull(payEntitySaved);
    }

    @Test
    public void shouldDeleteIslemYasaklariObjectandReturnIsEmpty(){
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        islemYasaklariRepository.save(islemYasaklari);
        islemYasaklariRepository.deleteById(islemYasaklari.getId());

        Optional<IslemYasaklari> islemYasaklariReturn = islemYasaklariRepository.findById(islemYasaklari.getId());

        Assertions.assertTrue(islemYasaklariReturn.isEmpty());

    }

    @Test
    public void shouldDeletePayEntityObjectandReturnIsEmpty(){
        PayEntity payEntity = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();

        payRepository.save(payEntity);
        payRepository.deleteById(payEntity.getId());
        Optional<PayEntity> payEntityReturn = payRepository.findById(payEntity.getId());
        Assertions.assertTrue(payEntityReturn.isEmpty());
    }

    @Test
    public void shouldUpdateIslemYasaklariObjectandReturnNotNull(){
        IslemYasaklari islemYasaklari = IslemYasaklari.builder()
                .unvan("test")
                .kurulKararTarihi("test")
                .payKodu("test")
                .kurulKararNo("test")
                .mkkSicilNo("test")
                .build();

        islemYasaklari.setUnvan("test1");
        islemYasaklari.setMkkSicilNo("test1");
        islemYasaklari.setKurulKararTarihi("test1");
        islemYasaklari.setPayKodu("test1");
        islemYasaklari.setKurulKararNo("test1");

        islemYasaklariRepository.save(islemYasaklari);
        Optional<IslemYasaklari> islemYasaklariSaved = islemYasaklariRepository.findById(islemYasaklari.getId());

        Assertions.assertNotNull(islemYasaklariSaved);
    }


    @Test
    public void shouldUpdatePayEntityObjectandReturnNotNull(){
        PayEntity payEntity = PayEntity.builder()
                .pay("test")
                .payKodu("test")
                .build();
        payRepository.save(payEntity);

        payEntity.setPay("test1");
        payEntity.setPayKodu("test1");


        Optional<PayEntity> payEntitySaved = payRepository.findById(payEntity.getId());
        Assertions.assertNotNull(payEntitySaved);
    }

}
