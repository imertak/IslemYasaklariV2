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
    public void updateIslemYasaklari(String unvan ,int id){
        IslemYasaklari islemYasaklari = islemYasaklariRepository.getById(id);
        islemYasaklari.setUnvan(unvan);
        islemYasaklariRepository.save(islemYasaklari);
    }

    @Override
    public String deleteIslemYasaklari(String onay, int id){
        try{
            //Postman'den "onay" text'i gelmeden silmez
            if(onay.equals("onay")){
                IslemYasaklari islemYasaklari = islemYasaklariRepository.getById(id);
                islemYasaklariRepository.delete(islemYasaklari);
                return "Başarılı";
            }
            else {
                return "Başarısız";
            }
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
