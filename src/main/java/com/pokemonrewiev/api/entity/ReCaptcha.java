package com.pokemonrewiev.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCaptcha {
    String secretKey;
    String token;
}
