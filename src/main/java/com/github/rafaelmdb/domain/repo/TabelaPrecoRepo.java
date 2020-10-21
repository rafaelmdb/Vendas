package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TabelaPrecoRepo extends JpaRepository<TabelaPreco, UUID> {
}
