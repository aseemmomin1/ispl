package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.AboutContent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutContentRepository extends JpaRepository<AboutContent, Long> {
    List<AboutContent> findByActiveTrueOrderByUpdatedAtDesc();
}
