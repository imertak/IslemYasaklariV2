package com.pokemonrewiev.api.client;

import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@FeignClient(name = "get-islemYasaklari", url = "https://ws.spk.gov.tr/IdariYaptirimlar/api")
public interface IslemYasaklariClient {
    @RequestMapping(method = RequestMethod.GET, value="/IslemYasaklari", produces = MediaType.APPLICATION_JSON_VALUE) //produces = "application/json"
    List<IslemYasaklariDto> getWebIslemYasaklari();
}
