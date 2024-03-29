package com.pokemonrewiev.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class IslemYasaklari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String unvan;
    private String mkkSicilNo;
    //private String pay;
    //private String payKodu;
    private String kurulKararTarihi;
    private String kurulKararNo;
    @Transient
    private String payKodu;

    @ManyToOne(fetch = FetchType.LAZY)//cascade=CascadeType.MERGE
    @JoinColumn(name = "pay_id", referencedColumnName = "id")
    private PayEntity payEntity;
}
