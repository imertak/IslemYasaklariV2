package com.pokemonrewiev.api.startup;

import com.pokemonrewiev.api.client.IslemYasaklariClient;
import com.pokemonrewiev.api.client.PayClient;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.dto.PayDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import com.pokemonrewiev.api.entity.PayEntity;
import com.pokemonrewiev.api.entity.Role;
import com.pokemonrewiev.api.entity.UserEntity;
import com.pokemonrewiev.api.mapper.IslemYasaklariMapper;
import com.pokemonrewiev.api.mapper.PayMapper;
import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.repository.PayRepository;
import com.pokemonrewiev.api.repository.RoleRepository;
import com.pokemonrewiev.api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyStartupBean {
    IslemYasaklariRepository islemYasaklariRepository;
    IslemYasaklariClient islemYasaklariClient;
    IslemYasaklariMapper islemYasaklariMapper;

    PayClient payClient;
    PayMapper payMapper;
    PayRepository payRepository;

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public MyStartupBean(IslemYasaklariRepository islemYasaklariRepository, IslemYasaklariClient islemYasaklariClient, IslemYasaklariMapper islemYasaklariMapper, PayClient payClient, PayMapper payMapper, PayRepository payRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.islemYasaklariRepository = islemYasaklariRepository;
        this.islemYasaklariClient = islemYasaklariClient;
        this.islemYasaklariMapper = islemYasaklariMapper;
        this.payClient = payClient;
        this.payMapper = payMapper;
        this.payRepository = payRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void createIslemYasaklari(){

        List<PayEntity> payList = payClient.getWebPay();
        //payList = payDtoList.stream().map(payDto -> payMapper.mapToEntity(payDto)).collect(Collectors.toList());

        for(PayEntity payEntity: payList){
            List<PayEntity> newPay = payRepository.findListByPayKodu(payEntity.getPayKodu());
            if(newPay.isEmpty()){
                payRepository.save(payEntity);
            }
        }

        List<IslemYasaklariDto> islemYasaklariDtoList = islemYasaklariClient.getWebIslemYasaklari();
        List<IslemYasaklari> islemYasaklariList;
        islemYasaklariList = islemYasaklariDtoList.stream().map(y -> islemYasaklariMapper.INSTANCE.mapToEntity(y)).collect(Collectors.toList());


        for (IslemYasaklari islemYasaklari: islemYasaklariList) {
            PayEntity payEntity = payRepository.findByPayKodu(islemYasaklari.getPayKodu());
            islemYasaklari.setPayEntity(payEntity);
        }
        islemYasaklariRepository.saveAll(islemYasaklariList);


        //ROL TANIMLAMA
        Role role = new Role();
        role.setRoleName("ADMIN");
        roleRepository.save(role);
        Role role1 = new Role();
        role1.setRoleName("USER");
        roleRepository.save(role1);

        //ADMIN TANIMLAMA
        UserEntity user = new UserEntity();
        user.setUserName("mert");
        user.setPassword(passwordEncoder.encode("5858"));
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        roleList.add(role1);
        user.setRoles(roleList);
        userRepository.save(user);

    }

}
