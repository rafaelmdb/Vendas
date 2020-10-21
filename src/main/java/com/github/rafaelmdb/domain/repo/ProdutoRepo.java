package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepo extends JpaRepository<Produto, UUID> {
}
