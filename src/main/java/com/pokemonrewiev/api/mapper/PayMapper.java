package com.pokemonrewiev.api.mapper;

import com.pokemonrewiev.api.dto.PayDto;
import com.pokemonrewiev.api.entity.PayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface PayMapper {

    PayMapper INSTANCE2 = Mappers.getMapper(PayMapper.class);

    @Mapping(source = "payKodu", target = "payKodu")
    @Mapping(source = "pay", target = "pay")
    PayDto mapToDto(PayEntity pay);

    @Mapping(source = "payKodu", target = "payKodu")
    @Mapping(source = "pay", target = "pay")
    PayEntity mapToEntity(PayDto payDto);

    /*public PayDto mapToDto(PayEntity pay){
        PayDto payDto = new PayDto();
        payDto.setPayKodu(pay.getPayKodu());
        payDto.setPay(pay.getPay());
        return payDto;
    }

    public PayEntity mapToEntity(PayDto payDto){
        PayEntity pay = new PayEntity();
        pay.setPay(payDto.getPay());
        pay.setPayKodu(payDto.getPayKodu());
        return pay;
    }*/
}
