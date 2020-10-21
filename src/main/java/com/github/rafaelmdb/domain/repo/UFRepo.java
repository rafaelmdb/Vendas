package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.UF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UFRepo extends JpaRepository<UF, UUID> {
}
