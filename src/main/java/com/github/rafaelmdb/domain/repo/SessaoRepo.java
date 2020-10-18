package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface SessaoRepo extends JpaRepository<Sessao, UUID> {

    Sessao findOneByData(LocalDate dataHora);
}
