package com.liberty.poker.linksession;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LinkSessionEntityRepository extends JpaRepository<LinkSessionEntity, UUID> {
}
