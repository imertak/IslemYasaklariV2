package com.pokemonrewiev.api.repository;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;


@Repository
public interface IslemYasaklariRepository extends JpaRepository<IslemYasaklari, Integer> {
    IslemYasaklari findByUnvan(String unvan);
}
