package com.pokemonrewiev.api.mapper;

import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper
public interface IslemYasaklariMapper {

    IslemYasaklariMapper INSTANCE = Mappers.getMapper(IslemYasaklariMapper.class);

    @Mapping(source = "unvan", target = "unvan")
    @Mapping(source = "mkkSicilNo", target = "mkkSicilNo")
    @Mapping(source = "payEntity.payKodu", target = "payKodu")
    @Mapping(source = "kurulKararTarihi", target = "kurulKararTarihi")
    @Mapping(source = "kurulKararNo", target = "kurulKararNo")
    @Mapping(source = "payEntity.pay", target = "pay")
    IslemYasaklariDto mapToDto(IslemYasaklari islemYasaklari);

    @Mapping(source = "unvan", target = "unvan")
    @Mapping(source = "mkkSicilNo", target = "mkkSicilNo")
    @Mapping(source = "payKodu", target = "payEntity.payKodu")
    @Mapping(source = "kurulKararTarihi", target = "kurulKararTarihi")
    @Mapping(source = "kurulKararNo", target = "kurulKararNo")
    @Mapping(source = "pay", target = "payEntity.pay")
    IslemYasaklari mapToEntity(IslemYasaklariDto islemYasaklariDto);

}
