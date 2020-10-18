package com.github.rafaelmdb.domain.repo;

import com.github.rafaelmdb.domain.entity.ParticipanteSessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipanteSessaoRepo extends JpaRepository<ParticipanteSessao, UUID> {
}
