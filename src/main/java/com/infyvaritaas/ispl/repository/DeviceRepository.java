package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findBySlug(String slug);

    List<Device> findByActiveTrueAndDeletedFalseOrderByNameAsc();
}
