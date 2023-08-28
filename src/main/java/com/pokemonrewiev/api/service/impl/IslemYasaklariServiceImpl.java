package com.pokemonrewiev.api.service.impl;

import com.pokemonrewiev.api.client.IslemYasaklariClient;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.PayEntity;
import com.pokemonrewiev.api.exceptions.IslemYasaklariNotFoundException;
import com.pokemonrewiev.api.mapper.IslemYasaklariMapper;
import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.repository.PayRepository;
import com.pokemonrewiev.api.service.IslemYasaklariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class IslemYasaklariServiceImpl implements IslemYasaklariService {

    @Autowired
    IslemYasaklariRepository islemYasaklariRepository;
    @Autowired
    IslemYasaklariMapper islemYasaklariMapper;
    @Autowired
    IslemYasaklariClient islemYasaklariClient;
    @Autowired
    PayRepository payRepository;



    @Override
    public List<IslemYasaklariDto> getWebIslemYasaklari() {
        return islemYasaklariClient.getWebIslemYasaklari();
    }


    //Liste halinde girilin Json'ları kaydeder
    @Override
    public List<IslemYasaklari> createIslemYasaklari(List<IslemYasaklari> yasaklar){
        return islemYasaklariRepository.saveAll(yasaklar);
    }

    @Override
    public List<IslemYasaklariDto> getIslemYasaklariByRepository(int pageNo, int pageSize){
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<IslemYasaklari> islemYasaklaris = islemYasaklariRepository.findAll(pageable);
        //List<IslemYasaklari> islemYasaklariList = islemYasaklariRepository.findAll();
        return islemYasaklaris.stream().map(i -> islemYasaklariMapper.INSTANCE.mapToDto(i)).collect(Collectors.toList());
    }

    @Override
    public IslemYasaklari saveIslemYasaklari(IslemYasaklari islemYasaklari) {
        return islemYasaklariRepository.save(islemYasaklari);
    }

    @Override
    public void updateIslemYasaklari(IslemYasaklariDto islemYasaklariDto, String unvan){
        IslemYasaklari islemYasaklari = islemYasaklariRepository.findByUnvan(unvan);
        islemYasaklari.setUnvan(islemYasaklariDto.getUnvan());
        islemYasaklari.setKurulKararNo(islemYasaklariDto.getKurulKararNo());
        islemYasaklari.setMkkSicilNo(islemYasaklariDto.getMkkSicilNo());
        islemYasaklari.setPayKodu(islemYasaklariDto.getPayKodu());
        islemYasaklari.setKurulKararTarihi(islemYasaklariDto.getKurulKararTarihi());

        PayEntity payEntity = new PayEntity();
        payEntity.setPayKodu(islemYasaklariDto.getPayKodu());
        payEntity.setPay(islemYasaklariDto.getPay());

        islemYasaklari.setPayEntity(payEntity);

    }

    @Override
    public String deleteIslemYasaklari(String unvan){
        try{
            System.out.println("delete işlemi başlıyor");
            IslemYasaklari islemYasaklari = islemYasaklariRepository.findByUnvan(unvan);
            islemYasaklariRepository.delete(islemYasaklari);
            return "Başarılı";

        }catch (Exception exception){
            return ("Başarısız: "+ exception.getMessage());
        }
    }

    @Override
    public IslemYasaklariDto getDetail(int id){
        IslemYasaklari islemYasaklari = islemYasaklariRepository.findById(id).orElseThrow(()->new IslemYasaklariNotFoundException("Islem Yasaklari Bulunamadı..."));
        IslemYasaklariDto islemYasaklariDto = new IslemYasaklariDto();
        islemYasaklariDto=islemYasaklariMapper.INSTANCE.mapToDto(islemYasaklari);
        return islemYasaklariDto;
    }

    @Override
    public IslemYasaklariDto createDto(IslemYasaklariDto islemYasaklariDto) {
        IslemYasaklari islemYasaklari = islemYasaklariMapper.mapToEntity(islemYasaklariDto);
        List<PayEntity> payEntityList = payRepository.findAll();
        int counter=0;
        for (PayEntity payQuery: payEntityList){
            if(islemYasaklari.getPayKodu().equals(payQuery.getPayKodu())){
                counter++;
            }
        }
        if(counter==0){
            PayEntity addPay=new PayEntity();
            addPay.setPayKodu(islemYasaklariDto.getPayKodu());
            addPay.setPay(islemYasaklariDto.getPay());
            payRepository.save(addPay);
        }

        PayEntity payEntity = payRepository.findByPayKodu(islemYasaklari.getPayKodu());
        islemYasaklari.setPayEntity(payEntity);
        islemYasaklariRepository.save(islemYasaklari);

        return islemYasaklariDto;
    }

}
