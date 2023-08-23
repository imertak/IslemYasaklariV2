package com.pokemonrewiev.api.repository;

import com.pokemonrewiev.api.entity.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PayRepository extends JpaRepository<PayEntity,Integer> {
    List<PayEntity> findListByPayKodu(String payKodu);
    PayEntity findByPayKodu(String payKodu);
}
