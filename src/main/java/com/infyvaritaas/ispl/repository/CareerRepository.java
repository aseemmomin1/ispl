package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.Career;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findByActiveTrueAndDeletedFalseOrderByCreatedAtDesc();

    List<Career> findByDeletedFalseOrderByCreatedAtDesc();
}
