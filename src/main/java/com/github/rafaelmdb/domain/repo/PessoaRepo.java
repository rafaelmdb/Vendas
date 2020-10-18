package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepo extends JpaRepository<Pessoa, UUID> {

}
