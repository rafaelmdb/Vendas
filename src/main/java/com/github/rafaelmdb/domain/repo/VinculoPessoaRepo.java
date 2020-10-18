package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.entity.VinculoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface VinculoPessoaRepo extends JpaRepository<VinculoPessoa, UUID> {

    @Query("select v from VinculoPessoa v where (v.pessoa=:pessoa and v.vinculo=:vinculo) or (v.vinculo=:pessoa and v.pessoa=:vinculo)")
    Optional<VinculoPessoa> findVinculo(Pessoa pessoa, Pessoa vinculo);
}
