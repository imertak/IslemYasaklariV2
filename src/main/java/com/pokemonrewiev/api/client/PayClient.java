package com.pokemonrewiev.api.client;

import com.pokemonrewiev.api.dto.PayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "get-pay", url = "https://ws.spk.gov.tr/IdariYaptirimlar/api")
public interface PayClient {
    @RequestMapping(method = RequestMethod.GET, value="/IslemYasaklari", produces = MediaType.APPLICATION_JSON_VALUE) //produces = "application/json"
    List<PayDto> getWebPay();
}
