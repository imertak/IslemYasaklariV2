package com.pokemonrewiev.api.repository;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IslemYasaklariRepository extends JpaRepository<IslemYasaklari, Integer> {
}
