package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TabelaPrecoItemRepo extends JpaRepository<TabelaPrecoItem, UUID> {
}
