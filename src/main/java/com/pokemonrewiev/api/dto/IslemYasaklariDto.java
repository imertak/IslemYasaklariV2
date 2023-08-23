package com.pokemonrewiev.api.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IslemYasaklariDto {
    private String unvan;
    private String mkkSicilNo;
    private String pay;
    private String payKodu;
    private String kurulKararTarihi;
    private String kurulKararNo;
}
