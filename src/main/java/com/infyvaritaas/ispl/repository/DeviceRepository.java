package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    @EntityGraph(attributePaths = "category")
    Optional<Device> findBySlugAndActiveTrueAndDeletedFalse(String slug);

    @EntityGraph(attributePaths = "category")
    List<Device> findByActiveTrueAndDeletedFalseOrderByNameAsc();

    @EntityGraph(attributePaths = "category")
    List<Device> findByDeletedFalseOrderByNameAsc();
}
