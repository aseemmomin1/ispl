package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.DeviceCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long> {
    Optional<DeviceCategory> findBySlug(String slug);
}
