package com.pokemonrewiev.api.repository;
import com.pokemonrewiev.api.dto.IslemYasaklariDto;
import com.pokemonrewiev.api.entity.IslemYasaklari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;


@Repository
public interface IslemYasaklariRepository extends JpaRepository<IslemYasaklari, Integer> {
    void deleteById(int id);
    IslemYasaklari findByUnvan(String unvan);
    IslemYasaklari findByUnvanAndKurulKararNo(String unvan, String kurulKararNo);

}