package com.pokemonrewiev.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.ReCaptcha;
import com.pokemonrewiev.api.mapper.IslemYasaklariMapper;
import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.service.IslemYasaklariService;
import com.pokemonrewiev.api.service.impl.IslemYasaklariServiceImpl;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequestMapping("/api")

public class IslemYasaklariController {


    @Autowired
    IslemYasaklariService islemYasaklariService;
    @Autowired
    IslemYasaklariRepository islemYasaklariRepository;

    @GetMapping()
    public String hello(){
        return "MKK İŞLEM YASAKLARI...";
    }


    //@FeignClient ile alır
    //IslemYasaklari'daki Json verileri gösterir
    @GetMapping("/tum-yasaklar")
    public ResponseEntity<List<IslemYasaklariDto>> getWebIslemYasaklari(){
        return new ResponseEntity<List<IslemYasaklariDto>>(islemYasaklariService.getWebIslemYasaklari(),HttpStatus.OK);
    }

    @GetMapping("/getStringByWeb")
    public ResponseEntity getByWeb() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://ws.spk.gov.tr/IdariYaptirimlar/api/IslemYasaklari"; // Harici sitenin API URL'si
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(jsonResponse);
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<IslemYasaklari>> createİslemYasaklari(@RequestBody List<IslemYasaklari> yasaklar){
        try{
            return new ResponseEntity<>(islemYasaklariService.createIslemYasaklari(yasaklar), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //DB'den alır
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<IslemYasaklariDto>> getIslemYasaklariByRepository(
            @RequestParam (value = "pageNo",defaultValue = "1", required = false) int pageNo,
            @RequestParam (value = "pageSize", defaultValue = "1",required = false) int pageSize){
        try {
            System.out.println("Başarılı");
            return new ResponseEntity<>(islemYasaklariService.getIslemYasaklariByRepository(pageNo,pageSize),HttpStatus.ACCEPTED);
        }catch (Exception e){
            System.out.println("Hatalı");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/get-db")
    public ResponseEntity<List<IslemYasaklariDto>> getIslemYasak(){
        List<IslemYasaklari> islemYasaklariList = islemYasaklariRepository.findAll();
        List<IslemYasaklariDto> islemYasaklariDtoList = islemYasaklariList.stream().map(i -> IslemYasaklariMapper.INSTANCE.mapToDto(i)).collect(Collectors.toList());
        for(IslemYasaklariDto islemYasaklariDto: islemYasaklariDtoList){
            for (IslemYasaklari islemYasaklari: islemYasaklariList){
                islemYasaklariDto.setPayKodu(islemYasaklari.getPayEntity().getPay());
            }
        }

        return new ResponseEntity<>(islemYasaklariDtoList, HttpStatus.OK
        );
    }

    @PostMapping("update/{unvan}")
    public String updateIslemYasaklari(@RequestBody IslemYasaklariDto islemYasaklariDto, @PathVariable String unvan){
        try {
            islemYasaklariService.updateIslemYasaklari(islemYasaklariDto,unvan);
            System.out.println("update işlemi");
            return "Update Başarılı";
        }catch (Exception e){
            return "Update Başarısız";
        }
    }

    @DeleteMapping("/delete/{unvan}")
    public void deleteIslemYasaklari(@PathVariable String unvan){
        islemYasaklariService.deleteIslemYasaklari(unvan);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<IslemYasaklariDto> detailIslemYasaklari(@PathVariable int id){
        return ResponseEntity.ok(islemYasaklariService.getDetail(id));
    }

    @PostMapping("/add")
    public ResponseEntity<IslemYasaklariDto> createDto(@RequestBody IslemYasaklariDto islemYasaklariDto){
        System.out.println("kayıt girişi");
        return new ResponseEntity<>(islemYasaklariService.createDto(islemYasaklariDto),HttpStatus.CREATED);
    }


    @PostMapping("/google")
    public ResponseEntity<String> verifyToken(@RequestBody ReCaptcha reCaptcha) {
        String reCAPTCHA_TOKEN = reCaptcha.getToken();
        String secretKey = reCaptcha.getSecretKey();


            RestTemplate restTemplate = new RestTemplate();

            // İstek için URL ve verileri belirle
            String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + reCAPTCHA_TOKEN;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // İstek içeriğinin türünü belirtiyoruz
            String requestBody = "{\"secret\": \"secretKey\", \"response\": \"reCAPTCHA_TOKEN\"}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // POST isteği yap
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response;
    }


}
