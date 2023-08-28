package com.pokemonrewiev.api.service;

import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;

import java.util.List;

public interface IslemYasaklariService {
    public List<IslemYasaklariDto> getWebIslemYasaklari();
    public List<IslemYasaklariDto> getIslemYasaklariByRepository(int pageNo, int pageSize);
    public com.pokemonrewiev.api.entity.IslemYasaklari saveIslemYasaklari(com.pokemonrewiev.api.entity.IslemYasaklari islemYasaklari);
    public void updateIslemYasaklari(IslemYasaklariDto islemYasaklariDto ,String unvan);
    public String deleteIslemYasaklari(String unvan);
    public IslemYasaklariDto getDetail(int id);
    public IslemYasaklariDto createDto(IslemYasaklariDto islemYasaklariDto);
    public List<IslemYasaklari> createIslemYasaklari(List<IslemYasaklari> yasaklar);

}
