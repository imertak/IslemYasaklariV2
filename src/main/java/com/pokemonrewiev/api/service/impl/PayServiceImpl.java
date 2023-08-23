package com.pokemonrewiev.api.service.impl;

import com.pokemonrewiev.api.repository.IslemYasaklariRepository;
import com.pokemonrewiev.api.repository.PayRepository;
import com.pokemonrewiev.api.service.IslemYasaklariService;
import com.pokemonrewiev.api.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    PayRepository payRepository;
    @Autowired
    IslemYasaklariRepository islemYasaklariRepository;


}
