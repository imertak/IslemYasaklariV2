package com.pokemonrewiev.api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCaptcha {
    String secretKey="6Lc89uMnAAAAABlD3je21MenfK_a4KW82_Ah63vM";
    String token;
}
